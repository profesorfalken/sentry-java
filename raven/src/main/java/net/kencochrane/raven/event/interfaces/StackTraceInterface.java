package net.kencochrane.raven.event.interfaces;

import java.util.Arrays;

/**
 * The StackTrace interface for Sentry, allowing to add a stackTrace to an event.
 */
public class StackTraceInterface implements SentryInterface {
    /**
     * Name of the Sentry interface allowing to send a StackTrace.
     */
    public static final String STACKTRACE_INTERFACE = "sentry.interfaces.Stacktrace";
    private final StackTraceElement[] stackTrace;
    private final int framesCommonWithEnclosing;

    /**
     * Creates a StackTrace.
     *
     * @param stackTrace StackTrace to provide to Sentry.
     */
    public StackTraceInterface(StackTraceElement[] stackTrace) {
        this(stackTrace, new StackTraceElement[0]);
    }

    /**
     * Creates a StackTrace.
     * <p>
     * With the help of the enclosing StackTrace, figure out which frames are in common with the parent exception
     * to potentially hide them later in Sentry.
     * </p>
     *
     * @param stackTrace          StackTrace to provide to Sentry.
     * @param enclosingStackTrace StackTrace of the enclosing exception, to determine how many Stack frames
     *                            are in common.
     */
    public StackTraceInterface(StackTraceElement[] stackTrace, StackTraceElement[] enclosingStackTrace) {
        this.stackTrace = Arrays.copyOf(stackTrace, stackTrace.length);

        int m = stackTrace.length - 1;
        int n = enclosingStackTrace.length - 1;
        while (m >= 0 && n >= 0 && stackTrace[m].equals(enclosingStackTrace[n])) {
            m--;
            n--;
        }
        framesCommonWithEnclosing = stackTrace.length - 1 - m;
    }

    @Override
    public String getInterfaceName() {
        return STACKTRACE_INTERFACE;
    }

    public StackTraceElement[] getStackTrace() {
        return Arrays.copyOf(stackTrace, stackTrace.length);
    }

    public int getFramesCommonWithEnclosing() {
        return framesCommonWithEnclosing;
    }
}
