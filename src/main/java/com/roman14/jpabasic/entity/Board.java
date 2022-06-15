package com.roman14.jpabasic.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board
{
  @Id
  @Column(name = "board_id", nullable = false)
  private Long id;

  private String name;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "board")
  private List<BoardItem> items = new ArrayList<>();

  @Lob
  private String discription;

  public List<BoardItem> getItems()
  {
    return items;
  }

  public void setItems(List<BoardItem> items)
  {
    this.items = items;
  }

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

  public String getDiscription()
  {
    return discription;
  }

  public void setDiscription(String discription)
  {
    this.discription = discription;
  }
}
