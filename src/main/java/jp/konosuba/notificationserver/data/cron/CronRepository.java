package jp.konosuba.notificationserver.data.cron;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CronRepository extends JpaRepository<Cron,Long> {
}
