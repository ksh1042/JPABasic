package com.roman14.jpabasic;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaBasic
{
  public void main()
  {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    try
    {
      emf = Persistence.createEntityManagerFactory("JpaBasic");
      em  = emf.createEntityManager();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if( em != null ) em.close();
      if( emf != null ) emf.close();
    }

  }
}
