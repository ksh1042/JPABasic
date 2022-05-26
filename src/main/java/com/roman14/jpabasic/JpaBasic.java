package com.roman14.jpabasic;


import com.roman14.jpabasic.entity.Member;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

      final Member member = createMemberDummy(1L);
//      final Member member2 = createMemberDummy(2L);

      // save
//      em.persist(member);
//      em.persist(member2);

      Member findMember = em.find(Member.class, 1L);

      // JPQL select ALL
      List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
        .getResultList();

      // Update
//      findMember.setName("memberAA");

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
    Member member = new Member();

    member.setId(id);
    member.setName("name" + id);

    return member;
  }
}