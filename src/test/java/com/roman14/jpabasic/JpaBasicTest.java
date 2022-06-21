package com.roman14.jpabasic;

import com.roman14.jpabasic.entity.Member;
import com.roman14.jpabasic.entity.Team;
import com.roman14.jpabasic.entity.embeded.Address;
import com.roman14.jpabasic.entity.embeded.WorkTime;
import com.roman14.jpabasic.entity.enumeration.City;
import com.roman14.jpabasic.entity.enumeration.Sex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;

class JpaBasicTest
{
  private JpaBasic jb;
  private static final SecureRandom sr = new SecureRandom();

  @BeforeEach
  void init()
  {
    jb = new JpaBasic();
  }
  @Transactional
  @Test
  void testAddMember()
  {
    Member member = getMember();

    Assertions.assertDoesNotThrow(
      () -> jb.addMember(member)
    );
  }

  @Test
  void testFindMember()
  {
    Member expected = getMember();

    jb.addMember(expected);

    jb.clear();

    Assertions.assertEquals(
      expected, jb.findMember(expected.getId()).get()
    );
  }

  @Test
  void findMemberJPQL()
  {
    Assertions.assertDoesNotThrow(
      () -> jb.findMemberJPQL()
    );
  }

  @Test
  void criteria()
  {
    Assertions.assertDoesNotThrow(
      () -> jb.criteria()
    );
  }

  @Test
  void nativeQuery()
  {
    Member expected = getMember();

    jb.addMember(expected);

    Assertions.assertDoesNotThrow(
      () -> jb.nativeQuery()
    );
  }

  @AfterEach
  void close()
  {
    jb.close();
  }

  private final Member getMember()
  {
    final Team team = new Team();
    team.setName("Backend1");

    final Member member = new Member();

    final WorkTime workTime = new WorkTime(LocalDateTime.now());

    final Address homeAddress = new Address(
      TestUtil.randomEnum(City.class).toString(),
      String.valueOf( TestUtil.randomInt(999999) )
    );

    final Address workAddress = new Address(
      TestUtil.randomEnum(City.class).toString(),
      String.valueOf( TestUtil.randomInt(999999) )
    );

    member.setUserId("roman14");
    member.setName("Moon");
    member.setTeam(team);
    member.setWorkTime(workTime);
    member.setSex( TestUtil.randomEnum(Sex.class) );
    member.setRegistUserId("_SYSTEM");
    member.setRegistDate(LocalDateTime.now());
    member.setDescription("created by system");
    member.setHomeAddress(homeAddress);
    member.setWorkAddress(workAddress);

    return member;
  }
}