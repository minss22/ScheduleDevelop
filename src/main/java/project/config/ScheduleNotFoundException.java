package project.config;

/**
 * 해당 일정을 조회할 수 없을 때
 */
public class ScheduleNotFoundException extends ServiceException {
    public ScheduleNotFoundException() {
        super(ErrorMessage.NOT_FOUND_SCHEDULE);
    }
}
