package com.example.common;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespResult {
    private int resultCode;
    private Object data;
    private String message;
    public static RespResult getInstance(){
        return new RespResult();
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
