package jpabook.jpashop;

import jpabook.jpashop.domian.Order;
import jpabook.jpashop.domian.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //persistence.xml 유닛네임 넣어줌
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        // EntityManager 쓰레드간 공유 x
        EntityManager em =  emf.createEntityManager();
        // JPA의 모든 데이터변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        System.out.println("Hello Spring Jpa study");
        tx.begin();
        try {
            //양방향 아니여도 세팅 가능함
            Order order = new Order();
            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            //닫기
            em.clear();
        }
        emf.close();


    }
}
