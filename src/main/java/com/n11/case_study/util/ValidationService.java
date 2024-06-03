package com.n11.case_study.util;

import com.n11.case_study.constant.ErrorConstants;
import com.n11.case_study.data.request.SessionEntityRequest;
import com.n11.case_study.entity.SessionEntity;
import com.n11.case_study.exception.*;
import com.n11.case_study.exception.AlreadyExistsException;
import com.n11.case_study.exception.NotFoundException;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@UtilityClass
public class ValidationService {
    private static final int BREAK_START_TIME_HOUR = 12;
    private static final int END_DATE_TIME = 17;
    private static final int LIGHTNING_TALK_TIME_MINUTE = 5;
    private static final int LIGHTNING_TALK_START_TIME = 16;

    public <T> void validatePropertyIsAlreadyExists(Optional<T> optional, String exMsg) {
        if (optional.isPresent()) {
            throw new AlreadyExistsException(exMsg);
        }
    }

    public <T> T validatePropertyIsExists(Optional<T> optional, String exMsg) {
        if (!optional.isPresent()) {
            throw new NotFoundException(exMsg);
        }
        return optional.get();
    }

    public void validateSessionTimeForBreakTime(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate.getHour() == BREAK_START_TIME_HOUR) {
            throw new BreakTimeNotFinishException(ErrorConstants.BREAK_TIME_NOT_FINISH);
        }
        if (endDate.getHour() == BREAK_START_TIME_HOUR && endDate.getMinute() != 0) {
            throw new BreakTimeNotFinishException(ErrorConstants.BREAK_TIME_NOT_FINISH);
        }
    }

    public void validateSessionTimeForEndTime(ZonedDateTime endDate) {
        if (endDate.getHour() > END_DATE_TIME) {
            throw new SessionTimeOutException(ErrorConstants.SESSION_END_DATE_TIME_EXCEED);
        }
        if (endDate.getHour() == END_DATE_TIME && endDate.getMinute() > 0) {
            throw new SessionTimeOutException((ErrorConstants.SESSION_END_DATE_TIME_EXCEED));
        }
    }

    public void validateSessionMinuteForLightningTalk(SessionEntityRequest request) {
        if (request.getMinute() > LIGHTNING_TALK_TIME_MINUTE) {
            throw new LightningTalkTimeExceedException(ErrorConstants.LIGHTNING_TALK_TIME_EXCEED);
        }
    }

    public void validateSessionStartDateForLightningTalk(SessionEntity entity) {
        if (DateConverterUtils.translateDateTime(entity.getStartDate(), ZoneId.of("UTC")).getHour() < LIGHTNING_TALK_START_TIME) {
            throw new LightningTalkTimeNotProperException(ErrorConstants.LIGHTNING_TALK_TIME_IS_NOT_PROPER);
        }

    }
}
