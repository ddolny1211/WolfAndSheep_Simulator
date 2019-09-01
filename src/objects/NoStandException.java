package objects;

public class NoStandException extends RuntimeException {
    private static final long serialVersionUID = 6388525547948327400L;

    public NoStandException(String message) {
        super(message);
    }

}
