package jpabook.jpashop;

import jpabook.jpashop.domian.Movie;
import jpabook.jpashop.domian.Order;
import jpabook.jpashop.domian.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
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

            Movie moive = new Movie();

            moive.setDirector("봉준호");
            moive.setActor("송강호");
            moive.setName("기생충");
            moive.setPrice(12000);
            moive.setCreatedBy("2000003582");
            moive.setCreatedDate(LocalDateTime.now());
            moive.setLastModifiedBy("2000003582");
            moive.setLastModifiedDate(LocalDateTime.now());

            em.persist(moive);

            em.flush();
            em.clear();


            //getReference << 쓸경우  find와 다르게 안쪽 메소드를 호출해야지
            //쿼리가 나감 getReference target -> moive
            //getReference 호출뒤 똑같은걸 find로 호출하면 find객체도 proxy형태가 됨.
            //반대로하면 find -> getReference proxy객체가아닌 movie 객체가됨
            //getReference :: 준영속상태(영속성컨테이너에없을경우) 에러발생
            Movie refMoive = em.getReference(Movie.class, moive.getId());



            //양방향 아니여도 세팅 가능함
//            Order order = new Order();
//            em.persist(order);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            em.persist(orderItem);

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
