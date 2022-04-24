package com.shop.projectlion.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

}