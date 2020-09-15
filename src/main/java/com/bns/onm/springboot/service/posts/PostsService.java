package com.bns.onm.springboot.service.posts;

import com.bns.onm.springboot.domain.posts.Posts;
import com.bns.onm.springboot.domain.posts.PostsRepository;
import com.bns.onm.springboot.web.dto.PostsListResponseDto;
import com.bns.onm.springboot.web.dto.PostsResponseDto;
import com.bns.onm.springboot.web.dto.PostsSaveRequestDto;
import com.bns.onm.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        /*Jpa 에서 이미 delete 메소드를 지원하고 있다.
        deleteById 로 바로 삭제가 가능하지만 존재하는지 확인 후
        삭제 하기위해 위 처럼 먼저 Posts 객체를 조회한다.*/
        postsRepository.delete(posts);
    }

    /*
    트랜잭션 범위는 유지
    조회기능만 남겨놓으므로 조회속도 개선 
     */
    @Transactional(readOnly = true) 
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}