package xmindconsolidate.actions;

import org.eclipse.ui.IWorkbenchWindow;

import xmindconsolidate.msproject.MsProjectWrapper;

import com.jacob.com.*;
import com.jacob.activeX.*;


/**
 * 
 * Classe de test excel
 * @author LAVALDAVID
 *
 */
public class MsProjectExport
{
  public static void main(String[] args)
  {
	  
	  
	
    //	WorkConsolidate wc = new WorkConsolidate ( IWorkbenchWindow window,  String workbookPath)	  
	  
	MsProjectWrapper prjW = new MsProjectWrapper();
	
	/*
    ActiveXComponent msProjApp = new ActiveXComponent("MsProject.Application");
    
    Object projects = msProjApp.getProperty("Projects").toDispatch();
    Object project = Dispatch.get((Dispatch) projects,"Add").toDispatch();
    
    
    Dispatch tasks = Dispatch.get((Dispatch) project,"Tasks").toDispatch();

    //tiveXComponent tasks = ((ActiveXComponent) project).getPropertyAsComponent("Tasks");
    //ject task = tasks.invoke("Add","test");
     * ù/
     */
	
	prjW.addTask("Texte",6,"1",new String[]{},1,new String[]{"ML"});
	
	//prjW.addOrUpdateResource("ML",50);
	//prjW.addOrUpdateResource("XD",70);
	
	prjW.addTask("Texte",6,"2",new String[]{},2,new String[]{"XX"});
	prjW.addTask("Texte",6,"3",new String[]{},3,new String[]{"ML"});
	prjW.addTask("Texte",6,"4",new String[]{},3,new String[]{"ML"});

	/*
    Dispatch task0 = Dispatch.call(tasks, "Add", "Texte").toDispatch();
    Dispatch task1 = Dispatch.call(tasks, "Add", "Texte").toDispatch();
    Dispatch task2 = Dispatch.call(tasks, "Add", "Texte").toDispatch();
    Dispatch.put((Dispatch) task0, "Work", 8*60);
    Dispatch.put((Dispatch) task1, "Work", 16*60);
    Dispatch.put((Dispatch) task2, "Predecessors", task1 ); 
    */
    
   
    //Object task = Dispatch.get((Dispatch) tasks,"Add").toDispatch();
   
    
    /* Object a1 = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
            new Object[] {"A1"},
            new int[1]).toDispatch();*/
    
//    Object xlo = msProjApp.getObject();

    try {
    	

    
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    	//msProjApp.invoke("Quit", new Variant[] {});
    }
/*
	  
    ActiveXComponent xl = new ActiveXComponent("Excel.Application");
    Object xlo = xl.getObject();
    try {
      System.out.println("version="+xl.getProperty("Version"));
      System.out.println("version="+Dispatch.get((Dispatch) xlo, "Version"));
      xl.setProperty("Visible", new Variant(true));
      Object workbooks = xl.getProperty("Workbooks").toDispatch();
      Object workbook = Dispatch.get((Dispatch) workbooks,"Add").toDispatch();
      Object sheet = Dispatch.get((Dispatch) workbook,"ActiveSheet").toDispatch();
      Object a1 = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
                                  new Object[] {"A1"},
                                  new int[1]).toDispatch();
      Object a2 = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
                                  new Object[] {"A2"},
                                  new int[1]).toDispatch();
      Dispatch.put((Dispatch) a1, "Value", "123.456");
      Dispatch.put((Dispatch) a2, "Formula", "=A1*2");
      System.out.println("a1 from excel:"+Dispatch.get((Dispatch) a1, "Value"));
      System.out.println("a2 from excel:"+Dispatch.get((Dispatch) a2, "Value"));
      Variant f = new Variant(false);
      //Dispatch.call(workbook, "Close", f);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      xl.invoke("Quit", new Variant[] {});
    }
    */
  }
 
}