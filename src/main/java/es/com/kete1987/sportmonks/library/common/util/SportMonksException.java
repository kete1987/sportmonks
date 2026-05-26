package es.com.kete1987.sportmonks.library.common.util;

public class SportMonksException extends Exception {
    private static final long serialVersionUID = -4146966752341203498L;

    public SportMonksException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
