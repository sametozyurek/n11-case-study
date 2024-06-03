package com.n11.case_study.data.response;

import com.n11.case_study.configuration.ZonedDateTimeDeserializer;
import com.n11.case_study.data.base.BaseResponse;
import com.n11.case_study.data.enums.SessionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
public class SessionEntityResponse extends BaseResponse {
    private String title;
    private SessionType sessionType;
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private ZonedDateTime startDate;
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private ZonedDateTime endDate;
}
