package es.com.kete1987.sportmonks.library.common.util;

public class SportMonksException extends Exception {
    private static final long serialVersionUID = -4146966752341203498L;
    private final String error;

    public SportMonksException(String s) {
        super(s);
        error = s;
    }

    public String toString() {
        return error;
    }
}
