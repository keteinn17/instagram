package security.instagram.config.auth;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public class BearerContextHolder {
    private static final ThreadLocal<BearerContext> contextHolder = new ThreadLocal<>();

    private BearerContextHolder() {
        // Private constructor
    }

    /**
     *
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * @return
     */
    public static BearerContext getContext() {
        BearerContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    /**
     * @param context
     */
    public static void setContext(BearerContext context) {
        if (context != null) {
            contextHolder.set(context);
        }
    }

    /**
     * @return
     */
    public static BearerContext createEmptyContext() {
        return new BearerContext();
    }
}
