package i5.las2peer.services.fileService;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adabru on 28.11.16.
 */
public class DatabaseManagerTest {

    @Test
    public void testQueryAndUpdate() {
        DatabaseManager dm = new DatabaseManager("sa", "", "jdbc:h2:mem:databasemanagertest_0;DB_CLOSE_DELAY=-1", "testSchema");
        try {
            assertEquals(2,
                dm.update("INSERT INTO files VALUES" +
                        " (0, '0.4', 'Windows XP 32', 'x86.exe','', '1000')" +
                        ",(0, '1.0.7', 'Windows XP 64', 'amd64.exe','', '2000')"));
            ResultSet rs = dm.query("SELECT * FROM files");
            int rowCount = 0;
            while(rs.next()) rowCount++;
            assertEquals(2, rowCount);
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testMigration() {
        DatabaseManager dm = new DatabaseManager("sa", "", "jdbc:h2:mem:databasemanagertest_1;DB_CLOSE_DELAY=-1", "testSchema");
        try {
            // table 'files' must already be created by migration
            dm.query("SELECT * FROM files");
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testBackupAndRestore() {
        DatabaseManager dm = new DatabaseManager("sa", "", "jdbc:h2:mem:databasemanagertest_2;DB_CLOSE_DELAY=-1", "testSchema");
        try {
            dm.update("INSERT INTO files VALUES (2,'a','b','c','d',7)");
            dm.backup();
            dm.update("INSERT INTO files VALUES (4,'a','b','c','d',7)");
            ResultSet rs = dm.query("SELECT app FROM files");
            assertTrue(rs.next() && rs.next() && !rs.next());
            dm.restore();
            rs = dm.query("SELECT app FROM files");
            assertTrue(rs.next() && !rs.next());
        } catch (SQLException e) {
            fail(e.toString());
        }
    }
}