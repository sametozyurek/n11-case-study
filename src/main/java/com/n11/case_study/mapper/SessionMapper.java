package com.n11.case_study.mapper;

import com.n11.case_study.data.request.SessionEntityRequest;
import com.n11.case_study.data.response.SessionEntityResponse;
import com.n11.case_study.entity.SessionEntity;
import com.n11.case_study.util.DateConverterUtils;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class SessionMapper {

    public SessionEntity toEntity(SessionEntityRequest request, ZonedDateTime startDate, ZonedDateTime endDate) {
        return SessionEntity.builder()
                .uuid(request.getUuid())
                .createdDate(request.getCreatedDate() != null ? request.getCreatedDate().toInstant().toEpochMilli() : System.currentTimeMillis())
                .title(request.getTitle())
                .startDate(startDate.toInstant().toEpochMilli())
                .endDate(endDate.toInstant().toEpochMilli())
                .build();
    }

    public SessionEntityResponse toResponse(SessionEntity entity) {
        return SessionEntityResponse.builder()
                .uuid(entity.getUuid())
                .createdDate(DateConverterUtils.translateDateTime(entity.getCreatedDate(), ZoneId.of("UTC")))
                .lastModifiedDate(DateConverterUtils.translateDateTime(entity.getLastModifiedDate(), ZoneId.of("UTC")))
                .title(entity.getTitle())
                .sessionType(entity.getSessionType())
                .startDate(DateConverterUtils.translateDateTime(entity.getStartDate(), ZoneId.of("UTC")))
                .endDate(DateConverterUtils.translateDateTime(entity.getEndDate(), ZoneId.of("UTC")))
                .build();
    }
}
