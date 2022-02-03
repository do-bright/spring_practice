package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 아래 클래스 이름이랑 분리해주기 위해서 orders 로 함
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id") // 기본키 설정
    private Long id;

    @ManyToOne // 회원 -> 주문 일대다, 주문 -> 회원 다대일
    @JoinColumn(name = "member_member_id") // 매핑을 뭘로 할거냐 ; 주문에 회원 정보가 외래키로 있움 > 관련해서 표시!
    private Member member; // 외래키로 멤버 가져오기

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 -> 주문상세 : 일대다 주문상세 -> 주문 : 다대일
    // alt enter ; 클래스 orderItem 생성

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    // alt enter ; 클래스 Delivery 생성

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [Order, Cancel]
    // alt enter ; enum 열거형 OrderStatus 생성

    // 주문 엔티티대로 작성 ; id member:Member orderItems:List delivery:Delivery orderDate:Date status:OrderStatus
}
