package i5.las2peer.services.fileService;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.*;

import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * LAS2peer Service
 * 
 */
@Path("files")
@Version("0.1")
public class DownloadService extends Service {

	// from properties file, injected by LAS2peer
	private String jdbcDriverClassName;
	private String jdbcLogin;
	private String jdbcPass;
	private String jdbcUrl;
	private String jdbcSchema;

	private DownloadServiceHelper dsh;

	public DownloadService() {
		setFieldValues();
		this.dsh = new DownloadServiceHelper(new DatabaseManager(jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema, "etc/db_migration", "database"));
	}

	/**
	 * This method is needed for every RESTful application in LAS2peer. There is no need to change!
	 * 
	 * @return the mapping
	 */
	public String getRESTMapping() {
		String result = "";
		try {
			result = RESTMapper.getMethodsAsXML(this.getClass());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}


	@GET
	public HttpResponse signOfLife() {
		return new HttpResponse(
				"LAS2peer file service for app distributables" +
						"\n\n" +
						"\nGET {app}                       : list available app files" +
						"\nGET {app}/{platform}/{version}  : download app file for platform and version" +
						"\nPUT {app}/{platform}/{version}  : upload app file for platform and version",
				HttpsURLConnection.HTTP_OK);
	}

	@GET
	@Path("{app}")
	public HttpResponse listAppFiles(@PathParam("app") int app) {
		return dsh.listAppFiles(app);
	}

	@GET
	@Path("{app}/{platform}/{version}")
	public HttpResponse getFile(@PathParam("app") int app, @PathParam("platform") String platform,
								@PathParam("version") String version) {
		return dsh.getFile(app, platform, version);
	}

	@PUT
	@Path("{app}/{platform}/{version}/{name}")
	public HttpResponse putFile(@PathParam("app") int app, @PathParam("platform") String platform
								,@PathParam("version") String version, @PathParam("name") String name
								,@ContentParam String content) {
		return dsh.putFile(app, platform, version, name, content);
	}

}
