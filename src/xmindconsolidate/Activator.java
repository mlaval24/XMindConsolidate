package xmindconsolidate;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import xmindconsolidate.core.WorkConsolidate;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "XMindConsolidate"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		
		
	
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		
	IWorkbench wb = PlatformUI.getWorkbench();
		
	
	IWorkbenchWindow[] iwwt = wb.getWorkbenchWindows();
		IWorkbenchWindow iww =  iwwt[0];

		if  (iww != null)
		{
		//ISelectionListener listener = new ISelectionListener();
		IPartService ips  = iww.getPartService();
		
		ips.addPartListener( new IPartListener2 () {

			@Override
			public void partActivated(IWorkbenchPartReference partRef)
			{
				System.out.println("Part activated");
				// TODO Auto-generated method stub

			// TODO Auto-generated method stub
				
				WorkConsolidate wc = new WorkConsolidate(partRef.getPage().getWorkbenchWindow());
				
				try
				{
					wc.updateCommandStatus(partRef.getPart(false));
				} catch (ExecutionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void partBroughtToTop(IWorkbenchPartReference partRef)
			{
				System.out.println("Part on top");
	
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void partDeactivated(IWorkbenchPartReference partRef)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void partOpened(IWorkbenchPartReference partRef)
			{
				System.out.println("Part open");
				// TODO Auto-generated method stub
				
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void partVisible(IWorkbenchPartReference partRef)
			{
				System.out.println("Part visible");
				
			}

			@Override
			public void partInputChanged(IWorkbenchPartReference partRef)
			{
				// TODO Auto-generated method stub
				
			}});
		
		//IWorkbenchListener ibl =new IWorkbenchListener();
	//	wb.addWorkbenchListener( ibl);

		}
		
		plugin = this;
		
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
