package com.bns.onm.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // 현재 실행 중인 프로파일을 다 가져옵니다.
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> prdProfiles = Arrays.asList("prd", "prd1", "prd2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                       .filter(prdProfiles::contains)
                       .findAny()
                       .orElse(defaultProfile);
    }
}
