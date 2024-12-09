package org.sonatype.nexus.security;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.asCharSource;

/**
 * File-backed {@link PhraseService}.
 *
 * @since 3.8
 */
@Named("file")
@Singleton
public class FilePhraseService
    extends AbstractPhraseService
{
  private final Supplier<String> masterPhraseSupplier;

  @Inject
  public FilePhraseService(@Named("${nexus.security.masterPhraseFile}") @Nullable final File masterPhraseFile) {
    super(masterPhraseFile != null);
    this.masterPhraseSupplier = Suppliers.memoize(new Supplier<String>()
    {
      @Override
      public String get() {
        try {
          return asCharSource(masterPhraseFile, UTF_8).read().trim();
        }
        catch (IOException e) {
          throw Throwables.propagate(e);
        }
      }
    });
  }

  @Override
  protected String getMasterPhrase() {
    return masterPhraseSupplier.get();
  }
}
