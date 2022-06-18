package com.roman14.jpabasic.entity.embeded;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address
{
  private String city;
  private String address;
  private String zipCode;

  public Address(){}
  public Address(String city, String address, String zipCode)
  {
    this.city = city;
    this.address = address;
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

  public String getAddress()
  {
    return address;
  }

  private final void setAddress(String address)
  {
    this.address = address;
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
    Address address1 = (Address) o;
    return Objects.equals(getCity(), address1.getCity()) && Objects.equals(getAddress(), address1.getAddress()) && Objects.equals(getZipCode(), address1.getZipCode());
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(getCity(), getAddress(), getZipCode());
  }
}
