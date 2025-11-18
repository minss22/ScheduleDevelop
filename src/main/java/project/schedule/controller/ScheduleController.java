package project.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.config.UnauthorizedException;
import project.schedule.dto.*;
import project.schedule.service.ScheduleService;
import project.user.dto.SessionUser;

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
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @param request 일정 생성 Request DTO
     * @return 생성된 일정 Response DTO와 201(CREATED) 상태 코드
     */
    @PostMapping("/users/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        ScheduleResponse result = scheduleService.save(sessionUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 유저의 전체 일정 조회
     * @param sessionUser 로그인한 유저 정보를 담은 DTO
     * @return 조회된 일정 Response DTO 리스트와 200(OK) 상태 코드
     */
    @GetMapping("/users/schedules")
    public ResponseEntity<List<SchedulesResponse>> getAllSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        List<SchedulesResponse> result = scheduleService.getAll(sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 단건 조회
     * @param scheduleId 일정 고유 ID
     * @return 조회된 일정 Response DTO와 200(OK) 상태 코드
     */
    @GetMapping("/users/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getOneSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        ScheduleResponse result = scheduleService.getOne(sessionUser.getId(), scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request 일정 수정 Request DTO
     * @return 수정된 일정 Response DTO와 200(OK) 상태 코드
     */
    @PutMapping("/users/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request)
    {
        if (sessionUser == null) throw new UnauthorizedException();

        ScheduleResponse result = scheduleService.update(sessionUser.getId(), scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 유저의 일정 삭제
     * @param scheduleId 일정 고유 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("/users/schedules/{scheduleId}")
    public ResponseEntity<List<Void>> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        if (sessionUser == null) throw new UnauthorizedException();

        scheduleService.delete(sessionUser.getId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}