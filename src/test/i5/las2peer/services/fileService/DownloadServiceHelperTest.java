package i5.las2peer.services.fileService;

import i5.las2peer.restMapper.HttpResponse;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by adabru on 29.11.16.
 */
public class DownloadServiceHelperTest {

    // to inspect database in tests, replace
    //
    // DatabaseManager dm = new DatabaseManager("org.h2.Driver", "sa", "", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "testSchema");
    //
    // with
    //
    // DatabaseManager dm = null;
    // try {
    //  DatabaseManager dm = new DatabaseManager("org.h2.Driver", "sa", "", "jdbc:h2:tcp://localhost/mem:test;DB_CLOSE_DELAY=-1", "testSchema");
    //  dm.resetTables();
    // } catch (SQLException e) {
    //  fail(e.getMessage());
    // }


    @Test
    public void testListApps() {
        try {
            DatabaseManager dm = new DatabaseManager("sa", "", "jdbc:h2:mem:downloadservicehelpertest_0;DB_CLOSE_DELAY=-1", "testSchema", "etc/db_migration", "database");
            DownloadServiceHelper dsh = new DownloadServiceHelper(dm);

            dm.update("INSERT INTO files VALUES (2,'a','b','c','d',7)");
            dm.update("INSERT INTO files VALUES (3,'a','b','c','d',7)");
            dm.update("INSERT INTO files VALUES (4,'a','b','c','d',7)");

            assertEquals(DownloadServiceHelper.stringToJson("[{\"app\":2},{\"app\":3},{\"app\":4}]")
                    ,dsh.listApps());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPutGetList() {
        DatabaseManager dm = new DatabaseManager("sa", "", "jdbc:h2:mem:downloadservicehelpertest_1;DB_CLOSE_DELAY=-1", "testSchema", "etc/db_migration", "database");
        DownloadServiceHelper dsh = new DownloadServiceHelper(dm);

        assertEquals(HttpURLConnection.HTTP_CREATED
                , dsh.putFile(456, "Windows 8", "0.4", "anatomy.exe", "123").getStatus());
        assertEquals(HttpURLConnection.HTTP_CREATED
                , dsh.putFile(456, "Windows XP", "0.4", "anatomy_x86.exe", "1234").getStatus());
        assertEquals(HttpURLConnection.HTTP_CREATED
                , dsh.putFile(456, "Windows 8", "0.5", "anatomy.exe", "12345").getStatus());
        HttpResponse r = dsh.getFile(456, "Windows 8", "0.4");
        assertEquals(HttpURLConnection.HTTP_OK, r.getStatus());
        assertEquals("123", r.getResult());

        assertEquals(HttpURLConnection.HTTP_NOT_FOUND
                , dsh.getFile(684, "a", "a").getStatus());

        assertEquals( dsh.stringToJson("{\"Windows 8\":{\"0.4\":{\"name\":\"anatomy.exe\",\"size\":3},\"0.5\":{\"name\":\"anatomy.exe\",\"size\":5}},\"Windows XP\":{\"0.4\":{\"name\":\"anatomy_x86.exe\",\"size\":4}}}")
                    , dsh.stringToJson(dsh.listAppFiles(456).getResult()));
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, dsh.listAppFiles(123).getStatus());
    }
}