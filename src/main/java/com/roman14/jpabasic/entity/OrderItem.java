package com.roman14.jpabasic.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "order_items")
public class OrderItem
{
  @Id @GeneratedValue
  @Column(name = "order_item_id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  private BigInteger orderPrice;

  private int count;

  public Item getItem()
  {
    return item;
  }

  public void setItem(Item item)
  {
    this.item = item;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Order getOrder()
  {
    return order;
  }

  public void setOrder(Order order)
  {
    this.order = order;
  }

  public BigInteger getOrderPrice()
  {
    return orderPrice;
  }

  public void setOrderPrice(BigInteger orderPrice)
  {
    this.orderPrice = orderPrice;
  }

  public int getCount()
  {
    return count;
  }

  public void setCount(int count)
  {
    this.count = count;
  }
}
