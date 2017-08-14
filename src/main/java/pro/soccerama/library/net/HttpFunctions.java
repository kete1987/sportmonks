package pro.soccerama.library.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpFunctions 
{
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
		
        int responseCode = 0;
		StringBuffer response = new StringBuffer();
		
		try
		{
			responseCode = con.getResponseCode();
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;	      	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
		}
		catch (SocketTimeoutException e){responseCode = 504; response.append("Timeout en la respuesta: " + e.toString());}
		catch (Exception e){response.append("Error en la respuesta: " + e.toString());}

        return new GetResponse(response.toString(), responseCode);
    }
}
