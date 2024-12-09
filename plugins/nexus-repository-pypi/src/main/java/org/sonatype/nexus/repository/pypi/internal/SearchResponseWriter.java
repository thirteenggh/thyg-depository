package org.sonatype.nexus.repository.pypi.internal;

import javax.xml.stream.XMLStreamWriter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class used to write PyPI search results in an XML-RPC format.
 *
 * @since 3.1
 */
final class SearchResponseWriter
    implements AutoCloseable
{
  private final XMLStreamWriter writer;

  private boolean writtenEntry = false;

  /**
   * Constructor.
   */
  public SearchResponseWriter(final XMLStreamWriter writer) {
    this.writer = checkNotNull(writer);
  }

  /**
   * Writes the beginning portion of the response.
   */
  public void writePrologue() throws Exception {
    writer.writeStartElement("methodResponse");
    writer.writeStartElement("params");
    writer.writeStartElement("param");
    writer.writeStartElement("value");
    writer.writeStartElement("array");
    writer.writeStartElement("data");
  }

  /**
   * Writes the ending portion of the response.
   */
  public void writeEpilogue() throws Exception {
    if (writtenEntry) {
      writer.writeEndElement();
    }
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndElement();
    writer.flush();
  }

  /**
   * Writes a specific entry into the search response.
   */
  public void writeEntry(final PyPiSearchResult result) throws Exception {
    checkNotNull(result);
    if (!writtenEntry) {
      writer.writeStartElement("value");
      writtenEntry = true;
    }
    writer.writeStartElement("struct");
    writeMember("_pypi_ordering", "boolean", "0");
    writeMember("name", "string", result.getName());
    writeMember("version", "string", result.getVersion());
    writeMember("summary", "string", result.getSummary());
    writer.writeEndElement();
  }

  /**
   * Writes a specific member field to the output.
   */
  private void writeMember(final String name, final String type, final String value) throws Exception {
    checkNotNull(name);
    checkNotNull(type);
    checkNotNull(value);
    writer.writeStartElement("member");
    writer.writeStartElement("name");
    writer.writeCharacters(name);
    writer.writeEndElement();
    writer.writeStartElement("value");
    writer.writeStartElement(type);
    writer.writeCharacters(value);
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndElement();
  }

  @Override
  public void close() throws Exception {
    writer.close();
  }
}
