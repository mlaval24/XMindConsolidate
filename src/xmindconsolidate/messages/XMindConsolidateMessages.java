package xmindconsolidate.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Jason Wong
 */
public class XMindConsolidateMessages extends NLS {

    private static final String BUNDLE_NAME = "xmindconsolidate.messages"; //$NON-NLS-1$

    public static String XmindConsolidate_Begin_Consolidation;
    public static String XmindConsolidate_Stop_Consolidation;

    static {
        NLS.initializeMessages(BUNDLE_NAME, XMindConsolidateMessages.class);
    }

    private XMindConsolidateMessages() {
    }
}
