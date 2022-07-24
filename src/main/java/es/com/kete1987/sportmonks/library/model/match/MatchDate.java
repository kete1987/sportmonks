package es.com.kete1987.sportmonks.library.model.match;

public class MatchDate {
	private String date_time = null;
	private String date = null;
	private String time = null;
	private long timestamp = -1;
	private String timezone = null;
	
	public MatchDate() {}

	public String getDateTime() {
		return date_time;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getTimezone() {
		return timezone;
	}
	
	
}
