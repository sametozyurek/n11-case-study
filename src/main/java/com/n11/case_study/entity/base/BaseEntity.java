package com.n11.case_study.entity.base;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@SuperBuilder
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID uuid;
    @CreatedDate
    protected long createdDate;
    @LastModifiedDate
    protected long lastModifiedDate;

    public BaseEntity() {
    }

    public BaseEntity(UUID uuid, long createdDate, long lastModifiedDate) {
        this.uuid = uuid;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = System.currentTimeMillis();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
