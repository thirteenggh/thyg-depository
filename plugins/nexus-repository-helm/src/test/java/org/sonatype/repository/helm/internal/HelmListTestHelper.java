package org.sonatype.repository.helm.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelmListTestHelper
{
  public static List<Map<String, String>> getMaintainersList() {
    List<Map<String, String>> maintainers = new ArrayList<>();
    Map<String, String> map = new HashMap<>();

    map.put("email", "containers@bitnami.com");
    map.put("name", "Bitnami");

    maintainers.add(map);

    return maintainers;
  }

  public static List<String> getUrlList() {
    List<String> list = new ArrayList<>();

    list.add("mongodb-0.5.2.tgz");

    return list;
  }

  public static List<String> getSourcesList() {
    List<String> list = new ArrayList<>();

    list.add("https://github.com/bitnami/bitnami-docker-mongodb");

    return list;
  }
}
