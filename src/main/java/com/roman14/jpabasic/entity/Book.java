package com.roman14.jpabasic.entity;

import javax.persistence.Entity;

@Entity
public class Book extends Item
{
  private String author;
  private String isbn;

  public String getAuthor()
  {
    return author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public String getIsbn()
  {
    return isbn;
  }

  public void setIsbn(String isbn)
  {
    this.isbn = isbn;
    logIsbn(isbn);
  }

  private void logIsbn(String isbn)
  {
    System.out.println(isbn);
  }
}
