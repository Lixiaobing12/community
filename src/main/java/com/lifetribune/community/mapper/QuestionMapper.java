package com.lifetribune.community.mapper;

import com.lifetribune.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper
{
   @Insert("insert into question (title,description,gmt_Create,gmt_Modified,creator,tag) values (#{title},#{description},#{gmt_Create},#{gmt_Modified},#{creator},#{tag})")
    void create(Question question);

   @Select("Select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value ="offset" ) Integer offset, @Param(value = "size") Integer size);

   @Select("select count(1) from question")
    Integer count();
}
