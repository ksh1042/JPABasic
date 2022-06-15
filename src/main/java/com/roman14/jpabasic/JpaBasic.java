package com.roman14.jpabasic;


import com.roman14.jpabasic.entity.Book;
import com.roman14.jpabasic.entity.Classs;
import com.roman14.jpabasic.entity.Member;
import com.roman14.jpabasic.entity.Team;
import com.roman14.jpabasic.entity.enumeration.Sex;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class JpaBasic
{
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public void main()
  {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    EntityTransaction tx = null;

    try
    {
      emf = Persistence.createEntityManagerFactory("JpaBasic");
      em  = emf.createEntityManager();
      tx  = em.getTransaction();
      tx.begin();

      Book refBook = em.getReference(Book.class, 1L);

      System.out.println("refBook.getId() = " + refBook.getId());
      try
      {
        System.out.println("refBook.getPrice() = " + refBook.getPrice());
      }
      catch( EntityNotFoundException e )
      {
        Book book = new Book();
        book.setPrice(new BigInteger("23000"));
        book.setStockQuantity(5L);
        book.setAuthor("Moon");
        book.setIsbn("123456");

        em.persist(book);
      }

      tx.commit();
    }
    catch( Exception e )
    {
      if( tx != null ) tx.rollback();
      e.printStackTrace();
    }
    finally
    {
      if( em != null ) em.close();
      if( emf != null ) emf.close();
    }

  }

  private Member createMemberDummy(long id)
  {
    final Member member = new Member();

    member.setId(id);
    member.setName("name" + id);
    member.setSex(Sex.MALE);
    member.setAddTime(LocalDateTime.now());
    member.setDescription("create new member");

    return member;
  }

  private Team createTeamDummy(long id)
  {
    final Team team = new Team();

    team.setId(id);
    team.setName("team1");

    return team;
  }
}