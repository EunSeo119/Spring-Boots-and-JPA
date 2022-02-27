package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   //상속 전략으로 SINGLE_TABLE 전략을 사용!(하나의 테이블에 다 때려박는거!)
@DiscriminatorColumn(name = "dtype")       //상속 된 애들 구분해주는거
@Getter
@Setter
public abstract class Item {      //추상 클래스로 만듬!(구현체를 가지고 할 것이기 때문에)

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQantity;

    @ManyToMany(mappedBy = "items")     //반대편은 걍 mappedBy하면 끝~
    private List<Category> categories = new ArrayList<>();

}
