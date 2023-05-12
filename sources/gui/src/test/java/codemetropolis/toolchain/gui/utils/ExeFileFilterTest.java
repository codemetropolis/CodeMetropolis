package codemetropolis.toolchain.gui.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for testing the {@link ExeFileFilter} for properly accepting and filtering files.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ExeFileFilterTest {

  private ExeFileFilter instance = new ExeFileFilter();

  /**
   * Creates a temp exe file, and checks if the {@link ExeFileFilter} properly accepts it.
   *
   * @throws IOException if it fails to create the temp file.
   */
  @Test
  public void testAcceptExeFile() throws IOException {
    File tempFile = File.createTempFile("test", ".exe");
    boolean result = instance.accept(tempFile);
    Assert.assertEquals(result, true);
  }

  /**
   * Creates a temp xml file, and checks if the {@link ExeFileFilter} properly filters it.
   *
   * @throws IOException if it fails to create the temp file.
   */
  @Test
  public void testAcceptNonExeFile() throws IOException {
    File tempFile = File.createTempFile("test", ".xml");
    boolean result = instance.accept(tempFile);
    Assert.assertEquals(result, false);
  }

  /**
   * Creates a temp directory, and checks if the {@link ExeFileFilter} properly accepts it.
   *
   * @throws IOException if it fails to create the temp directory.
   */
  @Test
  public void testAcceptDirectory() throws IOException {
    File tempFolder = Files.createTempDirectory("test").toFile();
    boolean result = instance.accept(tempFolder);
    Assert.assertEquals(result, true);
  }

}