package com.kete.sportmonks.library.util;

public class SportMonksException extends Exception
{
	private static final long serialVersionUID = -4146966752341203498L;
	private String error;

	public SportMonksException(String s)
	{
		super(s);
		error = s;
	}
	public String toString()
	{
		return error;
	}
}
