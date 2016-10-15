package xmindconsolidate.actions;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xmind.core.ITopic;

import xmindconsolidate.core.GenUtils;



/** Classe IHandler à exécuter lors d'une commande
 * 
 * @author LAVALDAVID
 *
 */
public class plusOneDay implements IHandler
{

	public plusOneDay()
	{
		
	}

	@Override
	public void addHandlerListener(IHandlerListener arg0)
	{
		
		
	}

	@Override
	public void dispose()
	{
		
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection) {
			
			
		        if (((IStructuredSelection) selection).size() != 1)
		        {
		        	
		        }
		        else
		        {
		        
		        	Object o = ((IStructuredSelection) selection).getFirstElement();
			        if (o instanceof ITopic) {

			        	ITopic topic = (ITopic)o;
			        	
			        	addOneDay(topic,1.0);
			        }
		        }
		}
		
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
		
	}
	
	
	/**
	 * Add one day to work
	 *  
	 */
	
	public void addOneDay(ITopic topic,double ind)
	{

		

		Iterator<String> iterator = topic.getLabels().iterator();
		


       while (iterator.hasNext())
       {
         	 String lb  =  iterator.next();	
		
	    	 final Pattern pattern1 = Pattern.compile("(\\D+\\s*)(\\d*\\.\\d*)(\\s*)([jJdDhH"+GenUtils.getDayAbrev()+"])");
	    	 final Pattern pattern2 = Pattern.compile("(\\D+\\s*)(\\d*)(\\s*)([jJdDhH"+GenUtils.getDayAbrev()+"])");
	    	 
	    	 
	    	 
	    	  Double res;
			  final Matcher matcher1 = pattern1.matcher(lb);
			  if ( matcher1.find() )
			  {
  			       res = Double.parseDouble(matcher1.group(2)) + 1.0 ;
  			       
        	       topic.removeLabel(lb);
	  			      
	  			   lb = matcher1.group(1)+res+matcher1.group(3)+matcher1.group(4);
	  			    topic.addLabel(lb);
			  }
			  else
			  {
     	           final Matcher matcher2 = pattern2.matcher(lb);

                   if ( matcher2.find() )
                   {
	   			          res = Double.parseDouble(matcher2.group(2)) +1.0 ;
	   			          topic.removeLabel(lb);
	   	  			      
	   	  			      lb = matcher2.group(1)+res+matcher2.group(3)+matcher2.group(4);
	   	  			      topic.addLabel(lb);
  	  			      
	   	  			      
                   }

			  }
			  
			
		}
		
	    	 
	    	 
		
	
    	}

}
