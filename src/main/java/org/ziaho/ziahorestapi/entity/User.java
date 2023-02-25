package org.ziaho.ziahorestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder // builder를 사용할 수 있게 됩니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자가 없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "user") // 'user' 테이블과 매핑됨을 알립니다.
public class User {

    @Id // primaryKey임을 명시합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk생성전략을 DB에 위임한다는 의미.
    // primaryKey 필드를 auto_increment로 설정하는 경우와 같다.
    private long msrl;

    @Column(nullable = false, unique = true, length = 30)
    // uid colum을 명시, null값이 될 수 없으며(필수값) 유니크한 필드이고 최대 길이는 30
    private String uid;

    @Column(nullable = false, length = 100)
    // name column을 명시,  null값이 될 수 없으며(필수값) 최대 길이는 100
    private String name;

}
