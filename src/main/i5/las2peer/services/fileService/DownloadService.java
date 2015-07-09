package i5.las2peer.services.fileService;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.GET;
import i5.las2peer.restMapper.annotations.Path;
import i5.las2peer.restMapper.annotations.PathParam;
import i5.las2peer.restMapper.annotations.Version;

/**
 * 
 * LAS2peer Service
 * 
 */
@Path("files")
@Version("0.1")
public class DownloadService extends Service {

	public DownloadService()
	{

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
	public HttpResponse getFile0()
	{
		return getHttpResponse("");

	}

	@GET
	@Path("{p1}")
	public HttpResponse getFile1(@PathParam("p1") String p1)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}")
	public HttpResponse getFile2(@PathParam("p1") String p1, @PathParam("p2") String p2)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}")
	public HttpResponse getFile3(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}")
	public HttpResponse getFile4(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}")
	public HttpResponse getFile5(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p5);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}/{p6}")
	public HttpResponse getFile6(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5, @PathParam("p6") String p6)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p5).append("/");
		sb.append(p6);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}/{p6}/{p7}")
	public HttpResponse getFile7(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5, @PathParam("p6") String p6,
			@PathParam("p7") String p7)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p5).append("/");
		sb.append(p6).append("/");
		sb.append(p7);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}/{p6}/{p7}/{p8}")
	public HttpResponse getFile8(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5, @PathParam("p6") String p6,
			@PathParam("p7") String p7, @PathParam("p8") String p8)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p4).append("/");
		sb.append(p5).append("/");
		sb.append(p7).append("/");
		sb.append(p8);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}/{p6}/{p7}/{p8}/{p9}")
	public HttpResponse getFile9(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5, @PathParam("p6") String p6,
			@PathParam("p7") String p7, @PathParam("p8") String p8,
			@PathParam("p9") String p9)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p5).append("/");
		sb.append(p6).append("/");
		sb.append(p7).append("/");
		sb.append(p8).append("/");
		sb.append(p9);
		return getHttpResponse(sb.toString());

	}

	@GET
	@Path("{p1}/{p2}/{p3}/{p4}/{p5}/{p6}/{p7}/{p8}/{p9}/{p10}")
	public HttpResponse getFile10(@PathParam("p1") String p1, @PathParam("p2") String p2,
			@PathParam("p3") String p3, @PathParam("p4") String p4,
			@PathParam("p5") String p5, @PathParam("p6") String p6,
			@PathParam("p7") String p7, @PathParam("p8") String p8,
			@PathParam("p9") String p9, @PathParam("p10") String p10)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p1).append("/");
		sb.append(p2).append("/");
		sb.append(p3).append("/");
		sb.append(p4).append("/");
		sb.append(p5).append("/");
		sb.append(p6).append("/");
		sb.append(p7).append("/");
		sb.append(p8).append("/");
		sb.append(p9).append("/");
		sb.append(p10);
		return getHttpResponse(sb.toString());

	}

	public String getFileSimple(String path)
	{
		return getHttpResponse(path).getResult();
	}

	private HttpResponse getHttpResponse(String path)
	{
		HttpResponse response;

		int lastDot = path.lastIndexOf('.');
		if (lastDot > 0 && path.charAt(lastDot - 1) != '.')// file
		{

			byte[] fileContents = getFile(path);
			String fileContentsString = "";
			String getMIME = MIMEMapper.getMIME(path);
			if (fileContents != null)
			{
				if (getMIME.startsWith("text"))
				{
					try {
						fileContentsString = new String(fileContents, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						fileContentsString = "";
					}
				}
				else
				{

					fileContentsString = "data:" + getMIME + ";base64," + Base64.encodeBase64String(fileContents);
				}
				response = new HttpResponse(fileContentsString, HttpURLConnection.HTTP_OK);
				response.setHeader("content-type", getMIME);
			}
			else
			{
				response = new HttpResponse("No such file: " + path, HttpURLConnection.HTTP_NOT_FOUND);
			}
		}
		else// dir
		{
			String dirContents = getDir(path);
			if (dirContents != null)
			{
				response = new HttpResponse(dirContents, HttpURLConnection.HTTP_OK);
				response.setHeader("content-type", "text/html");
			}
			else
			{
				response = new HttpResponse("No such directory: " + path, HttpURLConnection.HTTP_NOT_FOUND);
			}
		}
		return response;
	}

	private byte[] getFile(String path)
	{
		return LocalFileManager.getFile(path);
	}

	private String getDir(String path)
	{
		StringBuilder sb = new StringBuilder();
		List<String> files = LocalFileManager.getDir(path);
		if (files == null)
			return null;
		if (path.trim().length() > 0)
			sb.append("<a href=\"../\">..</a><br/>");
		for (String file : files)
		{
			sb.append("<a href=\"");
			sb.append(file);
			if (!file.contains("."))
				sb.append("/");
			sb.append("\">").append(file).append("</a><br>");
		}
		return sb.toString();

	}

}
