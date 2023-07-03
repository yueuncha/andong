package com.tour.user;

import com.tour.JsonForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.validation.ValidationException;
import java.net.BindException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@ControllerAdvice()
public class ExceptionController {
    private Map<String, Object> setForm;

    public ExceptionController() {
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
    }

//    //예외 처리
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public Map<String, Object> serverError(Exception e){
//        setForm.replace("code", "SUCCESS", "SERVER ERROR");
//        setForm.put("message", e.getMessage());
//        return setForm;
//    }



}
