package com.iplume.ad.advice;

import com.iplume.ad.exception.AdException;
import com.iplume.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理.
 *
 * @author: lingchen
 * @date: 2021/4/17
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException ex) {
        // 异常响应对象.
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        // 异常错误信息.
        response.setData(ex.getMessage());
        return response;
    }
}
