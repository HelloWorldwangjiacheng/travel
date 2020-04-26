package com.exam.travel.service;


import com.exam.travel.dto.NotificationDTO;
import com.exam.travel.dto.PaginationDTO;
import com.exam.travel.enums.NotificationStatusEnum;
import com.exam.travel.enums.NotificationTypeEnum;
import com.exam.travel.exception.GlobalException;
import com.exam.travel.mapper.NotificationMapper;
import com.exam.travel.mapper.UserMapper;
import com.exam.travel.model.Notification;
import com.exam.travel.model.NotificationExample;
import com.exam.travel.model.User;
import com.exam.travel.result.CodeMsg;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author w1586
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //totalCount是数据库question表中的记录的数量
//        Integer totalCount = questionMapper.countByUserId(userId);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount%size == 0){
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }

        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);
//        size*(i-1)
        Integer offset = size*(page-1);
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("id desc");
        example.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        if (notifications.size() == 0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (!Objects.equals(notification.getReceiver(),user.getId())){
//            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
            throw new GlobalException(CodeMsg.READ_NOTIFICATION_FAIL);
        }
        if (notification == null){
//            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
            throw new GlobalException(CodeMsg.NOTIFICATION_NOT_FOUND);
        }

        //标记为已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        int i = notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
