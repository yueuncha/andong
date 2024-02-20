package com.tour.user;

import com.tour.JsonForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.net.BindException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@RestControllerAdvice(annotations = Controller.class)
public abstract class ExceptionController extends AbstractController {
    private final Map<String, Object> setForm;

    public ExceptionController() {
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
    }

    @ResponseBody

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
