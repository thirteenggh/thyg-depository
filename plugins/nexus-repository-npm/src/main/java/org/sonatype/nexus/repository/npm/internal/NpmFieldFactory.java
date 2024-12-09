package org.sonatype.nexus.repository.npm.internal;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import org.sonatype.nexus.repository.Repository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import static java.util.Arrays.asList;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.META_ID;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.META_REV;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.rewriteTarballUrl;

/**
 * Simple factory class for providing handlers that are common for manipulating NPM JSON Fields.
 *
 * @since 3.16
 */
public class NpmFieldFactory
{
  public static final NpmFieldDeserializer NULL_DESERIALIZER = new NpmFieldDeserializer()
  {
    @Override
    public Object deserialize(final String fieldName,
                              final Object defaultValue,
                              final JsonParser parser,
                              final DeserializationContext context,
                              final JsonGenerator generator)
    {
      return null;
    }
  };

  public static final NpmFieldDeserializer SKIP_OBJECT_DESERIALIZER = new SkipObjectNpmFieldDeserializer();

  public static final NpmFieldMatcher REMOVE_ID_MATCHER = removeFieldMatcher(META_ID, "/" + META_ID);

  public static final NpmFieldMatcher REMOVE_REV_MATCHER = removeFieldMatcher(META_REV, "/" + META_REV);

  public static final List<NpmFieldMatcher> REMOVE_DEFAULT_FIELDS_MATCHERS = asList(REMOVE_ID_MATCHER,
      REMOVE_REV_MATCHER);

  public static final String DIST_TAGS_LATEST = "/dist-tags/latest";

  public static final String LATEST = "latest";

  private NpmFieldFactory() {
    // factory constructor
  }

  public static NpmFieldMatcher removeFieldMatcher(final String fieldName, final String pathRegex) {
    return new NpmFieldMatcher(fieldName, pathRegex, NULL_DESERIALIZER);
  }

  public static NpmFieldMatcher removeObjectFieldMatcher(final String fieldName, final String pathRegex) {
    return new NpmFieldMatcher(fieldName, pathRegex, SKIP_OBJECT_DESERIALIZER);
  }

  public static NpmFieldUnmatcher missingFieldMatcher(final String fieldName,
                                                      final String pathRegex,
                                                      final Supplier<Object> supplier)
  {
    return new NpmFieldUnmatcher(fieldName, pathRegex, missingFieldDeserializer(supplier));
  }

  public static NpmFieldDeserializer missingFieldDeserializer(final Supplier<Object> supplier) {
    NpmFieldDeserializer deserializer = new NpmFieldDeserializer()
    {
      @Override
      public Object deserializeValue(final Object defaultValue) {
        return supplier.get();
      }
    };
    return deserializer;
  }

  public static NpmFieldUnmatcher missingRevFieldMatcher(final Supplier<Object> supplier) {
    return missingFieldMatcher(META_REV, "/" + META_REV, supplier);
  }

  public static NpmFieldMatcher rewriteTarballUrlMatcher(final Repository repository, final String packageId) {
    return rewriteTarballUrlMatcher(repository.getName(), packageId);
  }

  public static NpmFieldMatcher rewriteTarballUrlMatcher(final String repositoryName, final String packageId) {
    return new NpmFieldMatcher("tarball", "/versions/(.*)/dist/tarball",
        rewriteTarballUrlDeserializer(repositoryName, packageId));
  }

  public static NpmFieldDeserializer rewriteTarballUrlDeserializer(final String repositoryName,
                                                                   final String packageId)
  {
    return new NpmFieldDeserializer()
    {
      @Override
      public Object deserializeValue(final Object defaultValue) {
        return rewriteTarballUrl(repositoryName, packageId, super.deserializeValue(defaultValue).toString());
      }
    };
  }

  /**
   * Rewrites the dist-tags/latest field with the latest, cataloged version
   *
   * @since 3.29
   */
  public static NpmFieldMatcher rewriteLatest(final List<String> nonCatalogedVersions, final List<String> allVersions) {
    return new NpmFieldMatcher(LATEST, DIST_TAGS_LATEST, latestFieldDeserializer(nonCatalogedVersions, allVersions));
  }

  private static NpmFieldDeserializer latestFieldDeserializer(final List<String> nonCatalogedVersions,
                                                              final List<String> allVersions)
  {
    return new NpmFieldDeserializer()
    {
      @Override
      public Object deserialize(final String fieldName,
                                final Object defaultValue,
                                final JsonParser parser,
                                final DeserializationContext context,
                                final JsonGenerator generator) throws IOException
      {
        String overrideDefaultValue = (String) defaultValue;
        if (nonCatalogedVersions.contains(defaultValue)) {
          for (int i = allVersions.size() - 1; i >= 0; i--) {
            String next = allVersions.get(i);
            if (!nonCatalogedVersions.contains(next)) {
              overrideDefaultValue = next;
              break;
            }
          }
        }
        return super.deserialize(fieldName, overrideDefaultValue, parser, context, generator);
      }
    };
  }

  public static class SkipObjectNpmFieldDeserializer extends NpmFieldDeserializer
  {
    @Override
    public Object deserialize(final String fieldName,
                              final Object defaultValue,
                              final JsonParser parser,
                              final DeserializationContext context,
                              final JsonGenerator generator) throws IOException
    {
      parser.skipChildren();
      return null;
    }
  }
}
