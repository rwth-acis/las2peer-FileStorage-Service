package i5.las2peer.services.fileService;

import i5.las2peer.restMapper.HttpResponse;
import org.apache.commons.codec.binary.Base64;
import org.flywaydb.core.api.FlywayException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by adabru on 28.11.16.
 *
 * This class enables faster unit testing than requiring to start the las2peer node in DownloadService
 */
public class DownloadServiceHelper {
    private DatabaseManager dm;

    // TODO: user authentication

    public DownloadServiceHelper(DatabaseManager dm) {
        this.dm = dm;
    }

    public HttpResponse listAppFiles(int app) {
        try {
            ResultSet rs = dm.query("SELECT platform,version,name,size FROM files WHERE app is ? ORDER BY (platform,version)", app);
            String platform=null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JsonGenerator json = Json.createGeneratorFactory(null).createGenerator(baos);
            json.writeStartObject();
            while (rs.next()) {
                if (! rs.getString("platform").equals(platform)) {
                    if (platform != null)
                        json.writeEnd();
                    json.writeStartObject(platform = rs.getString("platform"));
                }
                json.writeStartObject(rs.getString("version"))
                        .write("name", rs.getString("name"))
                        .write("size", rs.getLong("size"))
                    .writeEnd();
            }
            if (platform == null) {
                json.writeEnd().close();
                return new HttpResponse("no files found for this app", HttpURLConnection.HTTP_NOT_FOUND);
            }
            json.writeEnd().writeEnd().close();

            return new HttpResponse(baos.toString("utf-8"), HttpURLConnection.HTTP_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new HttpResponse("error getting content", HttpURLConnection.HTTP_INTERNAL_ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new HttpResponse("error using utf-8", HttpURLConnection.HTTP_INTERNAL_ERROR);
        } catch (FlywayException e) {
            e.printStackTrace();
            return new HttpResponse("", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    public HttpResponse getFile(int app, String platform, String version) {
        try {
            ResultSet rs = dm.query("SELECT app,platform,version,size,name FROM files WHERE app is ? AND platform IS ? AND version IS ?", app, platform, version);
            if (!rs.next())
                return new HttpResponse("No such file", HttpURLConnection.HTTP_NOT_FOUND);
            HttpResponse resp = new HttpResponse("", HttpURLConnection.HTTP_OK);
            resp.setHeader("Content-Length", rs.getString("size"));
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + rs.getString("name") +"\"");

            String path = "./"+app+"/"+platform+"#"+version;
            byte[] fileContents = LocalFileManager.getFile(path);
            if (fileContents == null)
                return new HttpResponse("No such file", HttpURLConnection.HTTP_NOT_FOUND);

            String mime = MIMEMapper.getMIME(path);
            if (mime == null)
                mime = "text/plain";
            resp.setResult(new String(fileContents));
            System.out.println("equals: "+resp.getResult().equals("abcde"));
            resp.setHeader("Content-Type", mime);

            return resp;
        } catch (SQLException e) {
            e.printStackTrace();
            return new HttpResponse("Error getting file", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    public HttpResponse putFile(int app, String platform, String version, String name, String content) {
        try {
            dm.update("INSERT INTO files VALUES (?, ?, ?, ?, '', ?)", app, version, platform, name, content.length());
            File path = new File("./files/"+app+"/"+platform+"#"+version);
            LocalFileManager.writeFile(path, content);
        } catch (SQLException e) {
            e.printStackTrace();
            return new HttpResponse("Error uploading file", HttpURLConnection.HTTP_INTERNAL_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new HttpResponse("Error uploading file", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }

        return new HttpResponse("", HttpURLConnection.HTTP_CREATED);
    }

    /*
     * Function for testing
     */
    public JsonArray listApps() {
        try {
            ResultSet rs = dm.query("SELECT app FROM files");

            JsonArrayBuilder jab = Json.createArrayBuilder();
            while (rs.next()) {
                jab.add(Json.createObjectBuilder()
                    .add("app", rs.getInt("app"))
                    .build());
            }

            return jab.build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonStructure stringToJson(String s) {
        JsonReader jr = Json.createReader(new StringReader(s));
        JsonStructure js = jr.read();
        jr.close();

        return js;
    }
}
