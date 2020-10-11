/**
 * Authored by Gary on 2020/07/24.
 **/

package com.gary.microblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.core.annotation.AnnotationUtils.*;

//当下的全部Controller
@ControllerAdvice
public class ControllerExceptionHandler {
   private Logger logger=LoggerFactory.getLogger(this.getClass());
   @ExceptionHandler
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
       //记录日志
       logger.error("Request URL :{},Exception: {}",request,e);
       if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
           throw e;
       }
       ModelAndView modelAndView=new ModelAndView();
       modelAndView.addObject("url",request.getRequestURL());
       modelAndView.addObject("exception", e);
       modelAndView.setViewName("error/error");
       return modelAndView;
   }

}
