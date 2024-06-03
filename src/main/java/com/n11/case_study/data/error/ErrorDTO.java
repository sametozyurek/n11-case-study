package com.n11.case_study.data.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {
    private String code;
    private String message;
}
