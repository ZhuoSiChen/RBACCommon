package com.example.exception;

import com.alibaba.fastjson.JSON;
import com.example.valid.ParamValidException;
import com.example.common.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParamValidException.class)
    @ResponseBody
    public String handle(ParamValidException e) {
        logger.info(e.getMessage());
        RespResult respResult = RespResult.getInstance();
        respResult.setData(e.getFieldErrors());
        return JSON.toJSONString(respResult);
    }
}
