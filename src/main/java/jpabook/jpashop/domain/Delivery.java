package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded// 내장타입
    private Address address;

    @Enumerated(EnumType.STRING) // Enum 열거형이다 명시
    // EnumType에는 Ordinal 이랑 String이 있음
    // Ordinal(default) 은 숫자 ex) 1:Ready, 2:Comp -> P; 중간에 다른 상태(Ready도 아니고 Comp도 아닌)가 생기면 망함 -> S ; 꼭 스트링으로 쓰기
    // String 은 문자로 들어감 (Ordinal 처럼 순서에 의해서 밀리는 게 없음)

    private DeliveryStatus status; //READY, COMP
    // alt enter - 열거형 DeliveryStatus 생성 (enum)


    // 배송 엔티티대로 작성 ; id order:Order address:Address status:DeliveryStatus
}
