package xmindconsolidate.core;

 


import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;

import org.xmind.core.ITopic;
import org.xmind.core.ITopicExtension;
import org.xmind.core.ITopicExtensionElement;
import org.xmind.core.IWorkbook;

import org.xmind.core.marker.IMarkerGroup;
import org.xmind.core.marker.IMarkerRef;

import xmindconsolidate.Activator;



/**
 * Utilities to act on topics 
 * 
 * @author Marc Laval
 *
 */
public class TopicUtils  {

	
	private ITopic topic; 
	//TODO Remove it
	//private IWorkbook wb;
	private IWorkbook wb;
	
	/*
	 * Ranges for markers
	 *  
	 * 0     12.5%    25%    37.5%  
	 * '-------'-------'-------'- .....
	 * >------< start
	 *         >------< oct
	 *                 >------< quarter ....
	 */
	private static Map<String,Double> markerValues = new HashMap<String, Double>();
	static {
		markerValues.put("task-start",0.0);
		markerValues.put("task-oct",12.5);
		markerValues.put("task-quarter",25.0);
		markerValues.put("task-3oct",37.5);
		markerValues.put("task-half",50.0);
		markerValues.put("task-5oct",62.5);
		markerValues.put("task-3quar",75.0);
		markerValues.put("task-7oct",87.5);
		markerValues.put("task-done",100.0);
	}

	
	private static Map<Integer ,String> markerRanges = new HashMap<Integer ,String>();
	static {
		markerRanges.put(0,"task-start");
		markerRanges.put(1,"task-oct");
		markerRanges.put(2,"task-quarter");
		markerRanges.put(3,"task-3oct");
		markerRanges.put(4,"task-half");
		markerRanges.put(5,"task-5oct");
		markerRanges.put(6,"task-3quar");
		markerRanges.put(7,"task-7oct");
		markerRanges.put(8,"task-done");
	}

	private static final String TOTAL_WORK_TAG = "charge_totale";
	private static final String TOTAL_COMPLETED_WORK_TAG = "charge_accomplie";


	List<IMarkerGroup> groupList = null;

	/**
	 * Getter topic 
	 * @return
	 */
	public ITopic getTopic() {
		return topic;
	}

	/**
	 * Setter topic
	 * @param topic
	 */
	public void setTopic(ITopic topic) {
		this.topic = topic;
	}


	/**
	 * Constructor 
	 * @param topic Xmind topic to work on 
	 * @param wb Xmind workbook which topic belongs to
	 */
	public TopicUtils(ITopic topic, IWorkbook wb) {
		
		this.topic = topic;
		this.wb = wb;
		
		/*
		 *  Recup des marqueurs du workbook
		
		 IMarkerSheet markerSheet = this.wb.getMarkerSheet();
		*/
	}
	
	
	
	/**
	 * Return the completion rate of this part of WBS (including all descendant tasks).
	 * Value is stored in taskInfo variable ( Xmind pro feature ) 
	 * @return Completion rate, in percent 
	 */
	public double getCompletionRate()
	{
		
		double acc = 0;
		
		String progressInfo ="NotFound"; 
		
		
		/* Get value in "progress" field */
	    
	    ITopicExtension ext =topic.getExtension("org.xmind.ui.taskInfo") ;
	   
	    if ( ext != null)
	    {
	      /*
	       * Value is known  	
	       */
	       List <ITopicExtensionElement> le = ext.getContent().getChildren("progress");
	       
	       if ( le != null && le.size()> 0)
	       {
	          String progressText = le.get(0).getTextContent();
	          
	       
	          if ( progressText != null)
	          {
	    	    // acc= Integer.parseInt(progressText);
	    	     acc= Double.parseDouble(progressText);
	    	     progressInfo = "Found";
	          }
	       }
	    }
	    
	    
	    
	    if (! progressInfo.equals("Found") )
	    {
	    	/* 
	    	 *  Value is not known, approximation is done with task marquer
	    	 */
	    	
	    	Set <IMarkerRef> marquersList = topic.getMarkerRefs();
	    	
	    
	    	for ( IMarkerRef marquer : marquersList)
	    	{
	    		String marquerId = marquer.getMarkerId();
	    		
	    		if ( marquerId.startsWith("task-"))
	    		{
	    			
	    			acc = markerValues.get(marquerId);	
	    			
		    	     
	    		}
	    		
	    	}

	    	
	    }
	    
	    
	    /*
	     * Finally, if neither progress value or task marker is known, achievement rate is assumed to be 0 
	     */
	    
	    return acc;

	}
	

