package jp.konosuba.notificationserver.user.reguser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegUserRepository extends JpaRepository<RegUserEntity,Long> {
    RegUserEntity getRegUserEntitiesByPhoneAndCode(String phone,Integer code);
}
