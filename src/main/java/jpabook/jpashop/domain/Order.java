package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

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

    @ManyToOne(fetch = FetchType.LAZY) // 회원 -> 주문 일대다, 주문 -> 회원 다대일
    @JoinColumn(name = "member_member_id") // 매핑을 뭘로 할거냐 ; 주문에 회원 정보가 외래키로 있움 > 관련해서 표시!
    private Member member; // 외래키로 멤버 가져오기

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // cascade ; orderItem에다 데이터를 넣어두고 order를 저장하면 orderItem + order 같이 저장
    // 원래라면 orderItem 저장 -> order 저장해야하는데 cascade 명시하면 같이 저장해줌
    // 원래 persist(orderItemA) persist(orderItemB) persist(orderItemC) persist(order) 인데 -> 명시 후 ; persist(order)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 -> 주문상세 : 일대다 주문상세 -> 주문 : 다대일
    // alt enter ; 클래스 orderItem 생성

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // cascade ; order persist 할 때 delivery도 같이 persist
    // 원래라면 각각인데 cascade는 같이 해줌

    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    // alt enter ; 클래스 Delivery 생성

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [Order, Cancel]
    // alt enter ; enum 열거형 OrderStatus 생성


    // 연관관계 메서드 ; 양 방향일 때 한 쪽만 작성해주면 되도록
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    // 주문 엔티티대로 작성 ; id member:Member orderItems:List delivery:Delivery orderDate:Date status:OrderStatus

    // == 생성 매서드 == //
    public static Order createdOrder(Member member, Delivery delivery, OrderItem... orderItems){ // ... = 여러개 넘긴다
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus((OrderStatus.ORDER)); // 주문한 상태로 만들기
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // == 비즈니스 로직 == //
    /**
     * 주문 취소
     */
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){ // COMP 배송 완료라고 설정 했었음 == 배송 완료가 되면 취소를 못함
            throw new IllegalStateException("이미 배송이 완료되어 상품 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL); // 상태 취소로 바꿔주기
        for (OrderItem orderItem : orderItems){
            // 한 번 주문할 때 고객이 상품을 여러개 주문 할 수 있음 -> OrderItem 여러개 생김 -> 주문 취소하면 각각에 cancel 날려주기 ! ; cancel이 할 것
            orderItem.cancel(); // alt enter 해서 매소드 추가 해주기
        }
    }

    // == 조회 로직 == //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        // alt enter 해서 매소드 추가 해주기
        // getTotalPrice()로 해주는 이유 저기서 주문수량 * 주문 가격을 해줘야 하기 때문

        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
//        아래 alt enter 해서 sum()으로 바꾸기

//        int totalPrice=0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice(); // alt enter 해서 매소드 추가 해주기
//            // getTotalPrice()로 해주는 이유 저기서 주문수량 * 주문 가격을 해줘야 하기 때문
//        }
    }
}
