package codemetropolis.toolchain.gui.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
 * Test class for GuiUtils. Automatically find minecraft root on the computer
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class GuiUtilsTest {

  /**
   * Test automatic find of minecraft folder. The null is also valid return value, but we cannot build test enviroment
   * so we accept both cases: when the return is null or when its not null and then it's a valid directory.
   */
  @Test
  public void testFindMinecraftRoot() {
    String mcroot = GuiUtils.findMinecraftRoot();
    if (mcroot != null) {
      File mcfile = new File(mcroot);
      assertTrue(mcfile.isDirectory());
    }
  }

}
