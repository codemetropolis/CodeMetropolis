package codemetropolis.toolchain.rendering.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to hold PrintWriter instances for the rendering. Previously, these files were (re-)opened and
 * closed for every single {@link codemetropolis.toolchain.rendering.model.primitive.Boxel}. Instead of that, it now
 * holds all of the references for each file, and serves them to the Boxel for writing. When all Boxels have been
 * written, all these files can be flushed and closed at once.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>} {@literal <bankeszi.adam@gmail.com>}
 */
public class BlockCsvWriterHolder {

  /** Holds the PrintWriter references for the blocks csv files. */
  private static Map<String, PrintWriter> writers = new HashMap<String, PrintWriter>();

  /**
   * Requests a {@link PrintWriter} for the given filename. On the first request it attempts to create the file and
   * stores the associated {@link PrintWriter} reference. When the same file is requested a second time, it simply
   * returns the already open {@link PrintWriter}.
   *
   * @param directory The directory in which these files shall be stored.
   * @param filename The requested file's name.
   * @return The {@link PrintWriter} for the requested file.
   * @throws FileNotFoundException if it fails to create the file (or the parent directory). See
   *   {@link #createAndStoreWriter(File, String)} for more information.
   */
  public static PrintWriter requestFile(File directory, String filename) throws FileNotFoundException {
    if (writers.containsKey(filename)) {
      return writers.get(filename);
    } else {
      return createAndStoreWriter(directory, filename);
    }
  }

  /**
   * Closes all {@link PrintWriter} instances and clears the storage.
   */
  public static void closeWriters() {
    writers.values().forEach(writer -> writer.close());
    writers.clear();
  }

  /**
   * Creates a new {@link PrintWriter} instance for the specific filename and adds it to the storage.
   *
   * @param directory The directory in which the file should be created.
   * @param filename The filename that should be used.
   * @return The new {@link PrintWriter} instance for the newly created file.
   * @throws FileNotFoundException if it fails to create the file (or the parent directory). Its most likely due to
   *   insufficent user permissions for the given path or insufficent drive space.
   */
  private static PrintWriter createAndStoreWriter(File directory, String filename) throws FileNotFoundException {
    directory.mkdirs();
    File file = new File(directory, filename);
    PrintWriter writer = new PrintWriter(file);
    writers.put(filename, writer);
    return writer;
  }

}
