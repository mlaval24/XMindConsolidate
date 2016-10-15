package xmindconsolidate;

import org.eclipse.ui.IStartup;

public class MyStartup implements IStartup {

public void earlyStartup() {
    System.err.println("Starting up plugin");
}
}