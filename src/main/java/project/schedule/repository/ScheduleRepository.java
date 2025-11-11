package project.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.schedule.entity.Schedule;
import java.util.List;

/**
 * ScheduleRepository 인터페이스
 * - 일정 Entity에 대한 CRUD를 수행하는 Repository 인터페이스
 * - JpaRepository를 활용
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * 전체 조회, 수정일 기준으로 내림차순
     * @return 등록된 전체 일정
     */
    List<Schedule> findAllByOrderByModifiedAtDesc();
    /**
     * 작성자명을 기준으로 다건 조회, 수정일 기준으로 내림차순
     * @param name 작성자명
     * @return 해당 작성자의 전체 일정
     */
    List<Schedule> findAllByNameOrderByModifiedAtDesc(String name);

}
