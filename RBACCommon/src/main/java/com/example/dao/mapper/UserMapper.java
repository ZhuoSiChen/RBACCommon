package com.example.dao.mapper;

import com.example.dao.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select * from user_tbl")
    List<User> getAll();

    User getOne(Long id);

    int insert(User user);

    int update(User user);

    int delete(Long id);
}
