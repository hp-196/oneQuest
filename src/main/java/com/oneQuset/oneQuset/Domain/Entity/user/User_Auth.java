package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.user.Provider;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class User_Auth {
    /**
     * 유저의 인증 데이터,
     * id : 유저의 고유 id,
     * number : 유저의 번호,
     * email : 유저의 email,
     * provide : 유저가 인증한 소셜,
     * create_date : 생성 날짜
     */
    @Id
    @GeneratedValue
    private String id;
    private Long number;
    private String email;

    @Enumerated(EnumType.STRING)
    private Provider provide;
    private LocalDateTime create_date;
}
