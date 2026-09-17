package com.back.shared.post.dto;

import com.back.boundedContext.post.domain.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostCommentDto {
  private final int Id;
  private final LocalDateTime creatDate;
  private final LocalDateTime modifyDate;
  private final int authorId;
  private final String authorName;
  private final String content;

  public PostCommentDto(PostComment postComment) {
    this(
            postComment.getId(),
            postComment.getCreateDate(),
            postComment.getModifyDate(),
            postComment.getAuthor().getId(),
            postComment.getAuthor().getNickname(),
            postComment.getContent()
    );
  }

}