	/**
	 * For a leaf in WBS, work is calculated with label content 
	 * 
	 * @return Task work, in days
	 */
	public double getTopicTotalWork()
	{
		
		double ch = 0;
		
		
		Set <String>  lbs = topic.getLabels();
		
		for ( String lb :lbs )
		{
			
			 final Pattern pattern1 = Pattern.compile("\\D+\\s*(\\d*\\.\\d*)\\s*([jJdDhH"+GenUtils.getDayAbrev()+"])");
			 final Pattern pattern2 = Pattern.compile("\\D+\\s*(\\d*)\\s*([jJdDhH"+GenUtils.getDayAbrev()+"])");
			 final Pattern pattern1 = Pattern.compile("\\D+\\s*(\\d*\\.\\d*)\\s[jJdD]");
			 final Pattern pattern2 = Pattern.compile("\\D+\\s*(\\d*)\\s*[jJdD]");
			
			
			  final Matcher matcher1 = pattern1.matcher(lb);
			  if ( matcher1.find() )
			  {
				   if ( matcher1.group(2).equals("h") 
						   || matcher1.group(2).equals("H"))
				   {
	   			       ch += Double.parseDouble(matcher1.group(1) )/8.0;
				   }
				   else
				   {
	   			       ch += Double.parseDouble(matcher1.group(1) );
					   
				   }
			  }
			  else
			  {
     	           final Matcher matcher2 = pattern2.matcher(lb);

                   if ( matcher2.find() )
                   {
				      if ( matcher2.group(2).equals("h") 
					   	   || matcher2.group(2).equals("H"))
				      {
	   			          ch += Double.parseDouble(matcher2.group(1) )/8.0;
				      }
				      else
				      {
	   			         ch += Double.parseDouble(matcher2.group(1) );
					    
				      }
                   }
			
	   			   ch += Double.parseDouble(matcher1.group(1) );
			  }
			  else
			  {
				  final Matcher matcher2 = pattern2.matcher(lb);
				  if ( matcher2.find() )
				  {
		   			   ch += Double.parseDouble(matcher2.group(1) );
					  
				  }
				  
			  }
			
		}
		
		
		return ch;
		
	}
	

	

	
	/**
	 * Store TotalWork in XMind  
	 * @param ch Total Work of Topic
	 */
	public void setTotalWork(Double ch)
	{
	  
	  
	  ITopicExtension charge = topic.getExtension("org.mlaval.xmind");
		if (charge == null)
		{
		  
		  charge = topic.createExtension("org.mlaval.xmind");
	  }
	  
	  ITopicExtensionElement ee = charge.getContent().getFirstChild(TOTAL_WORK_TAG);
	  if ( ee == null)
	  {
	  
		  ee =   charge.getContent().createChild(TOTAL_WORK_TAG);
	  }
	  
		if (ee != null)
		{
	    ee.setTextContent(ch.toString());
	  }
	 
	 /*
	 * Store TotalWork in XMind  
	 * @param ch Total Work of Topic
	 */
	public void setTotalWork(Double ch ) {
	  
	  ITopicExtension charge = topic.getExtension("org.mlaval.xmind");
	  if(charge == null) {
		  
		  charge = topic.createExtension("org.mlaval.xmind");
	  }
	  
	  ITopicExtensionElement ee = charge.getContent().getFirstChild(TOTAL_WORK_TAG);
	  if ( ee == null)
	  {
	  
		  ee =   charge.getContent().createChild(TOTAL_WORK_TAG);
	  }
	  
	  if (ee != null) {
	    ee.setTextContent(ch.toString());
	  }
	}

	/**
	 * Retrieve TotalWork from Xmind
	 * @param topic
	 * @return Total work or topic (with subtopics) in days
	 */
	public double getTotalWork()
	{
	
	     double res = 0;		 
		 
		  ITopicExtension charge = topic.getExtension("org.mlaval.xmind");
		  if(charge != null) {
			  
		    ITopicExtensionElement ee = charge.getContent().getFirstChild(TOTAL_WORK_TAG);
		    if ( ee != null)
		    {
		          res = Double.parseDouble(ee.getTextContent());
		    }
		  }
		  return res;
	}

	/**
	 * Delete TotalWork information from Xmind
	 * @param topic
	 */
	public void removeTotalWork()
	{
		topic.deleteExtension(TOTAL_WORK_TAG);
	}
	
	

	
	
	
	/**
	 * Store amount of total work  completed in XMind   
	 * @param ch Amount of work completed (in days)
	 */
	public void setTotalWorkCompleted(Double ch ) {
	  
		  ITopicExtension charge = topic.getExtension("org.mlaval.xmind");
		  if(charge == null) {
			  
			  charge = topic.createExtension("org.mlaval.xmind");
		  }
		  
		  ITopicExtensionElement ee = charge.getContent().getFirstChild(TOTAL_COMPLETED_WORK_TAG);
		  if ( ee == null)
		  {
		  
			  ee =   charge.getContent().createChild(TOTAL_COMPLETED_WORK_TAG);
		  }
		  
		  if (ee != null) {
		    ee.setTextContent(ch.toString());
		  }
	}
	

	/**
	 * Retrieve amount of completed work  from Xmind
	 * @param topic
	 * @return Amount of completed work (in days)
	 */
	public double getTotalWorkCompleted() {
	
      double res = 0;		 
	 
	  ITopicExtension charge = topic.getExtension("org.mlaval.xmind");
	  if(charge != null) {
		  
	    ITopicExtensionElement ee = charge.getContent().getFirstChild(TOTAL_COMPLETED_WORK_TAG);
	    if ( ee != null)
	    {
	          res = Double.parseDouble(ee.getTextContent());
	    }
	  }
	  return res;
	}

	/**
	 *  Delete "completed total work" information from Xmind
	 * @param topic
	 */
	public void removeChargeAccomplie() {
	  topic.deleteExtension(TOTAL_COMPLETED_WORK_TAG);
	}
	

	
	/**
	 * Set progress value on leaf
	 * @param ch Progress
	 */
	public void setProgress(Double ch ) {
	  
	  
	  ITopicExtension charge = topic.getExtension("org.xmind.ui.taskInfo");
	  if(charge == null) {
		  
		  charge = topic.createExtension("org.xmind.ui.taskInfo");
	  }
	  
	  ITopicExtensionElement ee = charge.getContent().getFirstChild("progress");
	  if ( ee == null)
	  {
	  
		  ee =   charge.getContent().createChild("progress");
	  }
	  
	  if (ee != null) {
		
		  
	    ee.setTextContent(ch.toString());
	  }
	}
	
	
	/**
	 * Remove progress information
	 */
	public void removeProgress()
	{
		  ITopicExtension charge = topic.getExtension("org.xmind.ui.taskInfo");
		  
		  if(charge != null) {
			  
         	  ITopicExtensionElement ee = charge.getContent().getFirstChild("progress");
		      if (ee != null) {
	    		  charge.getContent().deleteChild(ee);
    		  }
		  }
	}
	
	
	/**
	 * Set progress indicator
	 * <p>
	 * 8 slices 
	 * <p>
	 * Slice number is given by formula :
	 *    round_inf [((ind + 9.375)*16/100 -1)/2] 
	 *  
	 */
	
	public void setProgressIndicator(double ind)
	{
		
		double prepTranche = ((ind + 9.375)*16/100 -1)/2;
		
		BigDecimal bd = new  BigDecimal(prepTranche);
		int  tranche =  bd.setScale(0,RoundingMode.DOWN).intValue();
		
		String markerName = markerRanges.get(tranche);
		
		

		/*
		 * Dirty .... think to improve this part
		 */
		for (int i =0; i<9; i++ )
		{
            topic.removeMarker(markerRanges.get(i));
			  
		}
		
		//IMarker marker = markerGroup.getMarker(nomMarqueur);
		topic.addMarker(markerName);
		

				
	}

	
	
	
	
	
	

}
