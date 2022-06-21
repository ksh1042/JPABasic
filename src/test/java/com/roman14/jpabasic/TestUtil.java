package com.roman14.jpabasic;

import java.security.SecureRandom;

public class TestUtil
{
  private static final SecureRandom sr = new SecureRandom();

  /**
   * This is method to extract a random enumeration value.
   * <pre>{@code
   *   public enum Fruit {
   *     APPLE, BANANA, ORANGE, GRAPE
   *   }
   *
   *   public class Main {
   *     public void main()
   *     {
   *       Fruit fruit = randomEnum(Fruit.class);
   *     }
   *   }
   * }</pre>
   * @param clazz clazz must be Enumeration Type
   * @param <T>
   * @return &lt;T&gt;
   */
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz)
  {
    if( !clazz.isEnum() ) throw new IllegalArgumentException("clazz must be Enumeration Type");

    int n = sr.nextInt(clazz.getEnumConstants().length);

    return clazz.getEnumConstants()[n];
  }

  public static int randomInt(int range)
  {
    return sr.nextInt(range);
  }
}
