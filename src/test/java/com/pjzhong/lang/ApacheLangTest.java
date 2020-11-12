package com.pjzhong.lang;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.stream.Streams;
import org.junit.Test;

public class ApacheLangTest {

  @Test
  public void toStringTest() {
    System.out.println(new Person());
  }

  @Test(expected = NumberFormatException.class)
  public void failAbleStreamTest() {
    Streams.stream(Collections.singleton("a"))
        .map(Integer::parseInt)
        .forEach(System.out::println);
  }


  void updateCache() {
    Map<Long, CacheElement> elements = Collections.emptyMap();

    Iterator<Map.Entry<Long, CacheElement>> iterator = elements.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Long, CacheElement> entry = iterator.next();
      long id = entry.getKey();
      CacheElement element = entry.getValue();
      lock(id);
      try {
        if (element.isAlive()) {//是否存活
          if (element.isSave()) {//是否保存
            update(id);//入库
          }
        } else {
          update(id);//入库
          iterator.remove();//移除
        }
      } finally {
        unlock(id);
      }
    }
  }

  private void update(Object object) {
  }

  private void removeFromCache(Object id) {
  }


  private void lock(long id) {
  }

  private void unlock(long id) {
  }

}

class CacheElement {

  private int hits;
  private long accessTime;
  private Object data;

  public boolean isAlive() {
    return false;
  }

  public boolean isSave() {
    return false;
  }
}

class Person {

  private String name = "asdfasdf";
  private int age = 18;
  private int sex = 1;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
