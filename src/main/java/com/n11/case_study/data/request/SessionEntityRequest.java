package com.n11.case_study.data.request;

import com.n11.case_study.data.base.BaseRequest;
import com.n11.case_study.data.enums.SessionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionEntityRequest extends BaseRequest {
    private String title;
    private int minute;
    private SessionType sessionType;

}
