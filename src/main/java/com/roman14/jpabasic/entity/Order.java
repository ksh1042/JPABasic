package com.roman14.jpabasic.entity;

import com.roman14.jpabasic.entity.enumeration.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order
{
  @Id @GeneratedValue
  @Column(name = "order_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Member getMember()
  {
    return member;
  }

  public void setMember(Member member)
  {
    this.member = member;
  }

  public LocalDateTime getOrderDate()
  {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate)
  {
    this.orderDate = orderDate;
  }

  public OrderStatus getStatus()
  {
    return status;
  }

  public void setStatus(OrderStatus status)
  {
    this.status = status;
  }
}
