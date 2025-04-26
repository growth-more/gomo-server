package com.gomo.app.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAudit {

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    protected LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;
}
