package xmindconsolidate.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;



/** Classe IHandler à exécuter lors d'une commande
 * 
 * @author LAVALDAVID
 *
 */
public class testCommande implements IHandler
{

	public testCommande()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addHandlerListener(IHandlerListener arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(),
				"xx",
				"yy");
		
		
		return null;
	}

	@Override
	public boolean isEnabled()
	{
	
		return true;
	}

	@Override
	public boolean isHandled()
	{
	
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
