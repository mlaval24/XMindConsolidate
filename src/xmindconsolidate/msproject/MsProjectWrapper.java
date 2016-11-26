package xmindconsolidate.msproject;

import java.util.ArrayList;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Wrapper to msproject
 * @author LAVALDAVID
 *
 */
public class MsProjectWrapper
{

	ActiveXComponent msProjApp;
	Dispatch  projects;
	Dispatch  project;
	Dispatch  tasks;
	Variant d ;
	

	public MsProjectWrapper()
	{
		
		msProjApp = new ActiveXComponent("MsProject.Application");
		d = new Variant();

		System.out.println("Project Wrapper");
		
		msProjApp = new ActiveXComponent("MsProject.Application");
		
		
	    Dispatch projects = msProjApp.getProperty("Projects").toDispatch();
		project = Dispatch.get((Dispatch) projects,"Add").toDispatch();

		
		d = msProjApp.invoke("FieldNameToFieldConstant", "Texte1");
		Dispatch.call(msProjApp, "CustomFieldRename", new Variant[]{d,new Variant("ID")});
		//Variant res = msProjApp.invoke("CustomFieldRename",new Variant[]{d,new Variant("ID")});

	    
		tasks = Dispatch.get((Dispatch) project,"Tasks").toDispatch();
	    
	}
	
	
    /**
     * Add tasks to project
     * @param titre
     */
    public void addTask (String titre, 
    		             int work, 
    		             String id, 
    		             List<Integer> predList,
    		             long outlinelevel,
    		             String [] resources
    		             )
    {
    	
    	Dispatch task = Dispatch.call(tasks, "Add", titre).toDispatch();
    	
    	
    	
    	Dispatch.put((Dispatch) task, "Work", work);
    	Dispatch.put((Dispatch) task, "OutlineLevel", outlinelevel);
    	Dispatch.put((Dispatch) task, "Manual", 0);
    	Dispatch.put((Dispatch) task, "Type", 2);
    	Dispatch.call(task, "SetField", new Object[] {d, new Variant(id)});
    	
    	int taskId = Dispatch.get((Dispatch) task, "ID").getInt();
    	
    	
    	String strPred = "";
    	for (Integer prd : predList )
    	{
    		
    		strPred = strPred + prd.toString() + ";"; 
    		
    	}
    	    	
    	//Dispatch.put((Dispatch) task, "Predecessors", new Dispatch(strPred));
    	//Dispatch.put((Dispatch) task, "Predecessors", new Variant[]{new Variant(strPred)});
    	//Dispatch.call(task,"Predecessors", new Variant[]{new Variant(strPred)});
    	
    	for ( String r :resources)
    	{
    		long idRessource = this.addOrUpdateResource (r,100, taskId);
    	}
    	
    }

     

   /** 
    * Add or update a resource in project file 
    * @param name
    * @param units
    * @param taskId
    * @return
    */
   public long addOrUpdateResource (String name, int units, int taskId)
   {

	   
	    long resourceId = 0;
	   
		Dispatch resources = Dispatch.get((Dispatch) project,"Resources").toDispatch();
		
		Dispatch resource = null ;
		
		Variant nbRes = Dispatch.get((Dispatch) resources,"Count");
		
		boolean found  = false; 
		for (int i = 1; i <= nbRes.getInt(); i++)
		{
			 Dispatch r = Dispatch.call((Dispatch) project,"Resources",i).toDispatch();
			 
			 String nameTrt = Dispatch.get(r, "Name").toString();
			 
			 if ( nameTrt.equals(name))
			 {
				 found = true;
				 resource = r;
				 resourceId = Dispatch.get(r, "ID").getInt();
			 }
		}
		
		
		if (found)
		{
		}
		else
		{
			resource = Dispatch.call(resources, "Add", name).toDispatch();

			Dispatch.put(resource, "Initials", name);
    	}
		
		Dispatch assignments = Dispatch.get(resource, "Assignments").toDispatch();
		Dispatch.call(assignments,"Add",new Variant[]{new Variant(taskId)});
		
		return resourceId;
			

   }
    
    
    //Object task = Dispatch.get((Dispatch) tasks,"Add").toDispatch();

}
