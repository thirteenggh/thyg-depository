package org.sonatype.nexus.extdirect.model;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Ext Store load parameters.
 *
 * @see <a href="http://docs.sencha.com/extjs/4.2.2/#!/api/Ext.toolbar.Paging">Ext.toolbar.Paging</a>
 * @since 3.0
 */
public class StoreLoadParameters
{
  private Integer page;

  private Integer start;

  private Integer limit;

  private List<Sort> sort;

  private List<Filter> filter;

  private String query;

  public Integer getPage() {
    return page;
  }

  public void setPage(final Integer page) {
    this.page = page;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(final Integer start) {
    this.start = start;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(final Integer limit) {
    this.limit = limit;
  }

  public List<Filter> getFilters() {
    return filter;
  }

  public void setFilter(final List<Filter> filter) {
    this.filter = filter;
  }

  public String getFilter(String property) {
    checkNotNull(property, "property");
    if (filter != null) {
      for (Filter item : filter) {
        if (property.equals(item.getProperty())) {
          return item.getValue();
        }
      }
    }
    return null;
  }

  public List<Sort> getSort() {
    return sort;
  }

  public void setSort(final List<Sort> sort) {
    this.sort = sort;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return "StoreLoadParameters{" +
        "page=" + page +
        ", start=" + start +
        ", limit=" + limit +
        ", sort=" + sort +
        ", filter=" + filter +
        '}';
  }
  
  public static class Filter
  {
    private String property;

    private String value;

    public String getProperty() {
      return property;
    }

    public void setProperty(final String property) {
      this.property = property;
    }

    public String getValue() {
      return value;
    }

    public void setValue(final String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Filter{" +
          "property='" + property + '\'' +
          ", value='" + value + '\'' +
          '}';
    }
  }

  public static class Sort
  {
    private String property;

    private String direction;

    public String getProperty() {
      return property;
    }

    public void setProperty(final String property) {
      this.property = property;
    }

    public String getDirection() {
      return direction;
    }

    public void setDirection(final String value) {
      this.direction = value;
    }

    @Override
    public String toString() {
      return "Sort{" +
          "property='" + property + '\'' +
          ", direction='" + direction + '\'' +
          '}';
    }
  }
}
