package project.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.config.ScheduleNotFoundException;
import project.config.UserNotFoundException;
import project.schedule.dto.*;
import project.schedule.entity.Schedule;
import project.schedule.repository.ScheduleRepository;
import project.user.entity.User;
import project.user.repository.UserRepository;

import java.util.List;

/**
 * ScheduleService 클래스
 * - 일정 생성/조회/수정/삭제 로직을 처리하는 Service 클래스
 * - Controller와 Repository 사이에서 데이터를 가공하고 검증
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    /** 일정 데이터를 관리하는 JPA Repository */
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * Lv 1. 일정 생성
     * - 유저 조회 후, 유저와 함께 일정 생성
     * @param userId 유저의 고유 ID
     * @param request 일정 생성 Request DTO
     * @return 생성된 일정 Response DTO
     */
    @Transactional
    public ScheduleResponse save(Long userId, CreateScheduleRequest request) {
        // 유저 조회, 없으면 예외 처리
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Schedule schedule = new Schedule(request, user); // Entity에 일정-유저 저장
        Schedule saved = scheduleRepository.save(schedule); // DB에 일정-유저 저장

        return new ScheduleResponse(saved);
    }

    /**
     * Lv 2. 일정 조회
     * - 유저의 전체 일정 조회
     * - 일정 조회 후, 수정일 기준으로 내림차순 정렬
     * @param userId 유저의 고유 ID
     * @return 조회된 일정 Response DTO 리스트
     */
    @Transactional(readOnly = true) // 읽기 전용
    public List<SchedulesResponse> getAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new); // 없으면 예외 처리

        // 전체 일정 조회하고, '수정일' 기준 내림차순으로 정렬
        List<Schedule> schedules = scheduleRepository.findByUserOrderByModifiedAtDesc(user);

        return schedules.stream().map(SchedulesResponse::new).toList();
    }

    /**
     * Lv 2. 일정 조회
     * - scheduleId로 단일 일정 조회, null이면 예외 처리
     * @param scheduleId 일정 고유 ID
     * @return 조회된 일정 Response DTO
     */
    @Transactional(readOnly = true) // 읽기 전용
    public ScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new); // 일정 조회

        return new ScheduleResponse(schedule);
    }

    /**
     * Lv 3. 일정 수정
     * - scheduleId로 선택된 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request 일정 수정 Request DTO
     * @return 수정된 일정 Response DTO
     */
    @Transactional
    public ScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new); // 일정 조회

        schedule.update(request); // 선택한 일정 수정
        scheduleRepository.flush(); // 변경내용 DB에 동기화해서 수정일 갱신

        return new ScheduleResponse(schedule);
    }

    /**
     * Lv 4. 일정 삭제
     * - scheduleId로 선택된 일정 삭제
     * @param scheduleId 일정 고유 ID
     */
    @Transactional
    public void delete(Long scheduleId) {
        boolean exist = scheduleRepository.existsById(scheduleId); // 존재 여부
        if (!exist) throw new UserNotFoundException(); // 없으면 예외 처리

        scheduleRepository.deleteById(scheduleId); // 일정 삭제
    }

}
