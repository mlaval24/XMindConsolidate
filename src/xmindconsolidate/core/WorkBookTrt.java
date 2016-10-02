package xmindconsolidate.core;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;
import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.ITopic;
import org.xmind.core.ITopicExtension;
import org.xmind.core.ITopicExtensionElement;
import org.xmind.core.IWorkbook;
import org.xmind.core.event.ICoreEventRegistration;
import org.xmind.core.event.ICoreEventSupport;
import org.xmind.core.marker.IMarkerSheet;

import xmindconsolidate.Activator;


/**
 * Wokbook consolidation
 * 
 * @author Marc Laval
 *
 */

public class WorkBookTrt {

	private static final String IS_CONSOLIDATED = "est_consolide";

	
	private IWorkbook wb;


	public ICoreEventRegistration markerAddRegistration ;
	public  ICoreEventRegistration markerRemoveRegistration ;

	private static  ICoreEventSupport  ces  = null;
	private static WorkCoreEventListener wcel = null; 
	
	private IWorkbenchWindow window;


	/**
	 * Constructor
	 */
	public WorkBookTrt(IWorkbook wb_in, IWorkbenchWindow window) {
		
		this.window = window;
		
		wb = wb_in;


		manageIcons(wb);



	}


	/**
	 * WBS crossing (recursive method which starts with topics given as parameters) 
	 * 
	 * @param top parent Topic
	 * @param level (to store in hahsmap)
	 * @param hm hashmap which stores topic as lists 
	 */
	public static void crossTree(ITopic top, Integer level, HashMap<Integer, List <ITopic>> hm)
	{


		/*
		 * Root topic is placed at slot 0 of the hashmap hm 
		 */
		if ( level == 0) 
		{
			List <ITopic> l = new ArrayList<ITopic>() ;
			l.add(top);
			hm.put(level,l);
		}

		
		
		level ++;

		/* 
		 * Descendants
		 */

		List<ITopic> children =  top.getAllChildren();

		for ( ITopic child : children )
		{



			if (hm.containsKey(level))
			{

				hm.get(level).add(child);
			}
			else
			{
				List <ITopic> l = new ArrayList<ITopic>() ;
				l.add(child);
				hm.put(level, l);

			}

			crossTree(child, level++,hm);

			level--;

		}



	}


	/**
	 * Manage icons displayed
	 * @param workbook
	 */
	public void manageIcons(IWorkbook workbook)
	{



		String iconDirectory = "/markIcons";
		IMarkerSheet sheet = workbook.getMarkerSheet();


		try {



			BundleContext ctx =
					BundleReference.class.cast(this.getClass().getClassLoader())
					.getBundle()
					.getBundleContext(); 

			URL markerDirURL = ctx.getBundle().getResource(iconDirectory);


			if ( markerDirURL != null )
			{
			   sheet.importFrom(FileLocator.toFileURL(markerDirURL).getPath());
			}
	


		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}


	}
	
	
	/**
	 * Clean topic label from Sum of work information
	 * @param topic
	 * @param l
	 */
	public void cleanLabelfromSumInfo(ITopic topic)
	{

		final Pattern pattern1 = Pattern.compile("\\u03A3\\s=\\s(\\d*\\.\\d*)[jJdDhH"+GenUtils.getDayAbrev()+"]\\s\\(.*\\)");
		final Pattern pattern2 = Pattern.compile("\\u03A3\\s=\\s(\\d*)[jJdDhH"+GenUtils.getDayAbrev()+"]\\s\\(.*\\)");


	
		Set<String> labels = topic.getLabels();
		
		Iterator<String> iterator = labels.iterator();
		


    	while (iterator.hasNext()) 
		{
		    String l  =  iterator.next();	
			boolean isSum = false;
			


    		if (  pattern1.matcher(l).find() )
    		{
    			isSum = true;
    		}
    		else
    		{

    			if (   pattern2.matcher(l).find()  )
        		{
        			isSum = true;
        		}
    			
    		}
	    	
		    if (isSum )
		    {
		    	
		    	
		    	 topic.removeLabel(l);
		    	 /*
 		    	 MessageDialog.openInformation(
							this.window.getShell(),
							"XMindConsolidate",
							"maj pour le poste " +topic.getTitleText());
				*/			
				

		    }
		}
    	
    
    
	}
	
	

