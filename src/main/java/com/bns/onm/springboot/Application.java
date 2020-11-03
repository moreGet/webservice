package com.bns.onm.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Auditing 활성화
// @EnableJpaAuditing // Test 문제로 삭제 config로 이동
// 해당 어노테이션 으로 부트의 자동설정, Bean 읽기와 생성을 모두 자동으로 설정
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}