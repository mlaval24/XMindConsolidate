package xmindconsolidate.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import xmindconsolidate.core.WorkConsolidate;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ToogleConsolidateHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ToogleConsolidateHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = null;
     	window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		
		
		Command command = event.getCommand();
		
		/*
		 * Get get the status of consolidation with state of the command before action
		 */
	    boolean statutConsolidation = !HandlerUtil.toggleCommandState(command);
		
	     
        WorkConsolidate t = new WorkConsolidate(window);
        
		if (statutConsolidation )
		{
			
			MessageDialog.openInformation(
					window.getShell(),
					"XMindConsolidate",
					Messages.XmindConsolidate_Begin_Consolidation);

			   t.run();
		}
		else
		{
			MessageDialog.openInformation(
					window.getShell(),
					"XMindConsolidate",
					Messages.XmindConsolidate_Stop_Consolidation);
			   t.clean();
		}

	    
	     
		return null;
	}
}
