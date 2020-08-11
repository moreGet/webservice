package com.bns.onm.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 스프링 부트 테스트와 JUnit 사이의 연결자

/*
 - 여러 어노테이션중 Web Mvc에 집중 할 수 있는 어노테이션
 - @Controller, @ControllerAdvice 등 사용 가능
 - @Service, @Component, @Repository 등은 사용 붕가
 - 여기서는 단순 컨트롤러만 사용하기에 아래 어노테이션 사용
 */
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test // Test 메소드 표시
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // get Url 맵핑
                .andExpect(status().isOk()) // 응답이 200 ok 응답 이면
                .andExpect(content().string(hello)); // controller 의 return 값이 hello 인지 검사
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name)
                                               .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // jsonPath는 Json응답값을 필드별로 검증 할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
