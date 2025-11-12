package project.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.schedule.dto.*;
import project.schedule.service.ScheduleService;
import java.util.List;

/**
 * ScheduleController 클래스
 * - 클라이언트로부터 일정 관련 요청을 받아 처리하는 Controller 클래스
 * - 생성(POST), 조회(GET), 수정(PUT), 삭제(DELETE) 기능 제공
 */
@RestController
@RequiredArgsConstructor
public class ScheduleController {
    /** 일정 비즈니스 로직을 담당하는 Service */
    private final ScheduleService scheduleService;

    /**
     * 유저의 일정 생성
     * @param userId 유저의 고유 ID
     * @param request 일정 생성 Request DTO
     * @return 생성된 일정 Response DTO와 201(CREATED) 상태 코드
     */
    @PostMapping("/users/{userId}/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @PathVariable Long userId, @RequestBody CreateScheduleRequest request
    ) {
        ScheduleResponse result = scheduleService.save(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 유저의 전체 일정 조회
     * @param userId 유저의 고유 ID
     * @return 조회된 일정 Response DTO 리스트와 200(OK) 상태 코드
     */
    @GetMapping("/users/{userId}/schedules")
    public ResponseEntity<List<SchedulesResponse>> getAllSchedule(
            @PathVariable Long userId
    ) {
        List<SchedulesResponse> result = scheduleService.getAll(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 단건 조회
     * @param scheduleId 일정 고유 ID
     * @return 조회된 일정 Response DTO와 200(OK) 상태 코드
     */
    @GetMapping("/users/{userId}/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getOneSchedule(
            @PathVariable Long scheduleId
    ) {
        ScheduleResponse result = scheduleService.getOne(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request 일정 수정 Request DTO
     * @return 수정된 일정 Response DTO와 200(OK) 상태 코드
     */
    @PutMapping("/users/{userId}/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request)
    {
        ScheduleResponse result = scheduleService.update(scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 삭제
     * @param scheduleId 일정 고유 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("/users/{userId}/schedules/{scheduleId}")
    public ResponseEntity<List<Void>> deleteSchedule(
            @PathVariable Long scheduleId
    ) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}