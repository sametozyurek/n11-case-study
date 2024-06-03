package com.n11.case_study.util;

import com.n11.case_study.data.enums.SessionType;
import com.n11.case_study.data.request.SessionEntityRequest;
import com.n11.case_study.entity.SessionEntity;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@UtilityClass
public class SessionUtils {
    public void validateSession(SessionEntity entity) {
        ValidationService.validateSessionTimeForBreakTime(DateConverterUtils.translateDateTime(entity.getStartDate(), ZoneId.of("UTC")), DateConverterUtils.translateDateTime(entity.getEndDate(), ZoneId.of("UTC")));
        ValidationService.validateSessionTimeForEndTime(DateConverterUtils.translateDateTime(entity.getEndDate(), ZoneId.of("UTC")));
    }

    public TreeMap<String, List<SessionEntityRequest>> scheduleSessions(List<SessionEntityRequest> sessions, int leftMinutes) {
        TreeMap<String, List<SessionEntityRequest>> scheduledSessions = new TreeMap<>();
        List<SessionEntityRequest> currentSession = new ArrayList<>();
        List<SessionEntityRequest> lightningTalkSession = new ArrayList<>();
        int currentSessionTime = leftMinutes;
        int key = 0;

        for (SessionEntityRequest session : sessions) {
            if (SessionType.PRESENTATION.equals(session.getSessionType())) {
                if (currentSessionTime + session.getMinute() <= 180) {
                    currentSession.add(session);
                    currentSessionTime += session.getMinute();
                } else {
                    key += 1;
                    scheduledSessions.put(String.valueOf(key), currentSession);
                    currentSession = new ArrayList<>();
                    currentSession.add(session);
                    currentSessionTime = session.getMinute();
                }
            } else {
                lightningTalkSession.add(session);
            }
        }

        if (!currentSession.isEmpty()) {
            scheduledSessions.put(String.valueOf(key + 1), currentSession);
        }
        if (!lightningTalkSession.isEmpty()) {
            scheduledSessions.put(SessionType.LIGHTNING_TALK.name(), lightningTalkSession);
        }

        return scheduledSessions;
    }

    public boolean checkNextSessionTimeExceedCurrentDeadLine(int nextSessionMinute, ZonedDateTime validDate) {
        validDate = validDate.plusMinutes(nextSessionMinute);
        return validDate.getHour() >= 17;
    }

    public SessionEntity prepareSessionEntity(SessionEntityRequest request, ZonedDateTime validDate) {
        return SessionEntity.builder()
                .title(request.getTitle())
                .sessionType(request.getSessionType())
                .startDate(validDate.toInstant().toEpochMilli())
                .endDate(validDate.plusMinutes(request.getMinute()).toInstant().toEpochMilli())
                .build();
    }
}
