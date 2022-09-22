package jpabook.jpashop.domian;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "member")
    private List<Order> Orders = new ArrayList<>();

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

//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }
}
