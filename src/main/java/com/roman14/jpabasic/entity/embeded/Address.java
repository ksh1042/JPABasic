package com.roman14.jpabasic.entity.embeded;

import javax.persistence.Embeddable;

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

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }
}
