package com.hubspot.jinjava.lib.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;

public class TypeOfFilterTest {

  JinjavaInterpreter interpreter;
  TypeOfFilter filter;

  @Before
  public void setup() {
    interpreter = new Jinjava().newInterpreter();
    filter = new TypeOfFilter();
  }

  @Test
  public void testString() {
    assertThat(filter.filter(" foo  ", interpreter)).isEqualTo("str");
    assertThat(interpreter.renderFlat("{{ \"123\"|typeof }}")).isEqualTo("str");
  }

  @Test
  public void testInteger() {
    assertThat(filter.filter(123, interpreter)).isEqualTo("int");
  }

  @Test
  public void testLong() {
    assertThat(filter.filter(123L, interpreter)).isEqualTo("long");
  }


  @Test
  public void testDouble() {
    assertThat(filter.filter(123.3345d, interpreter)).isEqualTo("float");
  }

  @Test
  public void testDate() {
    assertThat(filter.filter(ZonedDateTime.parse("2013-11-06T14:22:00.000+00:00[UTC]"), interpreter)).isEqualTo("datetime");
  }

  @Test
  public void testList() {
    assertThat(filter.filter(new ArrayList<>(), interpreter)).isEqualTo("list");
    assertThat(interpreter.renderFlat("{{ [1,2,3]|typeof }}")).isEqualTo("list");
  }

  @Test
  public void testDict() throws Exception {
    assertThat(filter.filter(new HashMap<>(), interpreter)).isEqualTo("dict");
  }

  @Test
  public void testNumber() throws Exception {
    assertThat(interpreter.renderFlat("{{ 123|typeof }}")).isEqualTo("long");
    assertThat(interpreter.renderFlat("{{ 3446.5|typeof }}")).isEqualTo("float");
    assertThat(interpreter.renderFlat("{{ (123/3446.5)|typeof }}")).isEqualTo("float");
  }

  @Test
  public void testBool() {
    assertThat(interpreter.renderFlat("{{ 1|bool|typeof }}")).isEqualTo("bool");
    assertThat(interpreter.renderFlat("{{ 0|bool|typeof }}")).isEqualTo("bool");
  }
}
