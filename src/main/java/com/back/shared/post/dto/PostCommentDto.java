package com.back.shared.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostCommentDto {
  private final int Id;
  private final LocalDateTime creatDate;
  private final LocalDateTime modifyDate;
  private final int postId;
  private final int authorId;
  private final String authorName;
  private final String content;

}
