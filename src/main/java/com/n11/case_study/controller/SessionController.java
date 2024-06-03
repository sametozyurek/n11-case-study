package com.n11.case_study.controller;

import com.n11.case_study.data.request.SessionEntityRequest;
import com.n11.case_study.data.response.SessionEntityResponse;
import com.n11.case_study.mapper.SessionMapper;
import com.n11.case_study.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionMapper mapper;
    private final SessionService service;

    @PostMapping
    public ResponseEntity<SessionEntityResponse> save(@RequestBody SessionEntityRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.save(request)));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SessionEntityResponse>> saveList(@RequestBody List<SessionEntityRequest> requestList) {
        return ResponseEntity.ok(service.saveList(requestList).stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SessionEntityResponse> getById(@PathVariable String uuid) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(uuid)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionEntityResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toResponse).toList());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        service.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
