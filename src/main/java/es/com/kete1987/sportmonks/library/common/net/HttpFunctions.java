package es.com.kete1987.sportmonks.library.common.net;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpFunctions 
{
	private static final Logger logger = LoggerFactory.getLogger(HttpFunctions.class);
	public static GetResponse get(String url) throws IOException
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        String USER_AGENT = "Mozilla/5.0";
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        //Set timeouts
        con.setConnectTimeout(30000);
        con.setReadTimeout(30000);

        String endPoint = url.substring(0, url.indexOf("?")).replaceAll(Constants.baseURLV2, "");
        int responseCode = 0;
		StringBuffer response = new StringBuffer();
		String headerTotal = null;
		String headerPending = null;
		
		try {
			responseCode = con.getResponseCode();
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;	      	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
			headerTotal = con.getHeaderField("X-RateLimit-Limit");
	        headerPending = con.getHeaderField("X-RateLimit-Remaining");
	        logger.info("Endpoint: {} | Pending requests: {}/{}", endPoint, headerPending, headerTotal);
	        in.close();
		}
		catch (SocketTimeoutException e){responseCode = 504; response.append("Timeout en la respuesta: ").append(e.toString());}
		catch (Exception e){response.append("Error en la respuesta: ").append(e.toString());}
		logger.debug("URL: {} Response: {}", url, responseCode);
        return new GetResponse(response.toString(), responseCode, headerTotal, headerPending);
    }
}
