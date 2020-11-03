package com.bns.onm.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /*
        - Controller 메소드의 특정 파라미터를 지원하는지 판단 합니다.
        - 여기서는 파라미터에 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class 인경우 true를 반환 합니다.
        - 이 클래스 작성후 WebConfig 파일을 작성해 WebMvcConfigurer 를 구현해 주어야 한다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    /*
        - 파라미터에 전달할 객체를 생성합니다.
        - 여기서는 세션의 객체를 가져옵니다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
