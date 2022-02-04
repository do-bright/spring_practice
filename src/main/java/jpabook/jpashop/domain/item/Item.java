package jpabook.jpashop.domain.item;

// 추상 클래스 ( 구현체를 가지고 할 것이기 때문에 )

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
//import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// InheritanceType 눌러서 가보면 여러 종류들(Single_Table, Table_Per_Class. Joined) 있는데 우리는 그 중에서 single table를 써줄거다 라고 명시
// Joined 정규화(각각의 테이블로 변환), Single_Table 한 테이블에 다 때려 박기(통합 테이블로 변환), Table_per_class 서브타입 테이블로 변환하는 구현 클래스마다 테이블 생성
@DiscriminatorColumn(name="dtype") // 이거로(dtype) 구분할거다 라고 명시
@Getter //@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 추가로 상속 관계 (Album, Book, Movie) 만들어주기 (각각 클래스 java로 생성)

    // Category, Item 다대다 관계
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 상품 엔티티대로 작성 ; id name price:int stockQuantity categories:List + Album, Book, Movie 상속 관계

    // == 비즈니스 로직 == //

    /**
     * 재고 증가
     */

    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }


    /**
     * 재고 감소
     */

    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        // 재고가 0보다 없으면 예외 처리
        if (restStock<0){
            throw new NotEnoughStockException("need more stock 더이상 재고가 없음");
        }
        this.stockQuantity=restStock;
    }
}
