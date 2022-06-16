package com.roman14.jpabasic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JpaBasicTest
{
  private JpaBasic jb;

  @BeforeEach
  void init()
  {
    jb = new JpaBasic();
  }

  @Test
  void main()
  {
    Assertions.assertDoesNotThrow(
      () -> jb.addMember()
    );
  }
}