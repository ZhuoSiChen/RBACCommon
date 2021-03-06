package com.example.valid;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Aspect
public class RequestParamValidAspect{

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();

    private static Logger log = LoggerFactory.getLogger(RequestParamValidAspect.class);

    @Pointcut("execution(* com.example.controller.*.*(..))")
    public void controllerBefore(){};

    ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Before("controllerBefore()")
    public boolean before(JoinPoint point) throws NoSuchMethodException, SecurityException, ParamValidException{
        //  获得切入目标对象
        System.out.println("in before");
        Object target = point.getThis();
        // 获得切入方法参数
        Object [] args = point.getArgs();
        // 获得切入的方法
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        log.info("invoker method:{} ,args={}",method.getName(), JSON.toJSONString(args));
        Set<ConstraintViolation<Object>> validResult =  validator.validateParameters(target, method, args);
        // 执行校验，获得校验结果
        Set<ConstraintViolation<Object>> validResult1 = validMethodParams(target, method, args);

        if (!validResult.isEmpty()) {
            FieldError error = new FieldError();// 将需要的信息包装成简单的对象，方便后面处理
            String [] parameterNames = parameterNameDiscoverer.getParameterNames(method); // 获得方法的参数名称
            List<FieldError> errors = validResult.stream().map(constraintViolation -> {
                PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();  // 获得校验的参数路径信息
                int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
                String paramName = parameterNames[paramIndex];  // 获得校验的参数名称
                error.setName(paramName);  // 参数名称（校验错误的参数名称）
                error.setMessage(constraintViolation.getMessage()); // 校验的错误信息
                return error;
            }).collect(Collectors.toList());
            ParamValidException paramValidException = new ParamValidException(errors);
            paramValidException.setMethod(method.toString());
            throw paramValidException;  // 我个人的处理方式，抛出异常，交给上层处理
        }
        return true;
    }

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object [] params){
        return validator.validateParameters(obj, method, params);
    }
}
