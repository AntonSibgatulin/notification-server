package jp.konosuba.notificationserver.data.user.reguser;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "register_users")
public class RegUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(19)",nullable = false)
    private String phone;
    private Integer code;


}
