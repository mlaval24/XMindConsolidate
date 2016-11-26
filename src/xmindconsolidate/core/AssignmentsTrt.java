package xmindconsolidate.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmind.core.ITopic;
import org.xmind.core.internal.Topic;

public class AssignmentsTrt
{

	TopicUtils topicTrt;
	ITopic topic ;
	
	/**
	 * Constructor
	 * @param topicTrtIn
	 */
	public AssignmentsTrt(TopicUtils topicTrtIn)
	{
	
		topicTrt = topicTrtIn;
		topic = topicTrt.getTopic();
	}
	
	
	public List <HashMap <String, String>> getAssignments()
	{
	
     	
		List <HashMap <String, String>> assignments  = new ArrayList<HashMap <String, String>>();
     	
        
		Set<String> labels = topic.getLabels();
		
		
		for ( String lb :labels )
		{
		
			
			 final Pattern pattern1 = Pattern.compile("(\\D+)\\s*(\\d*\\.\\d*)\\s*([jJdDhH"+GenUtils.getDayAbrev()+"])");
			 final Pattern pattern2 = Pattern.compile("(\\D+)\\s*(\\d*)\\s*([jJdDhH"+GenUtils.getDayAbrev()+"])");
			
			 
			  Double ch = 0.0;
			  String initials = "";
			  
			  final Matcher matcher1 = pattern1.matcher(lb);
			  
			  
			  if ( matcher1.find() )
			  {

				  if (! matcher1.group(1).contains("\u03A3"))
				  {
				   if ( matcher1.group(3).equals("h") 
						   || matcher1.group(3).equals("H"))
				   {
	   			       ch += Double.parseDouble(matcher1.group(2) )/8.0;
				   }
				   else
				   {
	   			       ch += Double.parseDouble(matcher1.group(2) );
					   
				   }
				   initials =  matcher1.group(1);
				  }
				   
			  }
			  else
			  {
     	           final Matcher matcher2 = pattern2.matcher(lb);

                   if ( matcher2.find()   )
                   {
                	   
                	   if (! matcher2.group(1).contains("\u03A3"))
                	   {
				         if ( matcher2.group(3).equals("h") 
					   	   || matcher2.group(3).equals("H"))
				        {
	   			            ch += Double.parseDouble(matcher2.group(2) )/8.0;
				        }
				        else
				        {
	   			            ch += Double.parseDouble(matcher2.group(2) );
					    
				        }
                	   }
                   }
                   initials =  matcher2.group(1);
				   

			  }
			  
			  /*
			   * add assignment
			   */
			  if ( ! initials.equals(""))
			  {
			  
			      HashMap<String, String> assignnmentDetail = new HashMap <String, String>();
				  
			      assignnmentDetail.put("initials", initials );
			   
			      ch = ch*60.0; 
			      int chg = ch.intValue();
			      assignnmentDetail.put("work",Integer.toString(chg) );

			  
			      assignments.add(assignnmentDetail);
			 
			  }
			
		}
		return assignments;
		
	
	}
	
	

}
