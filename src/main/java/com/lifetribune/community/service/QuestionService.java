package com.lifetribune.community.service;

import com.lifetribune.community.dto.PaginationDTO;
import com.lifetribune.community.dto.QuestionDTO;
import com.lifetribune.community.mapper.QuestionMapper;
import com.lifetribune.community.mapper.UserMapper;
import com.lifetribune.community.model.Question;
import com.lifetribune.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * Spring boot专有的类方法，针对性注入
     *  @param page 分页数
     * @param size
     */
    public PaginationDTO list(Integer page, Integer size)
    {
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions)
        {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount= questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
}
