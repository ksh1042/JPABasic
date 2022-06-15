package com.roman14.jpabasic.entity;

import com.roman14.jpabasic.entity.enumeration.Sex;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member extends BaseEntity
{
  @Id
  @Column(name = "member_id")
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Sex sex;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_id")
  private Classs classs;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grade_id")
  private Grade grade;

  private LocalDateTime addTime;

  private LocalDateTime lastModifyTime;

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

  public LocalDateTime getAddTime()
  {
    return addTime;
  }

  public void setAddTime(LocalDateTime addTime)
  {
    this.addTime = addTime;
  }

  public LocalDateTime getLastModifyTime()
  {
    return lastModifyTime;
  }

  public void setLastModifyTime(LocalDateTime lastModifyTime)
  {
    this.lastModifyTime = lastModifyTime;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
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
}
