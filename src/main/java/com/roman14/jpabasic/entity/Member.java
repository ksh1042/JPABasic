package com.roman14.jpabasic.entity;

import com.roman14.jpabasic.entity.embeded.Address;
import com.roman14.jpabasic.entity.embeded.WorkTime;
import com.roman14.jpabasic.entity.enumeration.Sex;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity
{
  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @Column(unique = true, nullable = false)
  private String userId;

  private String name;

  @Enumerated(EnumType.STRING)
  private Sex sex;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "class_id")
  private Classs classs;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id")
  private Grade grade;

  @Embedded
  private WorkTime workTime;

  @Embedded
  private Address homeAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "city", column = @Column(name = "work_city")),
    @AttributeOverride(name = "address", column = @Column(name = "work_address")),
    @AttributeOverride(name = "zipCode", column = @Column(name = "work_zipCode"))
  })
  private Address workAddress;

  @Lob
  private String description;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Sex getSex()
  {
    return sex;
  }

  public void setSex(Sex sex)
  {
    this.sex = sex;
  }

  public Team getTeam()
  {
    return team;
  }

  public void setTeam(Team team)
  {
    this.team = team;
  }

  public Classs getClasss()
  {
    return classs;
  }

  public void setClasss(Classs classs)
  {
    this.classs = classs;
  }

  public Grade getGrade()
  {
    return grade;
  }

  public void setGrade(Grade grade)
  {
    this.grade = grade;
  }

  public WorkTime getWorkTime()
  {
    return workTime;
  }

  public void setWorkTime(WorkTime workTime)
  {
    this.workTime = workTime;
  }

  public Address getHomeAddress()
  {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress)
  {
    this.homeAddress = homeAddress;
  }

  public Address getWorkAddress()
  {
    return workAddress;
  }

  public void setWorkAddress(Address workAddress)
  {
    this.workAddress = workAddress;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }
}
