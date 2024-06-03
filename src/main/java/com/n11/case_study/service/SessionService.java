package com.n11.case_study.service;

import com.n11.case_study.constant.ErrorConstants;
import com.n11.case_study.data.enums.SessionType;
import com.n11.case_study.data.request.SessionEntityRequest;
import com.n11.case_study.entity.SessionEntity;
import com.n11.case_study.repository.SessionRepository;
import com.n11.case_study.util.DateConverterUtils;
import com.n11.case_study.util.SessionUtils;
import com.n11.case_study.util.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private static final ZonedDateTime DAY = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1).with(LocalTime.of(9, 0));

    private final SessionRepository repository;

    public SessionEntity save(SessionEntityRequest entity) {
        return new SessionEntity();
    }

    public List<SessionEntity> saveList(List<SessionEntityRequest> requestList) {
        return prepareSessions(requestList);
    }

    public SessionEntity getById(String uuid) {
        return ValidationService.validatePropertyIsExists(repository.findById(UUID.fromString(uuid)), ErrorConstants.ENTITY_NOT_FOUND);
    }

    public List<SessionEntity> getAll() {
        return repository.findAll();
    }

    public void delete(String uuid) {
        repository.deleteById(UUID.fromString(uuid));
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private List<SessionEntity> prepareSessions(List<SessionEntityRequest> requestList) {
        int leftMinutes = checkPreviousSession();
        TreeMap<String, List<SessionEntityRequest>> preparedSessions = SessionUtils.scheduleSessions(requestList, leftMinutes);
        List<SessionEntityRequest> lightningTalks = preparedSessions.get(SessionType.LIGHTNING_TALK.name());
        preparedSessions.remove(SessionType.LIGHTNING_TALK.name());
        List<SessionEntity> response = new ArrayList<>();
        List<SessionEntityRequest> unProcessedSessions = new ArrayList<>();

        preparedSessions.forEach((sessionType, filteredRequestList) -> filteredRequestList.forEach(request -> repository.findLastSession()
                .ifPresentOrElse(filteredEntity -> {
                    ZonedDateTime validDate = checkDate(DateConverterUtils.translateDateTime(filteredEntity.getEndDate(), ZoneId.of("UTC")), request.getMinute());
                    if (!SessionType.LIGHTNING_TALK.name().equals(sessionType)) {
                        if (SessionUtils.checkNextSessionTimeExceedCurrentDeadLine(request.getMinute(), validDate)) {
                            if (!CollectionUtils.isEmpty(lightningTalks)) {
                                List<SessionEntityRequest> processedSessions = new ArrayList<>();
                                lightningTalks.forEach(lightningTalk -> {
                                    prepareLightningTalks(lightningTalk, validDate, response);
                                    processedSessions.add(lightningTalk);
                                });
                                lightningTalks.removeAll(processedSessions);
                            }
                            unProcessedSessions.add(request);
                        } else {
                            preparePresentationSessions(request, validDate, response);
                        }
                    }
                }, () -> preparePresentationSessions(request, DAY, response))));

        return prepareUnProcessedSessions(unProcessedSessions, response);
    }

    private List<SessionEntity> prepareUnProcessedSessions(List<SessionEntityRequest> requestList, List<SessionEntity> response) {
        if (!CollectionUtils.isEmpty(requestList)) {
            requestList.forEach(request -> repository.findLastSession()
                    .ifPresentOrElse(entity -> {
                        ZonedDateTime validDate = checkDate(DateConverterUtils.translateDateTime(entity.getEndDate(), ZoneId.of("UTC")), request.getMinute());
                        if (!SessionUtils.checkNextSessionTimeExceedCurrentDeadLine(request.getMinute(), validDate)) {
                            preparePresentationSessions(request, validDate, response);
                        } else {
                            preparePresentationSessions(request, DAY.plusDays(1), response);
                        }
                    }, () -> preparePresentationSessions(request, DAY, response)));
        }

        return response;
    }

    private int checkPreviousSession() {
        return repository.findLastSession()
                .map(entity -> {
                    ZonedDateTime endDate = DateConverterUtils.translateDateTime(entity.getEndDate(), ZoneId.of("UTC"));
                    if (endDate.getHour() < 17) {
                        int leftHours = 17 - endDate.getHour();
                        int leftMinutes = 59 - endDate.getMinute();
                        return (leftHours * 60) + leftMinutes;
                    }
                    return 0;
                })
                .orElse(0);
    }

    private void preparePresentationSessions(SessionEntityRequest request, ZonedDateTime validDate, List<SessionEntity> entities) {
        SessionEntity entity = SessionUtils.prepareSessionEntity(request, validDate);

        SessionUtils.validateSession(entity);

        repository.save(entity);
        entities.add(entity);
    }

    private void prepareLightningTalks(SessionEntityRequest request, ZonedDateTime validDate, List<SessionEntity> entities) {
        ValidationService.validateSessionMinuteForLightningTalk(request);

        SessionEntity entity = SessionUtils.prepareSessionEntity(request, validDate);

        ValidationService.validateSessionStartDateForLightningTalk(entity);
        SessionUtils.validateSession(entity);

        repository.save(entity);
        entities.add(entity);
    }

    public ZonedDateTime checkDate(ZonedDateTime dateTime, int nextSessionMinutes) {
        if (dateTime.getHour() == 11) {
            ZonedDateTime cc = dateTime.plusMinutes(nextSessionMinutes);
            if (cc.getHour() == 12 && cc.getMinute() > 0) {
                return dateTime.plusHours(1).plusMinutes(60 - dateTime.getMinute());
            }
        }
        if (dateTime.getHour() == 12) {
            return dateTime.plusHours(1);
        }
        if (dateTime.getHour() >= 17) {
            return DAY.plusDays(1);
        }
        return dateTime;
    }
}
