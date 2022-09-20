package jpabook.jpashop.domian;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name =  "ORDERS")
public class Order extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   //cascade 자동으로 영속성애 넣을지 말지 결정
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    /*@Enumerated 이넘클래스 데이터 저장 값 설정*/
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //cascade 자동으로 영속성컨테이너 넣을지 말지 결정 , orphanRemoval 부모객체을 지우면 같이 지워짐
    //위 두개 사용시 주의점 ::: 다른곳에서 안쓰고 이 엔티티에서만 쓸때 사용해야된다.
    //ex) OneToMany, OneToOne 인데  Entity를 다른곳에서 참조하여 안쓸때 써야된다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void addOrderItem(OrderItem orderItem){
        //연관관계 주인이 아닐 떄 세팅 유틸
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
