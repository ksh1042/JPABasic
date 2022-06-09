package com.roman14.jpabasic.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Grade
{
  @Id
  @Column(name = "grade_id", nullable = false)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
  private List<Member> members = new ArrayList<>();

  @Lob
  private String descrption;

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

  public List<Member> getMembers()
  {
    return members;
  }

  public void setMembers(List<Member> members)
  {
    this.members = members;
  }

  public String getDescrption()
  {
    return descrption;
  }

  public void setDescrption(String descrption)
  {
    this.descrption = descrption;
  }
}
