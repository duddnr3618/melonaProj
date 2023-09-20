package com.fundguide.melona.board.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseBoardEntity {
  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdTime;

  @UpdateTimestamp
  @Column(insertable = false)
  private LocalDateTime updatedTime;

}