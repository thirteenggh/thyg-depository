package org.sonatype.nexus.quartz.internal.orient;

import org.quartz.Calendar;

/**
 * {@link Calendar} entity.
 *
 * @since 3.0
 */
public class CalendarEntity
  extends MarshalledEntity<Calendar>
{
  private String name;

  public CalendarEntity() {
    // empty
  }

  public CalendarEntity(final String name, final Calendar calendar) {
    setName(name);
    setValue(calendar);
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "name='" + name + '\'' +
        ", value=" + getValue() +
        '}';
  }
}
