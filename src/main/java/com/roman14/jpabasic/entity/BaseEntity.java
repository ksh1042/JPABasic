package com.roman14.jpabasic.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity
{
  private String registUserId;
  private LocalDateTime registDate;
  private String lastModifyUserId;
  private LocalDateTime lastModifyDate;

  public String getRegistUserId()
  {
    return registUserId;
  }

  public void setRegistUserId(String registUserId)
  {
    this.registUserId = registUserId;
  }

  public LocalDateTime getRegistDate()
  {
    return registDate;
  }

  public void setRegistDate(LocalDateTime registDate)
  {
    this.registDate = registDate;
  }

  public String getLastModifyUserId()
  {
    return lastModifyUserId;
  }

  public void setLastModifyUserId(String lastModifyUserId)
  {
    this.lastModifyUserId = lastModifyUserId;
  }

  public LocalDateTime getLastModifyDate()
  {
    return lastModifyDate;
  }

  public void setLastModifyDate(LocalDateTime lastModifyDate)
  {
    this.lastModifyDate = lastModifyDate;
  }
}
