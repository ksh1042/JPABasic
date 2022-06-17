package com.roman14.jpabasic.entity.embeded;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Access(AccessType.FIELD)
public class WorkTime
{
  @Column(name = "work_start_time")
  private LocalDateTime startTime;
  @Column(name = "work_end_time")
  private LocalDateTime endTime;

  public WorkTime(){}

  public WorkTime(LocalDateTime startTime)
  {
    this.startTime = startTime;
  }

  public boolean isWork()
  {
    return startTime != null && endTime == null;
  }

  public LocalDateTime getStartTime()
  {
    return startTime;
  }

  private final void setStartTime(LocalDateTime startTime)
  {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime()
  {
    return endTime;
  }

  private final void setEndTime(LocalDateTime endTime)
  {
    this.endTime = endTime;
  }
}
