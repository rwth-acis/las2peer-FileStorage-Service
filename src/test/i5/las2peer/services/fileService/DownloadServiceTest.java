package i5.las2peer.services.fileService;

import i5.las2peer.p2p.LocalNode;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.testing.MockAgentFactory;
import i5.las2peer.webConnector.WebConnector;
import i5.las2peer.webConnector.client.ClientResponse;
import i5.las2peer.webConnector.client.MiniClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Example Test Class demonstrating a basic JUnit test structure.
 * 
 * @author Peter de Lange
 *
 */
public class DownloadServiceTest
{

	private static final String HTTP_ADDRESS = "http://127.0.0.1";
	private static final int HTTP_PORT = WebConnector.DEFAULT_HTTP_PORT;

	private static LocalNode node;
	private static WebConnector connector;
	private static ByteArrayOutputStream logStream;

	private static UserAgent testAgent;
	private static final String testPass = "adamspass";

	private static final String testServiceClass = "i5.las2peer.services.fileService.DownloadService";

	/**
	 * Called before the tests start.
	 * 
	 * Sets up the node and initializes connector and users that can be used throughout the tests.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void startServer() throws Exception {

		// start node
		node = LocalNode.newNode();
		node.storeAgent(MockAgentFactory.getAdam());
		node.launch();

		ServiceAgent testService = ServiceAgent.generateNewAgent(testServiceClass, "a pass");
		testService.unlockPrivateKey("a pass");

		node.registerReceiver(testService);

		// start connector
		logStream = new ByteArrayOutputStream();

		connector = new WebConnector(true, HTTP_PORT, false, 1000);
		connector.setLogStream(new PrintStream(logStream));
		connector.start(node);
		Thread.sleep(100); // wait a second for the connector to become ready
		testAgent = MockAgentFactory.getAdam();

		connector.updateServiceList();
		// avoid timing errors: wait for the repository manager to get all services before continuing
		try
		{
			System.out.println("waiting..");
			Thread.sleep(6000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Called after the tests have finished.
	 * Shuts down the server and prints out the connector log file for reference.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void shutDownServer() throws Exception {

		connector.stop();
		node.shutDown();

		connector = null;
		node = null;

		LocalNode.reset();

		System.out.println("Connector-Log:");
		System.out.println("--------------");

		System.out.println(logStream.toString());

	}

	@Test
	public void testValidateLogin()
	{
		MiniClient c = new MiniClient();
		c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

		try
		{
			c.setLogin(Long.toString(testAgent.getId()), testPass);
			// ClientResponse result=c.sendRequest("GET", mainPath +"validate", "");
			// assertEquals(HttpURLConnection.HTTP_OK, result.getHttpCode());

		} catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception: " + e);
		}

	}

	@Test
	public void testAPI() {
		MiniClient c = new MiniClient();
		c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);
		try {
			c.setLogin(Long.toString(testAgent.getId()), testPass);
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
		ClientResponse res = c.sendRequest("PUT", "files/789/Windows%20XP/0.4/myApp.exe", "abcde");
		assertEquals(HttpURLConnection.HTTP_CREATED, res.getHttpCode());
		res = c.sendRequest("PUT", "files/789/Windows%20XP/1.5/myApp.exe", "fghij");
		assertEquals(HttpURLConnection.HTTP_CREATED, res.getHttpCode());
		res = c.sendRequest("GET", "files/789/Windows%20XP/0.4", "");
		assertEquals(HttpURLConnection.HTTP_OK, res.getHttpCode());
		assertEquals("abcde"+/*MiniClient specific https://github.com/rwth-acis/las2peer-WebConnector/blob/c29604e3d22d43fccd6766018c29fae588113a12/src/main/java/i5/las2peer/webConnector/client/MiniClient.java#L117*/"\r"
				, res.getResponse());
		res = c.sendRequest("GET", "files/789", "");
		assertEquals(HttpURLConnection.HTTP_OK, res.getHttpCode());
		assertEquals(DownloadServiceHelper.stringToJson("{\"Windows XP\":{\"0.4\":{\"name\":\"myApp.exe\",\"size\":5},\"1.5\":{\"name\":\"myApp.exe\",\"size\":5}}}")
			, DownloadServiceHelper.stringToJson(res.getResponse()));
	}
}
