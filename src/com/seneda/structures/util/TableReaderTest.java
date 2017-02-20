package com.seneda.structures.util;

import com.seneda.structures.util.TableReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by seneda on 20/02/17.
 */
public class TableReaderTest {
    @Test
    public void testTableReader() throws Exception {
        TableReader t = new TableReader("test.csv", "src/com/seneda/structures/util");
        assertEquals("Test", t.tableName);
        assertEquals("made up", t.tableSource);
        assertEquals("multi column", t.tableLayout);

        assertEquals(6.0, t.get("Row2", "Col3"), 0.000001);
    }
    @Test
    public void testTableReaderSingleColumn() throws Exception {
        TableReader t = new TableReader("test_single.csv", "src/com/seneda/structures/util");
        assertEquals("Test Single", t.tableName);
        assertEquals("made up", t.tableSource);
        assertEquals("single column", t.tableLayout);

        assertEquals(2, t.get("Row2"), 0.000001);
    }
}