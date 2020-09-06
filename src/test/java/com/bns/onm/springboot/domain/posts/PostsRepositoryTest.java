package com.bns.onm.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /*
    - Junit에서 단위테스트가 종료 할때 마다 실행해 주는 후처리
    - 테스트가 동시에 수행된다면 H2에는 데이터가 그대로 남아 있어 테스트가 실패 할 수 있음.
     */
    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /*
        - save : 테이블 posts에 insert/update 쿼리를 실행
        - id값이 존재한다면 update 없다면 insert
         */
        postsRepository.save(Posts.builder()
                       .title(title)
                       .content(content)
                       .author("dkdlwmzhs@naver.com")
                       .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
