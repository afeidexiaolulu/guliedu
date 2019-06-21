package com.atguigu.gulixueyuan.ucenter.handler;

import com.atguigu.entity.R;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常处理类
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("出现异常，操作失败");
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e) {
        e.printStackTrace();
        return R.error().message("sql语句出现异常");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
