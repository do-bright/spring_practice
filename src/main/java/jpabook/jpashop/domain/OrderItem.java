package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    // alt enter ; 클래스 Item 생성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order; // 주문 -> 주문상세 : 일대다 주문상세 -> 주문 : 다대일

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    // 주문상세 엔티티대로 작성 ; id item:Item order:Order orderPrice count

    // == 생성 매서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 생성된 만큼 재고를 빼주기 (주문한 상품 만큼 재고 빼줘야 하니까)
        return orderItem;
    }

    // == 비즈니스 로직 == //
    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addStock(count); // 재고를 다시 주문한 수량 만큼 올리기
    }

    // == 조회 로직 == //
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }


}
