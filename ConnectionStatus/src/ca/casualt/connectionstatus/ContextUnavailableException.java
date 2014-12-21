package ca.casualt.connectionstatus;

/**
 * Throw this if unable to provide context.
 * 
 * @author Tony
 */
public final class ContextUnavailableException extends Exception {
    /**
     * Generated.
     */
    private static final long serialVersionUID = 8044895481953134187L;

    /**
     * Default Constructor.
     */
    public ContextUnavailableException() {
    }

    /**
     * @param e
     *            pass-along.
     */
    public ContextUnavailableException(Exception e) {
        super(e);
    }

}
