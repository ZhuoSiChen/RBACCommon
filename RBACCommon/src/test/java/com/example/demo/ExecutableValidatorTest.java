package com.example.demo;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import com.alibaba.fastjson.JSON;

import org.hibernate.validator.constraints.Range;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.test.web.servlet.DispatcherServletCustomizer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ExecutableValidatorTest {

    @Test
    public void hibernateVaildTest() throws NoSuchMethodException, SecurityException {
        // 需要校验的方法实例
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator validator = factory.getValidator().forExecutables();

        Method method = this.getClass().getMethod("vaildMethod",  Integer.class, String.class, String.class);
        // 校验参数，应该是有两个非法的参数
        Object [] params = new Object[]{100, "", "test"};

        // 获得校验结果 Set 集合，有多少个字段校验错误 Set 的大小就是多少
        Set<ConstraintViolation<ExecutableValidatorTest>> constraintViolationSet =
                validator.validateParameters(this, method, params);

        System.out.println("非法参数校验结果条数: " + constraintViolationSet.size());
        constraintViolationSet.forEach(cons -> {
            System.out.println("非法消息: " + cons.getMessage());
        });

        params = new Object[]{10, "build-test", "test"};
        constraintViolationSet =
                validator.validateParameters(this, method, params);

        System.out.println("合法参数校验结果条数: " + constraintViolationSet.size());
    }

    // 校验示范方法
    public void vaildMethod(@NotNull @Range(min = 0, max = 18)Integer age, @NotBlank String build, String test){}

    @Test
    public void handle() {
        HandlerInterceptorAdapter adapter;
        DispatcherServletAutoConfiguration dispatcherServletAutoConfiguration;
        File file = new File("C:\\Users\\admin\\Desktop\\svnproject\\old");
        FileType file1= new FileType();
        System.out.println("\\");
        FileType files = readFile(file,"\\",file1);
        String str = JSON.toJSONString(files);
        FileType fileType = JSON.parseObject(str,FileType.class);
        System.out.println(fileType.fileName);
        System.out.println(str);
    }
    private FileType readFile(File file,String fileName,FileType fileType) {
        fileType.fileName = fileName;
        LinkedList<FileType> fileTypes = new LinkedList();
        for(int i = 0 ; i < file.listFiles().length; i ++){
            File childFile = file.listFiles()[i];
            FileType childFileType = new FileType();
            if(childFile.isFile()){
                childFileType.fileName=fileName+childFile.getName();
                childFileType.isFile = true;
            }
            if(childFile.isDirectory()){
                childFileType.isFile = false;
                readFile(childFile,fileName+childFile.getName()+File.separator,childFileType);
            }
            fileTypes.add(childFileType);
        }
        fileType.files = fileTypes;
        return fileType;
    }
    private static class FileType{
        public String fileName;
        public boolean isFile;
        public LinkedList<FileType> files;
    }
}

