package com.roman14.jpabasic.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item extends BaseEntity
{
  @Id @GeneratedValue
  @Column(name = "item_id", nullable = false)
  private Long id;

  private BigInteger price;

  private Long stockQuantity;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public BigInteger getPrice()
  {
    return price;
  }

  public void setPrice(BigInteger price)
  {
    this.price = price;
  }

  public Long getStockQuantity()
  {
    return stockQuantity;
  }

  public void setStockQuantity(Long stockQuantity)
  {
    this.stockQuantity = stockQuantity;
  }
}
