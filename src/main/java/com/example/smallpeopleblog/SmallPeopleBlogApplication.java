package com.example.smallpeopleblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.persistence.EntityManagerFactory;

//@RestController
@SpringBootApplication
@ServletComponentScan
//public class SmallPeopleBlogApplication implements CommandLineRunner {
public class SmallPeopleBlogApplication {

    @Autowired
    EntityManagerFactory emf;

    public static void main(String[] args) {
        SpringApplication.run(SmallPeopleBlogApplication.class, args);
//        SpringApplication app = new SpringApplication(SmallPeopleBlogApplication.class);
//        app.setWebApplicationType(WebApplicationType.NONE);
//        app.run(args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        EntityManager em = emf.createEntityManager();
//        System.out.println("em = " + em);
//
//        EntityTransaction tx = em.getTransaction();
//
//        Board board = new Board();
//
//        // 1. 저장
//        tx.begin(); // 트랜잭션 시작
//        em.persist(board); // Board엔티티를 em에 영속화(저장)
//        em.persist(board); // 같은 Board엔티티를 여러번 저장해도 한번만 insert
//        tx.commit(); // 트랜잭션 종료(DB에 반영)
//
//        // 2. 수정
//        board.update(new BoardFormDto()); // PersistenceContext가 변경감지. update
//                                          // 여러번 setter를 사용해도 하나의 update문이 사용된다.
//
//        tx.commit(); // 트랜잭션 종료(DB에 반영)
//
//        // 3. 조회
//        Board board1 = em.find(Board.class, "aaa");
//        System.out.println("board==board1 = " + (board==board1));
//
//        // 4. 삭제
//        tx.begin();
//        em.remove(board); // em에서 board엔티티를 삭제
//        tx.commit();
//
//    }

//    @GetMapping("/")
//    public String hello() {
//        return "Hello, Spring Boot";
//    }

}
