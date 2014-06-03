package i5.las2peer.services.fileService;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander
 */
public class MIMEMapperTest
{
    @Test
    public void testMIME()
    {
        String result=MIMEMapper.getMIME("a/b/c.html");
        assertEquals("text/html",result);
        result=MIMEMapper.getMIME("a/b/c.exe");
        assertEquals("application/x-msdownload",result);
    }
    /*@Test
    public void testLocal()
    {
        DownloadService service = new DownloadService();
        System.out.println(service.getFileSimple(""));

    }*/
}