	/**
	 * consolidate method : launch consolidation on tree
	 */

	public  void consolidate()  {
		
		


		/* We'll work on labels : we need to stop automtic reaction */
		
		if ( wcel  !=  null)
		{
			wcel.toogleReacting(false);
		}
			
		
		
		HashMap<Integer, List <ITopic>> hm = new HashMap<Integer, List<ITopic>>();

		ITopic root = wb.getPrimarySheet().getRootTopic();



          
		// manageIcons(wb);

		crossTree(root,0, hm);


		int maxLevelSeen = 0;
		for ( Integer i :  hm.keySet())
		{
			if ( i > maxLevelSeen)
			{
				maxLevelSeen = i;
			}

		}
		

		for ( Integer i =maxLevelSeen; i>-1; i--)
		{


			for ( ITopic topic : hm.get(i)) 
			{ 

				TopicUtils trt = new TopicUtils(topic, wb);

				/*
				 * Is the topic a leaf ?  In this case, work and progress are stored
				 * 
				 */
				Integer childrenCount = topic.getAllChildren().size();
				

				if ( childrenCount == 0)
				{


					/*
					 * Total work is saved 
					 */
					trt.setTotalWork(trt.getTopicTotalWork());
					trt.setTotalWorkCompleted(trt.getTopicTotalWork()*trt.getCompletionRate()/100);

					
					cleanLabelfromSumInfo(topic);

				}
				else
				{

					/*
					 * If topic is not a leaf, all work and effective work  from children are summarized
					 * 
					 * 
					 */
					List<ITopic> children =  topic.getAllChildren();

					double totalWork = 0;
					double totalWorkCompleted = 0;

					for ( ITopic child : children )
					{

						TopicUtils childTopic = new TopicUtils(child,wb);

						totalWork += childTopic.getTotalWork();
						totalWorkCompleted += childTopic.getTotalWorkCompleted();
						// System.out.println("totalWork = "+childTopic.getTotalWork());
						// System.out.println("totalWorkCompleted = "+childTopic.getTotalWorkCompleted());
					}    	 		    	

					trt.setTotalWork(totalWork);
					trt.setTotalWorkCompleted(totalWorkCompleted);


					if ( totalWork  == 0.0)
					{
						trt.setProgress(0.0);
						trt.setProgressIndicator(0.0);
					}
					else
					{
						trt.setProgress(totalWorkCompleted*100/totalWork);
						trt.setProgressIndicator(totalWorkCompleted*100/totalWork);
					}

                    
					/*
					 * Clean Sum info
					 */
					cleanLabelfromSumInfo(topic);

					
					/*
					 * Clean work : this is a non leaf topic
					 * 
					 */
					Set<String> labels = topic.getLabels();

					for( String  l : labels)
					{

						final Pattern ptWk1 = Pattern.compile("^([a-zA-Z]+)\\s+(\\d+\\.\\d+)[jJdDhH"+GenUtils.getDayAbrev()+"]$");
						final Pattern ptWk2 = Pattern.compile("^([a-zA-Z]+)\\s+(\\d+)[jJdDhH"+GenUtils.getDayAbrev()+"]$");


						final Matcher mWk1 = ptWk1.matcher(l);
						final Matcher mWk2 = ptWk2.matcher(l);

						if ( mWk1.find() ||  mWk2.find() )

						{
							topic.removeLabel(l);

							//TODO : internationalize it !	
							MessageDialog.openInformation(
									this.window.getShell(),
									"XMindConsolidate",
									"Suppression de la charge de travail "+l+" pour le poste " +topic.getTitleText());
						} 
					}
					

					/*
					 * And add sum information
					 */
    				topic.addLabel("\u03A3 = " + trt.getTotalWork()+GenUtils.getDayAbrev()+" ("+Math.round(totalWorkCompleted*100/totalWork)+"%)" );

				}


			}

		}


		/*
		 * Event listening
		 * 
		 */
		if ( ces ==null )
		{
			ces = (ICoreEventSupport) wb.getAdapter(ICoreEventSupport.class);
			
			wcel = new WorkCoreEventListener(this.window);

			ces.registerGlobalListener(Core.MarkerRefAdd, wcel); //
			ces.registerGlobalListener(Core.MarkerRefRemove, wcel); //
			ces.registerGlobalListener(Core.Labels, wcel); // 
			ces.registerGlobalListener(Core.TopicAdd, wcel); // 
			ces.registerGlobalListener(Core.TopicRemove, wcel); // 
            
		}
		else
		{
		   /* Workbook can react to events again */ 
		   if ( wcel  !=  null)
	       {
	    		wcel.toogleReacting(true);
	        }
		}


	}


