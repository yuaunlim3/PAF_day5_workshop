package PAF.day5_workshop.Model.exception;

public class LimitException extends RuntimeException {
    public LimitException() {
        super();
    }

    public LimitException(String msg) {
        super(msg);
    }

    public LimitException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
