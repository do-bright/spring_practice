package jpabook.jpashop.domain.item;

// 추상 클래스 ( 구현체를 가지고 할 것이기 때문에 )

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// InheritanceType 눌러서 가보면 여러 종류들(Single_Table, Table_Per_Class. Joined) 있는데 우리는 그 중에서 single table를 써줄거다 라고 명시
// Joined 정규화(각각의 테이블로 변환), Single_Table 한 테이블에 다 때려 박기(통합 테이블로 변환), Table_per_class 서브타입 테이블로 변환하는 구현 클래스마다 테이블 생성
@DiscriminatorColumn(name="dtype") // 이거로(dtype) 구분할거다 라고 명시
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 추가로 상속 관계 (Album, Book, Movie) 만들어주기 (각각 클래스 java로 생성)



    // 주문 엔티티대로 작성 ; id name price:int stockQuantity categories:List + Album, Book, Movie 상속 관계
}
