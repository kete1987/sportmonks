package es.com.kete1987.sportmonks.library.util;

public class MatchStatus 
{
	public static String NOT_STARTED = "NS";
	public static String LIVE = "LIVE";
	public static String HALF_TIME = "HT";
	public static String FULL_TIME = "FT";
	public static String EXTRA_TIME = "ET";
	public static String PENALTIES = "PEN_LIVE";
	public static String FINISHED_AFTER_EXTRA_TIME = "AET";
	public static String WAITING_EXTRA_TIME = "BREAK";
	public static String FULL_TIME_AFTER_PENALTIES = "FT_PEN";
	public static String CANCELLED = "CANCL";
	public static String POSTPONED = "POSTP";
	public static String INTERRUPTED = "INT";
	public static String ABANDONED = "ABAN";
	public static String SUSPENDED = "SUSP";
	public static String AWARDED = "AWARDED";
	public static String DELAYED = "DELAYED";
	public static String DELETED = "Deleted";
	public static String TBA = "TBA";
	public static String WO = "WO";
	public static String AU = "AU";

	public static boolean isLiveMatch(String status) {
		return status.equals(LIVE) || status.equals(HALF_TIME) || status.equals(EXTRA_TIME) || status.equals(PENALTIES) || status.equals(WAITING_EXTRA_TIME);
	}

	public static boolean isMatchFinished(String status) {
		return status.equals(FULL_TIME) || status.equals(FINISHED_AFTER_EXTRA_TIME) || status.equals(FULL_TIME_AFTER_PENALTIES);
	}
}
