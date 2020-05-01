package com.exam.travel.schedule;

import com.exam.travel.mapper.QuestionMapper;
import com.exam.travel.model.Question;
import com.exam.travel.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author w1586
 * @Date 2020/3/23 22:36
 * @Cersion 1.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 60*60*1000)
    public void reportCurrentTime() {
        int offset = 0;
        int limit = 5;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>(16);
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));

            for (Question question : list) {

                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        //这是一种计算优先级的计算方式
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }

            offset += limit;
        }
//        hotTagCache.setTags(priorities);
//
//        hotTagCache.getTags().forEach((k, v) -> {
//            System.out.println(k + ":" + v);
//        });

//        priorities.forEach((k, v) -> {
//            System.out.println(k + ":" + v);
//        });

        hotTagCache.updateTags(priorities);
    }
}



