package com.roman14.jpabasic;


import com.roman14.jpabasic.entity.Classs;
import com.roman14.jpabasic.entity.Member;
import com.roman14.jpabasic.entity.Team;
import com.roman14.jpabasic.entity.enumeration.Sex;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

      final Team team = createTeamDummy(100L);
      final Classs classs = new Classs();

      classs.setId(1L);
      classs.setName("");

      em.persist(team);

      em.flush();

      final Member member = createMemberDummy(1L);

      member.setTeam(team);

      em.persist(member);



      em.flush();
      em.clear();

      final Team findTeam = em.find(Team.class, 100L);

      System.out.println(Arrays.toString(findTeam.getMembers().stream()
        .map(m -> m.getName())
        .toArray()
      ));

      // JPQL select ALL
//      List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
//        .getResultList();


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