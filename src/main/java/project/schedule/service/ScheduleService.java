package project.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.schedule.dto.*;
import project.schedule.entity.Schedule;
import project.schedule.repository.ScheduleRepository;
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

    /**
     * Lv 1. 일정 생성
     * @param request 일정 생성 Request DTO
     * @return 생성된 일정 Response DTO
     */
    @Transactional
    public ScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request); // DTO → Entity 변환
        Schedule saved = scheduleRepository.save(schedule); // DB에 일정 저장

        return new ScheduleResponse(saved); // Entity → DTO 변환 후 반환
    }

    /**
     * Lv 2. 일정 조회
     * - name 미작성 시 전체 일정 조회
     * - name 작성 시 name의 전체 일정 조회
     * - 일정 조회 후, 수정일 기준으로 내림차순 정렬
     * @param name 작성자명 (선택)
     * @return 조회된 일정 Response DTO 리스트
     */
    @Transactional(readOnly = true) // 읽기 전용
    public List<ScheduleResponse> getAll(String name) {
        // 전체 일정 조회하고, '수정일' 기준 내림차순으로 정렬
        List<Schedule> schedules = name==null // name이 null이면 전체 조회, 아니면 '작성자명'을 기준으로 조회
                ? scheduleRepository.findAllByOrderByModifiedAtDesc()
                : scheduleRepository.findAllByNameOrderByModifiedAtDesc(name);

        return schedules.stream().map(ScheduleResponse::new).toList(); // Entity 리스트 → DTO 리스트 변환 후 반환
    }

    /**
     * Lv 2. 일정 조회
     * - id로 단일 일정 조회, null이면 예외 처리
     * @param id 일정 고유 ID
     * @return 조회된 일정 Response DTO
     * @throws IllegalStateException 해당 ID의 일정이 존재하지 않을 경우
     */
    @Transactional(readOnly = true) // 읽기 전용
    public ScheduleResponse getOne(Long id) {
        Schedule schedule = findOrThrow(id); // 일정 조회

        return new ScheduleResponse(schedule);
    }

    /**
     * Lv 3. 일정 수정
     * - id로 선택된 일정 수정
     * @param id 일정 고유 ID
     * @param request 일정 수정 Request DTO
     * @return 수정된 일정 Response DTO
     * @throws IllegalStateException 일정이 존재하지 않거나 비밀번호가 일치하지 않을 경우
     */
    @Transactional
    public ScheduleResponse update(Long id, UpdateScheduleRequest request) {
        Schedule schedule = findOrThrow(id); // 일정 조회

        schedule.update(request); // 선택한 일정 수정
        scheduleRepository.flush(); // 변경내용 DB에 동기화해서 수정일 갱신

        return new ScheduleResponse(schedule);
    }

    /**
     * Lv 4. 일정 삭제
     * - id로 선택된 일정 삭제
     * @param id 일정 고유 ID
     * @throws IllegalStateException 일정이 존재하지 않거나 비밀번호가 일치하지 않을 경우
     */
    @Transactional
    public void delete(Long id) {
        boolean exist = scheduleRepository.existsById(id); // 존재 여부
        if (!exist) throw new IllegalStateException("없는 유저입니다."); // 없으면 예외 처리

        scheduleRepository.deleteById(id); // 일정 삭제
    }

    /**
     * id를 기준으로 조회, null이면 예외 처리
     * @param id 일정 고유 ID
     * @return id를 기준으로 조회된 일정 Entity
     * @throws IllegalStateException 해당 ID의 일정이 존재하지 않을 경우
     */
    private Schedule findOrThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정입니다."));
    }

}
