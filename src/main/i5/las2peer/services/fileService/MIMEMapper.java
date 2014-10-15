package i5.las2peer.services.fileService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author Alexander
 */
public class MIMEMapper
{
    public static final String DEFAULT_MIME = "text/plain";
    private static HashMap<String,String> typeMapping=new HashMap<String, String>();
    private static MIMEMapper mapper;
    private MIMEMapper()
    {
        initMapping();
    }
    private void initMapping()
    {

		String mimeTypes= null;
		try{
			mimeTypes = new String(LocalFileManager.getFile(new File("./etc/MIME.txt")), "UTF-8");
		}catch (UnsupportedEncodingException e){
			mimeTypes=null;
		}

		if(mimeTypes==null)//backup plan if no file
        {
            typeMapping.put("html","text/html");
            typeMapping.put("js","text/javascript");
            typeMapping.put("css","text/css");
            typeMapping.put("txt","text/plain");
        }
        else
        {
            String[] mimeLines=mimeTypes.split("\n");
            for(int i = 0; i < mimeLines.length; i++)
            {
                String[] lineParts= mimeLines[i].trim().split(" ");
                if(lineParts.length>1)
                {
                    String mime=lineParts[0];
                    for(int j = lineParts.length-1; j > 0; j--)
                    {
                        typeMapping.put(lineParts[j].trim(),mime);
                    }
                }

            }
        }
    }

    private static MIMEMapper getMapper()
    {
        if(mapper==null)
            mapper=new MIMEMapper();

        return mapper;
    }


    public static String getMIME(String path)
    {
        getMapper();

        int lastDot = path.indexOf('.');
        if (lastDot>0)//file
        {
            String extension=path.substring(lastDot+1).trim().toLowerCase();
            String mime=typeMapping.get(extension);
            if(mime!=null)
            {
                return mime;
            }
            else
            {
                return DEFAULT_MIME;
            }

        }
        else return null;
    }
}
