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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "board")
  private List<BoardItem> items = new ArrayList<>();

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
}
