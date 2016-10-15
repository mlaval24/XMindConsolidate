package xmindconsolidate.handlers;

import org.eclipse.osgi.util.NLS;

/**
 * @author Marc Laval
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "xmindconsolidate.handlers.messages"; //$NON-NLS-1$

    public static String XmindConsolidate_Begin_Consolidation;
    public static String XmindConsolidate_Stop_Consolidation;

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
