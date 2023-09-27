package com.fundguide.melona.board.common.entity;

import com.fundguide.melona.board.common.role.BoardUsing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseBoardEntity {
  @CreationTimestamp
  @Column
  private LocalDateTime createdTime;

  @UpdateTimestamp
  @Column
  private LocalDateTime updatedTime;
}