package com.exam.travel.service;


import com.exam.travel.dto.PaginationDTO;
import com.exam.travel.dto.QuestionDTO;
import com.exam.travel.dto.QuestionQueryDTO;
import com.exam.travel.exception.GlobalException;
import com.exam.travel.mapper.QuestionExtMapper;
import com.exam.travel.mapper.QuestionMapper;
import com.exam.travel.mapper.UserMapper;
import com.exam.travel.model.Question;
import com.exam.travel.model.QuestionExample;
import com.exam.travel.model.User;
import com.exam.travel.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author w1586
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     *   这是在首页的分页展示
     */
    public PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size){

        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }



        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        //offset偏移
        Integer offset = size * (page - 1);
        Integer totalPage;
        //总记录数
//        Integer totalCount = questionMapper.count();

//        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample(););
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount  = questionExtMapper.countBySearch(questionQueryDTO);
        //得到总页数
        if (totalCount % size == 0) {
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }
        //容错校验
        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);

//        List<Question> questions = questionMapper.list(offset,size);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(offset, size);
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, rowBounds);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions){
//            User user  = userMapper.findById(question.getCreator());
        User user =  userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
        }

/**
 * 这是在我的问题中的分页
 * @param userId
 * @param page
 * @param size
 * @return
 */
public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        //totalCount是数据库question表中的记录的数量
//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
        .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);

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
        if (offset<1) {
        offset=1;
        }

//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
        .andCreatorEqualTo(userId);
        RowBounds rowBounds = new RowBounds(offset-1, size);
//        List<Question> qus = questionMapper.selectByExample(example);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, rowBounds);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
        User user = userMapper.selectByPrimaryKey(question.getCreator());
//            User user  = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);//将question上的所有属性复制到questionDTO上去
        questionDTO.setUser(user);
        questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
        }

public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
//        Question question = questionMapper.getById(id);
        //自定义异常抛出
        if (question == null){
        //当你查找的问题不存在时，比如你找id为9999999999的问题，显然是不存在的，就会抛出这个异常
        throw new GlobalException(CodeMsg.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);

        User user = userMapper.selectByPrimaryKey(question.getCreator());
//        User user  = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
        }

public void createOrUpdate(Question question) {
        if (question.getId() == null){
        //创建
        question.setGmtCreate(System.currentTimeMillis());
//            question.setGmtModified(question.getGmtCreate());
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);
//            questionMapper.createQuestion(question);
        //创建实质就是插入数据库
        questionMapper.insert(question);
        }else {
        //更新
//            questionMapper.update(question);
//            question.setGmtModified(question.getGmtCreate());

        Question updateQuestion = new Question();
        updateQuestion.setTitle(question.getTitle());
        updateQuestion.setDescription(question.getDescription());
        updateQuestion.setTag(question.getTag());
        QuestionExample example  = new QuestionExample();
        example.createCriteria()
        .andIdEqualTo(question.getId());
        int updated = questionMapper.updateByExampleSelective(updateQuestion,example);
        if (updated != 1){
        throw new GlobalException(CodeMsg.QUESTION_NOT_FOUND);
        }

        }
        }

public void incView(Long id) {
//        下面这段被注释的代码其实也是能够完成简单的阅读数的增加的，但是会出现一个问题
//        就是当有出现高并发的情况的时候可能会出现数据不一致的情况
//        Question question = questionMapper.selectByPrimaryKey(id);
//        Question updateQuestion = new Question();
//        updateQuestion.setViewCount(question.getViewCount()+1);
//        QuestionExample example = new QuestionExample();
//        example.createCriteria().andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(updateQuestion,example);

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

        }


public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        //额外验证
        if (StringUtils.isBlank(questionDTO.getTag())){
        return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
        QuestionDTO questionDTO1 = new QuestionDTO();
        BeanUtils.copyProperties(q,questionDTO1);
        return questionDTO1;
        }).collect(Collectors.toList());

        return questionDTOS;
        }
        }
