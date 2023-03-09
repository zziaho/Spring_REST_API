package org.ziaho.ziahorestapi.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ziaho.ziahorestapi.advice.exception.CUserNotFoundException;
import org.ziaho.ziahorestapi.model.response.CommonResult;
import org.ziaho.ziahorestapi.service.ResponseService;

@RequiredArgsConstructor
@RestControllerAdvice // 예외 발생 시, Json의 형태로 결과를 반환하기 위함
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

//    // Exceotion이 발생하면 해당 Handler로 처리하겠다고 명시
//    // 괄호 안에는 어떤 Exceoption이 발생할때 handler를 적용할 것인지 Exception Class를 인자로 넣음.
//    @ExceptionHandler(Exception.class)
//    // 해당 Exception이 발생하면 Response에 출력되는 HttpStatus Code가 500으로 내려가도록 설정
//    // 성공시엔 200으로 내려간다.
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        // Exception 발생 시 이미 만들어둔 CommonResult의 실패 결과를 json형태로 출력하도록 설정
//        return responseService.getFailResult();
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request,
                                            Exception e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")),
                getMessage("unKnown.message")
        );
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("userNotFound.code")),
                getMessage("userNotFound.message")
        );
    }

    // code 정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
