package pro.soccerama.library.net;

public class GetResponse 
{
	private int responseCode;
	private String response;
	
	public GetResponse(String resp, int code)
	{
		response = resp;
		responseCode = code;
	}
	public String getResponse()
	{
		return response;
	}
	public int getResponseCode()
	{
		return responseCode;
	}
}
