package jp.konosuba.notificationserver.data.cron;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CronRepository extends JpaRepository<Cron,Long> {

    List<Cron> getCronByUserId(Long userId);
}
