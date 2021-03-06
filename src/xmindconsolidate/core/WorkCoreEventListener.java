package xmindconsolidate.core;

import org.eclipse.ui.IWorkbenchWindow;
import org.xmind.core.Core;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.event.CoreEvent;
import org.xmind.core.event.ICoreEventListener;

/**
 * Event listener : all events thats impacts consolidation results may be
 * trapped
 * 
 * @author Marc Laval
 * 
 */
class WorkCoreEventListener implements ICoreEventListener
{

	private static Boolean listenerReacting = true;
	// private static int level = 0;
	private IWorkbenchWindow window;

	public WorkCoreEventListener(IWorkbenchWindow window)
	{
		this.window = window;
	}

	@Override
	public void handleCoreEvent(CoreEvent event)
	{

		/*
		 * If listener is not activated : nothing to do
		 */
		if (listenerReacting == false)
		{
			return;
		}

		
		
		/*
		 * If Workbook is not consolidated : nothing to do
		 */
		ITopic tp = (ITopic) event.getEventSource();

		WorkBookTrt wbt = new WorkBookTrt(tp.getOwnedWorkbook(), this.window);
		
		if (! wbt.getConsolidationStatus() )
		{
			return;
		}
	
		
		/*
		 * We only watch events coming from topics ....
		 */

		if (event.getEventSource().getClass().getName().endsWith(".TopicImpl"))
		{

			/*
			 * We are going to modify topics, so we disable reaction to events
			 */
			listenerReacting = false;

			if (event.getType().equals(Core.Labels))
			{
				System.out.println(event.getEventSource().toString() + " Labels");
			}

			/*
			 * Topic remove, cleanup data when last child is deleted
			 */
			if (event.getType().equals(Core.TopicRemove))
			{
				if (tp.getAllChildren().isEmpty())
				{

					TopicUtils trt = new TopicUtils(tp, tp.getOwnedWorkbook());
					trt.removeChargeAccomplie();
					trt.removeTotalWork();

				}

			}

			/*
			 * Marker has changed : update progress information
			 */
			if (event.getType().equals(Core.MarkerRefRemove))
			{

				TopicUtils trt = new TopicUtils(tp, tp.getOwnedWorkbook());
				trt.removeProgress();
				trt.removeTotalWork();
				trt.removeChargeAccomplie();

			}

			if (event.getType().equals(Core.MarkerRefAdd) || event.getType().equals(Core.MarkerRefRemove)
					|| event.getType().equals(Core.Labels) || event.getType().equals(Core.TopicAdd)
					|| event.getType().equals(Core.TopicRemove))
			{
				wbt.consolidate();
			}
		}

		listenerReacting = true;

	}

	public void toogleReacting(boolean reacting)
	{

		listenerReacting = reacting;
	}

}