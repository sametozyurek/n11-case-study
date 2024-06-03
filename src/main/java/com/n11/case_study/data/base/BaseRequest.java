package com.n11.case_study.data.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID uuid;
    @CreatedDate
    protected ZonedDateTime createdDate;
    @LastModifiedDate
    protected ZonedDateTime lastModifiedDate;

    public BaseRequest() {
    }

    public BaseRequest(UUID uuid, ZonedDateTime createdDate, ZonedDateTime lastModifiedDate) {
        this.uuid = uuid;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