    /** 
     * Clear sum information in non leaf pages
     */
	public void cleanSumInfo()
	{

		
		
		/* We'll work on labels : we need to stop automtic reaction */
		
		if ( wcel  !=  null)
		{
			wcel.toogleReacting(false);
		}
			
		
		HashMap<Integer, List <ITopic>> hm = new HashMap<Integer, List<ITopic>>();

		ITopic root = wb.getPrimarySheet().getRootTopic();


		crossTree(root,0, hm);
		

		int maxLevelSeen = 0;
		for ( Integer i :  hm.keySet())
		{
			if ( i > maxLevelSeen)
			{
				maxLevelSeen = i;
			}

		}
		
		
		for ( Integer i =maxLevelSeen; i>-1; i--)
		{
			for ( ITopic topic : hm.get(i)) 
			{ 

				cleanLabelfromSumInfo(topic);

			}
		}
		
	
	
	

	   /* Workbook can react to events again */ 
	   if ( wcel  !=  null)
       {
    		wcel.toogleReacting(true);
        }



	}


	/**
	 * Save the consolidation status 
	 * @param status
	 */
	public void saveConsolidationStatus(boolean status)
	{
		
		ITopic root = wb.getPrimarySheet().getRootTopic();
		/*
		 * Store the consolidation Status in Workbook
		 */
		ITopicExtension consolidated = (ITopicExtension) root.getExtension("org.mlaval.xmind");
		if (consolidated == null)
		{

			consolidated = root.createExtension("org.mlaval.xmind");
		}

		ITopicExtensionElement ee = consolidated.getContent().getFirstChild(IS_CONSOLIDATED);
		if (ee == null)
		{
			ee = consolidated.getContent().createChild(IS_CONSOLIDATED);
		}

		if (ee != null)
		{
			if (status)
			{
				   ee.setTextContent("Yes");
			}
			else
			{
				   ee.setTextContent("No");
			}
				
			
		}
	}

	
	
	/**
	 * Get the consolidation status 
	 * @param topic
	 * @return
	 */
	public boolean getConsolidationStatus()
	{
		boolean st = false;
		
		ITopic root = wb.getPrimarySheet().getRootTopic();

		/*
		 * Store the consolidation Status in Workbook
		 */
		ITopicExtension consolidated = (ITopicExtension) root.getExtension("org.mlaval.xmind");
		if (consolidated != null)
		{

			ITopicExtensionElement ee = consolidated.getContent().getFirstChild(IS_CONSOLIDATED);
			if (ee != null)
		   {
		   
				String res = ee.getTextContent();
				
				if (res.equals("Yes"))
				{
					st = true;
				}
		    }
			
		}
		return st;
	}



}
