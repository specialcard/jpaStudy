package jpabook.jpashop.domian;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "Member.findOneMember",
        query = "select m from Member m where m.id = :memberId"
)
//@NamedQuery(
//        name = "Member.findOneMember", 관례상 entity.쿼리이름
//        query = "select m from Member m where m.id = :memberId"
//)
// 장점  로딩시 캐시에 저장되어 쓸수 있음
// 단점 정적 쿼리만 가능
// xml에 작성하여 쓸수 있음
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
//    private String city;
//    private String street;
//    private String zipcode;

    //@Embedded :: Address에 @Embeddable선언 되어있어 생략이 가능하나 명시적으로 적어주는게 좋음
    @Embedded
    private Address address;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "member")
    private List<Order> Orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberClassType memberClassType;

//    @ElementCollection
//    @AttributeOverrides(@AssociationOverride(@JoinColumn(name = ""))) 값타입객체로 사용할때씀
//    테이블도 따로만들어짐 cascade, orphanRemoval 처럼 작동함
//    값변경시 주의사항 => 값을 추적하기 어려움으로 전체를 다삭제하고 다시 인설트함

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemberClassType getMemberClassType() {
        return memberClassType;
    }

    public void setMemberClassType(MemberClassType memberClassType) {
        this.memberClassType = memberClassType;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}
