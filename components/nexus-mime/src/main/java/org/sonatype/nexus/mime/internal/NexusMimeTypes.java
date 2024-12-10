package org.sonatype.nexus.mime.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.sonatype.nexus.mime.MimeRule;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusMimeTypes
{

  private static Logger log = LoggerFactory.getLogger(NexusMimeTypes.class);

  public static final String BUILTIN_MIMETYPES_FILENAME = "builtin-mimetypes.properties";

  public static final String MIMETYPES_FILENAME = "nexus.mimetypes";

  private Map<String, MimeRule> extensions = Maps.newHashMap();

  public NexusMimeTypes() {
    load(BUILTIN_MIMETYPES_FILENAME);
    load(MIMETYPES_FILENAME);
  }

  private void load(final String filename) {
    final InputStream stream = this.getClass().getResourceAsStream("/" + filename);
    if (stream != null) {
      final Properties properties = new Properties();
      try {
        properties.load(stream);
        initMimeTypes(properties);
      }
      catch (IOException e) {
        if (log.isDebugEnabled()) {
          log.warn("Could not load " + MIMETYPES_FILENAME, e);
        }
        else {
          log.warn("Could not load " + MIMETYPES_FILENAME + ": {}", e.getMessage());
        }
      }
    }
  }

  @VisibleForTesting
  void initMimeTypes(final Properties properties) {
    final Set<String> keys = properties.stringPropertyNames();
    final Map<String, List<String>> overrides = Maps.newHashMap();
    final Map<String, List<String>> additional = Maps.newHashMap();

    for (String key : keys) {
      if (key.startsWith("override.")) {
        overrides.put(key.substring("override.".length()), types(properties.getProperty(key, null)));
      }
      else {
        additional.put(key, types(properties.getProperty(key, null)));
      }
    }

    for (String extension : overrides.keySet()) {
      final List<String> mimetypes = overrides.get(extension);

      if (additional.containsKey(extension)) {
        mimetypes.addAll(additional.get(extension));
        additional.remove(extension);
      }
      this.extensions.put(extension, new MimeRule(true, mimetypes));
    }

    for (String extension : additional.keySet()) {
      final List<String> mimetypes = additional.get(extension);
      this.extensions.put(extension, new MimeRule(false, mimetypes));
    }
  }

  private List<String> types(final String value) {
    if (value == null) {
      return Collections.emptyList();
    }
    else {
      return Lists.newArrayList(Splitter.on(",").split(value));
    }
  }

  /**
   * Method returning {@link MimeRule} for given extension (it must not contain leading dot!). If no rule exists, {@code
   * null} is returned.
   */
  @Nullable
  public MimeRule getMimeRuleForExtension(String extension) {
    while (!extension.isEmpty()) {
      if (extensions.containsKey(extension)) {
        return extensions.get(extension);
      }
      extension = Files.getFileExtension(extension);
    }
    return null;
  }
}
