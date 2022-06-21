package com.roman14.jpabasic;


import com.roman14.jpabasic.entity.Member;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

public class JpaBasic
{
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private static final SecureRandom sr = new SecureRandom();

  private final EntityManagerFactory emf;

  public JpaBasic()
  {
    this.emf = Persistence.createEntityManagerFactory("JpaBasic");
    this.em = this.emf.createEntityManager();
  }

  private final EntityManager em;


  public void addMember(Member member)
  {
    EntityTransaction tx = createTransaction(this.em);

    this.em.persist(member);

    tx.commit();
  }

  public Optional<Member> findMember(Long id)
  {
    EntityTransaction tx = createTransaction(this.em);

    Optional<Member> result = Optional.of( this.em.find(Member.class, id) );

    tx.commit();
    return result;
  }

  public Optional<List<Member>> findMemberJPQL()
  {
    Optional<List<Member>> result = Optional.empty();

    EntityManagerFactory emf = null;
    EntityManager em = null;
    Transaction tx = null;

    try
    {
      emf = Persistence.createEntityManagerFactory("JPABasic");
      em = emf.createEntityManager();

      tx.begin();

      result = Optional.of( em.createQuery("SELECT m FROM Member as m WHERE m.userId = '1'").getResultList() );

      tx.commit();
      em.close();
    }
    catch(Exception e)
    {
      if(tx != null) tx.rollback();
    }

    return result;
  }

  public List<Member> criteria()
  {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Member> query = cb.createQuery(Member.class);

    Root<Member> m = query.from(Member.class);

    return em.createQuery(
      query.select(m).where(cb.equal(m.get("name"), "Moon"))
    ).getResultList();
  }

  public List<Member> nativeQuery()
  {
    String query = "SELECT m.* FROM MEMBER m ORDER BY m.member_id DESC;";

    return em.createNativeQuery(query, Member.class)
      .getResultList();
  }

  private EntityTransaction createTransaction(EntityManager em)
  {
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    return tx;
  }

  public final void flush()
  {
    if(em != null) em.flush();
  }

  public final void clear()
  {
    if(em != null) em.clear();
  }

  public final void close()
  {
    if(em != null) em.close();
    if(emf != null) emf.close();
  }
}