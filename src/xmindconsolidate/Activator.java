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



	/**
	 * Actions need as the bundle is activated
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext	 
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);

		IWorkbench wb = PlatformUI.getWorkbench();

		IWorkbenchWindow[] iwwt = wb.getWorkbenchWindows();
		IWorkbenchWindow iww = iwwt[0];

		if (iww != null)
		{
			IPartService ips = iww.getPartService();

			ips.addPartListener(new IPartListener2()
			{

				/*
				 * Whhen part - i.e. editor is activated, we update the status
				 * of consolidation button
				 * 
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui
				 * .IWorkbenchPartReference)
				 */
				@Override
				public void partActivated(IWorkbenchPartReference partRef)
				{

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
				}

				@Override
				public void partClosed(IWorkbenchPartReference partRef)
				{
				}

				@Override
				public void partDeactivated(IWorkbenchPartReference partRef)
				{
				}

				@Override
				public void partOpened(IWorkbenchPartReference partRef)
				{
				}

				@Override
				public void partHidden(IWorkbenchPartReference partRef)
				{
				}

				@Override
				public void partVisible(IWorkbenchPartReference partRef)
				{
				}

				@Override
				public void partInputChanged(IWorkbenchPartReference partRef)
				{
				}
			});

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
