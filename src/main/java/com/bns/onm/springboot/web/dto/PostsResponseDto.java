package com.bns.onm.springboot.web.dto;

import com.bns.onm.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.title = entity.getContent();
        this.author = entity.getAuthor();
    }
}
