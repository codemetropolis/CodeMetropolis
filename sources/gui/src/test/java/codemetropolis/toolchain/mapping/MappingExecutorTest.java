package codemetropolis.toolchain.mapping;

import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.mapping.model.Mapping;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;

import static codemetropolis.toolchain.mapping.MappingExecutor.MAX_SCALE;
import static codemetropolis.toolchain.mapping.MappingExecutor.MIN_SCALE;
import static org.junit.jupiter.api.Assertions.*;

class MappingExecutorTest {

    /**
     * testing if required resources exist.
     */

    @Test
    void execute() {

        assertNotNull(Resources.get("reading_mapping"));
        assertNotNull(Resources.get("reading_mapping_done"));
        assertNotNull(Resources.get("reading_graph"));
        assertNotNull(Resources.get("reading_graph_done"));
        assertNotNull(Resources.get("linking_metrics"));
        assertNotNull(Resources.get("linking_metrics_done"));
        assertNotNull(Resources.get("invalid_hierarchy_error"));
        assertNotNull(Resources.get("mapping_printing_output"));
        assertNotNull(Resources.get("cmxml_writer_error"));
        assertNotNull(Resources.get("mapping_printing_output_done"));

    }
}