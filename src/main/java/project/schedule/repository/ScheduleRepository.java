package project.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.schedule.entity.Schedule;
import project.user.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * ScheduleRepository 인터페이스
 * - 일정 Entity에 대한 CRUD를 수행하는 Repository 인터페이스
 * - JpaRepository를 활용
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * 유저를 기준으로 전체 조회 후, 수정일 기준으로 내림차순
     * @param user 유저
     * @return 유저의 전체 일정
     */
    List<Schedule> findByUserOrderByModifiedAtDesc(User user);

    /**
     * 일정 ID와 유저가 모두 일치하는 일정 조회
     * @param scheduleId 일정 고유 ID
     * @param user 유저
     * @return 유저의 해당 일정 조회
     */
    Optional<Schedule> findByIdAndUser(Long scheduleId, User user);

    /**
     * 일정 ID와 유저가 모두 일치하는 일정 존재 확인
     * @param scheduleId 일정 고유 ID
     * @param user 유저
     * @return 유저의 해당 일정 존재 여부
     */
    boolean existsByIdAndUser(Long scheduleId, User user);
}
