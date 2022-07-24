package es.com.kete1987.sportmonks.library.net;

public class GetResponse 
{
	private int responseCode;
	private String response;
	private String headerTotal;
	private String headerPending;
	
	public GetResponse(String resp, int code, String ht, String hp)
	{
		response = resp;
		responseCode = code;
		headerTotal = ht;
		headerPending = hp;
	}
	public String getResponse()
	{
		return response;
	}
	public int getResponseCode()
	{
		return responseCode;
	}

	public String getHeaderTotal() {
		return headerTotal;
	}

	public String getHeaderPending() {
		return headerPending;
	}
}
