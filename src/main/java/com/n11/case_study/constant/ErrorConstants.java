package com.n11.case_study.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants {

    public static final String ENTITY_ALREADY_EXISTS = "Entity already exists!";
    public static final String ENTITY_NOT_FOUND = "Entity not found!";
    public static final String BREAK_TIME_NOT_FINISH = "Session can not create at break time!";
    public static final String SESSION_END_DATE_TIME_EXCEED = "Session could not create. Session end date exceed!";
    public static final String LIGHTNING_TALK_TIME_EXCEED = "Lightning talk time can not be more than 5 minutes!";
    public static final String LIGHTNING_TALK_TIME_IS_NOT_PROPER = "Lightning talk time can not start before 16!";

}
