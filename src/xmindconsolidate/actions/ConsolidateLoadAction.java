package xmindconsolidate.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import xmindconsolidate.core.WorkConsolidate;

/**
 * Action created when the user request consolidation
 * @see IWorkbenchWindowActionDelegate
 */
public class ConsolidateLoadAction implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public ConsolidateLoadAction() {
		
	}

	/**
	 * The action has been activated, we launch consolidation or we stop consolidation
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		if (action.isChecked() )
		{
		   WorkConsolidate t = new WorkConsolidate(window);
		   t.run();
		}
		
	}

	/**
	 * Selection in the workbench has been changed
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}