package com.roman14.jpabasic;


import com.roman14.jpabasic.entity.Member;
import com.roman14.jpabasic.entity.Team;
import com.roman14.jpabasic.entity.embeded.Address;
import com.roman14.jpabasic.entity.embeded.WorkTime;
import com.roman14.jpabasic.entity.enumeration.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaBasic
{
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public void addMember()
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

      final Team team = new Team();
      team.setName("Backend1");

      final Member member = new Member();

      final WorkTime workTime = new WorkTime(LocalDateTime.now());

      final Address homeAddress = new Address("city", "cheon-an", "136136");
      final Address workAddress = new Address("city", "seoul", "151324");

      member.setUserId("roman14");
      member.setName("Moon");
      member.setTeam(team);
      member.setWorkTime(workTime);
      member.setSex(Sex.INTERSEX);
      member.setRegistUserId("_SYSTEM");
      member.setRegistDate(LocalDateTime.now());
      member.setDescription("create by system");
      member.setHomeAddress(homeAddress);
      member.setWorkAddress(workAddress);

      em.persist(member);

      em.flush();
      em.clear();

      final Member findMember = em.find(Member.class, member.getId());

      System.out.println("findMember.getClass() = " + findMember.getClass());
      System.out.println("findMember.getName() = " + findMember.getName());

      tx.commit();
    }
    catch( Exception e )
    {
      if( tx != null ) tx.rollback();
      e.printStackTrace();
      throw e;
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