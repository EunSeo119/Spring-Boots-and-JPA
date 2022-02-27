package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany     //단방향으로 해도 되는데 이 예제도 보여주고 싶어서 걍 해봄(실무에서는 다대다 x)
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 편의 메서드==//     //양방향일 때 편하게 만들어 주는 것!(양쪽의 핵심적으로 컨트롤 하는 쪽에 넣어주는 것이 좋음!)-양방향인거 모두 찾아서 넣기!
    public void addChildCategory(Category child) {
        this.child.add(child);      //부모 컬렉션에도 들어가야되고
        child.setParent(this);      //반대로 자식에서도 부모가 누구인지 this로 넣어줌
    }
}
