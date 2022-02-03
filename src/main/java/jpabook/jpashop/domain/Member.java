package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter // lombok 으로 게터 세터 열어주기
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // 기본키 설정
    private Long id;

    private String name;

    @Embedded // 내장 타입을 포함했다고 표시
    // alt enter ; 클래스 address 생성
    private Address address;

    @OneToMany(mappedBy = "member")// 회원 -> 주문 일대다, 주문 -> 회원 다대일
    // mappedBy = "member" 자식 표시, member는 Order.java (주문 테이블)내에서 private Member member; 에 의해 매핑이 되었다는 의미
    // 난 저거에 이해서 매핑이 되었다고 표시 !
    // alt enter ; 클래스 order 생성
    private List<Order> orders = new ArrayList<>();

    // 회원 엔티티대로 작성 ; id:Long name:String address:Address orders:List
    // 회원 테이블 분석 ; Member_Id(PK) Name City Street Zipcode

}
