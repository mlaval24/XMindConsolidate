package xmindconsolidate.core;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;


/**
 * Work consolidation of the entire WBS
 * 
 * @author mlaval24
 * @version 0.1.0
 *
 */

public class WorkConsolidate {


	private IWorkbenchWindow window;
	private IWorkbook workbook = null;

	
	/**
	 * Constructor (with parent window), loading of an  existing file 
	 * 
	 * @param window
	 */
	public WorkConsolidate ( IWorkbenchWindow window)
	{

		this.window = window;
		

		window.getWorkbench().getActiveWorkbenchWindow();

		IWorkbenchPage p = window.getActivePage();

		IEditorPart ep= p.getActiveEditor();

		
		try {

			/*
			 *  
			 */
			workbook = (IWorkbook) ep.getClass().getMethod("getWorkbook").invoke(ep);
			
			
			
		} catch (Exception e) {

			MessageDialog.openInformation(
					this.window.getShell(),
					"XMindConsolidate",
					"Workbook not found "+e.getMessage());
		}


	}

	/**
	 * Constructor, with parent windows and file path. For test purposes
	 * @param window
	 * @param workbookPath
	 */
	public WorkConsolidate ( IWorkbenchWindow window,  String workbookPath)
	{
		this.window = window;
		

		window.getWorkbench().getActiveWorkbenchWindow();
		

		IWorkbookBuilder builder = Core.getWorkbookBuilder();
		try {
			workbook = builder.loadFromPath(workbookPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}	

	

	
	
	/**
	 * main method to use class as a main program
	 * @param args
	 */
	public static void main(String[] args)  {

		WorkConsolidate t = new WorkConsolidate(null);
		t.run();
	}





    

			/**
		     * run method : prepare workbook and launch consolidation
		     */
		    
			public void run()  {
				
				

				
				WorkBookTrt wbt = new WorkBookTrt(workbook, this.window);
				wbt.consolidate();
				


			}
         


	}

