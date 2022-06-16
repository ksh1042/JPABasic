package com.roman14.jpabasic.entity;

import javax.persistence.*;

@Entity
public class Blog
{
  @Id
  @Column(name = "blog_id", nullable = false)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", unique = true)
  private Member member;

  private String title;

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
}
