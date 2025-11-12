package project.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.schedule.entity.Schedule;
import project.user.entity.User;

import java.util.List;

/**
 * ScheduleRepository 인터페이스
 * - 일정 Entity에 대한 CRUD를 수행하는 Repository 인터페이스
 * - JpaRepository를 활용
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * 유저명을 기준으로 전체 조회 후, 수정일 기준으로 내림차순
     * @param user 유저명
     * @return 유저명의 전체 일정
     */
    List<Schedule> findByUserOrderByModifiedAtDesc(User user);
}
