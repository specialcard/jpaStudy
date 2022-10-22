package jpabook.jpashop;

import jpabook.jpashop.domian.*;

import javax.persistence.*;
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

//            Member m1 = new Member();
//
//            m1.setName("최승혁");
//            m1.setAddress(new Address("인천시", "부평구 청천동" ,"1-3"));
//            m1.setMemberClassType(MemberClassType.BRONZE);
//            em.persist(m1);
//
//            Member m2 = new Member();
//
//            m2.setName("김길동");
//            m2.setAddress(new Address("서울", "대방구" ,"김길동"));
//            em.persist(m2);
//
//            em.flush();
//            em.clear();


            String sql = "select m from Member m where m.name like :name";

            List<Member> MemberResult = em.createQuery(sql,Member.class)
                    .setParameter("name", "%"+"형"+"%")
                    .getResultList();
            for(Member member : MemberResult){
                System.out.println(member.getName() + member.getAddress().FullAddress());
            }


            //형변환 활용
            List MemberList2 = em.createQuery("select m.name , m.id from Member m").getResultList();

            Object MemberList2Object = MemberList2.get(0);

            Object[] result2 = (Object[]) MemberList2Object;

            System.out.println(result2[0]);
            System.out.println(result2[1]);

            List<Object[]> MemberList3 = em.createQuery("select m.name , m.id from Member m").getResultList();

            Object[] result3 = MemberList3.get(0);
            System.out.println(result3[0]);


            //DTO활용
            List<MemberDTO> MemberList4 = em.createQuery("select new jpabook.jpashop.domian.MemberDTO(m.name , m.id) from Member m",MemberDTO.class).getResultList();


            for(MemberDTO m : MemberList4){
                System.out.println(m.getName() + "DTO활용");
            }


            //페이징처리
            List<Member> pagingResult = em.createQuery("select m from Member m order by m.name",Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for(Member m : pagingResult){
                System.out.println(m.getName());
            }

//            Member MemberResult = Qry.setParameter("name", "김").getSingleResult();




//            //Criteria 사용 준비
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            //루트 클래스 (조회를 시작할 클래스)
//            Root<Member> m = query.from(Member.class);

//쿼리 생성 CriteriaQuery<Member> cq =  query.select(m).where(cb.equal(m.get("username"), “kim”)); List<Member> resultList = em.createQuery(cq).getResultList();



//            Movie moive = new Movie();
//
//            moive.setDirector("봉준호");
//            moive.setActor("송강호");
//            moive.setName("기생충");
//            moive.setPrice(12000);
//            moive.setCreatedBy("2000003582");
//            moive.setCreatedDate(LocalDateTime.now());
//            moive.setLastModifiedBy("2000003582");
//            moive.setLastModifiedDate(LocalDateTime.now());
//
//            em.persist(moive);
//
//            em.flush();
//            em.clear();


            //getReference << 쓸경우  find와 다르게 안쪽 메소드를 호출해야지
            //쿼리가 나감 getReference target -> moive
            //getReference 호출뒤 똑같은걸 find로 호출하면 find객체도 proxy형태가 됨.
            //반대로하면 find -> getReference proxy객체가아닌 movie 객체가됨
            //getReference :: 준영속상태(영속성컨테이너에없을경우) 에러발생
//            Movie refMoive = em.getReference(Movie.class, moive.getId());



            //양방향 아니여도 세팅 가능함
//            Order order = new Order();
//            em.persist(order);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            em.persist(orderItem);


            //enumType Sql
            //이넘 sql예시
            //파라미터로 안받으면 경로써야함 ex) m.memberClassType = jpabook.jpashop.domain.MemberClassType.DIAMOND
            String enumSql = "select m from Member m where m.memberClassType = :Mtypes";

            List<Member> enumSqlList = em.createQuery(enumSql,Member.class)
                    .setParameter("Mtypes", MemberClassType.DIAMOND)
                    .getResultList();

            for(Member m : enumSqlList){
                System.out.println(m.getName());

            }

            //상속관계시 type 으로 찾기
//            String typeSql = "select i from Item i where type(i) = BOOK ";
//
//            List<Object[]> ss = em.createQuery(typeSql)
//                    .getResultList();

            //caseSql
            String caseSql = "select " +
                             "case m.name when '최승혁' then '최승혁이네' else '일반유저' end " +
                             "from Member m ";

            List<String> caseSqlList = em.createQuery(caseSql, String.class)
                    .getResultList();
            for(String s : caseSqlList){
                System.out.println(s);
            }

            //경로표현식
            //잘못된 예시 > "select m.orders.OrderStatus from Member m"; 컬렉션에선 그다음으로 접근불가
            //조인으로 명시적으로 해줘야됨 > "select o.OrderStatus from Member m join Order o";
            //명시적조인으로 별칭을 얻어야된다.
            String pathSql = "select o.id from Member m join m.Orders o";

            List<Long> pathSqlList = em.createQuery(pathSql, Long.class).getResultList();

            for(Long s : pathSqlList){
                System.out.println(s);
            }

            //fetch join
            //조인패치를 쓰면 조인대상도 다가지고(조회) 옴
            //둘 이상의 컬랙션 패차 조인x => team 의 orders, members 둘다 조회시 x
            //패치조인 대상에는 별칭을 줄수 없습니다.(관례상x) *하이버네이트는 가능  ex)"select t from Team t join fetch t.members as m where m.name ='11' "; x
            // 예시 중복제거 > select distinct Member m  join fetch m.Orders
            // distinct를 쓰면 어플리케이션 레벨에서 중복제거해줌
            //jpql :: select i from item i where treat(i as Book).auther = 'kim'; jpql 자식으로 형변환
            String fetchSql = "select m from Member m left join fetch m.Orders where m.name like '%김%' ";

            List<Member> fetchSqlList = em.createQuery(fetchSql, Member.class).getResultList();
//            일대다에 경우 사용금지 setFirstResult, setMaxResults 사용금지 (전부 조회해오고 메모리에 올려서 계산해 가지고옴) => 다대일로 순서 바꿔서 조회 , @BatchSize(size = 100), xml에 배치사이즈적용 활용
//            List<Member> fetchSqlList2 = em.createQuery(fetchSql, Member.class).setFirstResult(0).setMaxResults(1).getResultList();

            for(Member s : fetchSqlList){
                System.out.println(s.getOrders().size());
                System.out.println(s.getName());
                //컬렉션 페치조인후 값 조회
                for(Order o : s.getOrders()){



                    System.out.println(o.getDelivery());
                }
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            //닫기
            em.clear();
        }
        emf.close();


    }
}
