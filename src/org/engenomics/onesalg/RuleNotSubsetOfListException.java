package org.engenomics.onesalg;

public class RuleNotSubsetOfListException extends Throwable {
    public RuleNotSubsetOfListException() {
    }

    public RuleNotSubsetOfListException(String message) {
        super(message);
    }

    public RuleNotSubsetOfListException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleNotSubsetOfListException(Throwable cause) {
        super(cause);
    }

    public RuleNotSubsetOfListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
