package org.sonatype.nexus.internal.node;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.ssl.CertificateUtil;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FileKeyStoreStorageTest
    extends TestSupport
{
  private static final char[] STORE_PASSWORD = "very-secret".toCharArray();

  private static final String CERT_ALIAS = "test-cert";

  private File basedir = util.createTempDir();

  private File keyStoreFile = new File(basedir, "test.ks");

  private FileKeyStoreStorage storage;

  private KeyStore newKeyStore() throws Exception {
    KeyStore keyStore = KeyStore.getInstance("JKS");
    keyStore.load(null, STORE_PASSWORD);
    return keyStore;
  }

  private KeyStore newKeyStoreWithData() throws Exception {
    KeyStore keyStore = newKeyStore();
    KeyPairGenerator kpgen = KeyPairGenerator.getInstance("RSA");
    kpgen.initialize(512);
    KeyPair keyPair = kpgen.generateKeyPair();
    Certificate cert = CertificateUtil.generateCertificate(keyPair.getPublic(), keyPair.getPrivate(), "SHA1WITHRSA", 7,
        "testing", "Trust Repository", "TianheCloud", "Fulton", "MD", "USA");
    keyStore.setCertificateEntry(CERT_ALIAS, cert);
    return keyStore;
  }

  @Before
  public void setUp() {
    storage = new FileKeyStoreStorage(keyStoreFile);
  }

  @Test
  public void testExists() throws Exception {
    assertThat(storage.exists(), is(false));
    keyStoreFile.createNewFile();
    assertThat(storage.exists(), is(true));
  }

  @Test
  public void testModified() throws Exception {
    assertThat(storage.modified(), is(false));
    keyStoreFile.createNewFile();
    assertThat(storage.modified(), is(true));
  }

  @Test
  public void testLoad() throws Exception {
    KeyStore keyStore = newKeyStoreWithData();
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(keyStoreFile))) {
      keyStore.store(bos, STORE_PASSWORD);
    }
    assertThat(storage.modified(), is(true));
    keyStore = newKeyStore();
    storage.load(keyStore, STORE_PASSWORD);
    assertThat(keyStore.containsAlias(CERT_ALIAS), is(true));
    assertThat(storage.modified(), is(false));
  }

  @Test
  public void testSave() throws Exception {
    KeyStore keyStore = newKeyStoreWithData();
    storage.save(keyStore, STORE_PASSWORD);
    assertThat(storage.modified(), is(false));
    keyStore = newKeyStore();
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(keyStoreFile))) {
      keyStore.load(bis, STORE_PASSWORD);
    }
    assertThat(keyStore.containsAlias(CERT_ALIAS), is(true));
  }

  @Test
  public void testSave_CreateParentDirectories() throws Exception {
    keyStoreFile = new File(basedir, "sub/dir/test.ks");
    storage = new FileKeyStoreStorage(keyStoreFile);
    storage.save(newKeyStore(), STORE_PASSWORD);
    assertThat(keyStoreFile.isFile(), is(true));
  }
}
