package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.dao.mapper.UserMapper;
import com.example.dao.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    UserMapper mapper;

    @RequestMapping("/hello")
    public String index() {
        List<User> userList = mapper.getAll();
        return "Hello World "+ JSON.toJSONString(userList);
    }

    @RequestMapping("/login/LoginAction")
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    public String login(@NotBlank(message="用户名不能为空") String user){
        Connector a=new Connector();
        return user;
    }

}