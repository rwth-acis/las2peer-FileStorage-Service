package i5.las2peer.services.fileService;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by adabru on 28.11.16.
 */
public class DatabaseManagerTest {
    @BeforeClass public static void setupMigration() {
        File f = new File("./tmp/db_migration/V1__init.sql");
        f.getParentFile().mkdirs();
        try( FileWriter out = new FileWriter(f) ) {
            out.write( "" +
                    //+ "DROP TABLE IF EXISTS files\n" +
                    // Beware of setting table and schema names into quotes, as h2 has default option DATABASE_TO_UPPER=true"
                    "CREATE TABLE files (\n" +
                    "  `app` INT NOT NULL\n" +
                    ", `version` VARCHAR(255)\n" +
                    ", `platform` VARCHAR(255)\n" +
                    ", `name` VARCHAR(255)\n" +
                    ", `oidc_sub` VARCHAR(255)\n" +
                    ", `size` BIGINT\n" +
                    ", CONSTRAINT pk_fileID PRIMARY KEY (app,platform,version)\n" +
                    ");"
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DatabaseManager getMock(int testNumber) {
        return new DatabaseManager(
                "sa"
                , ""
                , "jdbc:h2:mem:databasemanagertest_"+testNumber+";DB_CLOSE_DELAY=-1"
                , "testSchema"
                , "./tmp/db_migration"
                , "./tmp/database"
        );
    }

    @Test
    public void testQueryAndUpdate() {
        DatabaseManager dm = getMock(1);
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
        DatabaseManager dm = getMock(2);
        try {
            // table 'files' must already be created by migration
            dm.query("SELECT * FROM files");
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testBackupAndRestore() {
        DatabaseManager dm = getMock(3);
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