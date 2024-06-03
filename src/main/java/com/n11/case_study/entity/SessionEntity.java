package com.n11.case_study.entity;

import com.n11.case_study.data.enums.SessionType;
import com.n11.case_study.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SessionEntity extends BaseEntity {
    private String title;
    private SessionType sessionType;
    private long startDate;
    private long endDate;
}
