package com.n11.case_study.repository;

import com.n11.case_study.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
    @Query(value = "select top 1 * from session_entity order by start_date desc", nativeQuery = true)
    Optional<SessionEntity> findLastSession();
}
