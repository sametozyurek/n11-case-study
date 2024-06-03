package com.n11.case_study.data.response;

import com.n11.case_study.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class SessionEntityDetailResponse extends BaseEntity {
    private List<SessionEntityResponse> sessions;
    private Map<String, String> unsavedSessions;
}
