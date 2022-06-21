package com.roman14.jpabasic.entity.embeded;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address
{
  private String city;
  private String zipCode;

  public Address(){}
  public Address(String city, String zipCode)
  {
    this.city = city;
    this.zipCode = zipCode;
  }

  public String getCity()
  {
    return city;
  }

  private final void setCity(String city)
  {
    this.city = city;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  private final void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;
    Address address = (Address) o;
    return Objects.equals(getCity(), address.getCity()) && Objects.equals(getZipCode(), address.getZipCode());
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(getCity(), getZipCode());
  }
}
