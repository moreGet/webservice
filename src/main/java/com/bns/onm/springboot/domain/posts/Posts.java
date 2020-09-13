package com.bns.onm.springboot.domain.posts;

import com.bns.onm.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
/*
- @Entity 란?
- 테이블과 링크될 클래스 임을 나타냄
- 기본적으로 Class이름이 ComCpUser 라면 테이블 이름은 com_cp_user 임
- Class는 CamelCase -> Table은 UnderScoreNaming
 */
@Entity
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 부트2.0 부터는 AutoIncrement 를 위해 필수
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /*
    - 기본적으로 @Column 어노테이션이 존재하지 않더라도 @Entity 클래스 의 멤버 필드들은 Column을 나타냄
     */
    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}