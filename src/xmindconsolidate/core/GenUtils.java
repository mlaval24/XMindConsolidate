package xmindconsolidate.core;

import org.eclipse.jface.preference.IPreferenceStore;

import xmindconsolidate.Activator;

public class GenUtils
{

	private static String dayAbrevStock = "";

	public static String getDayAbrev()

	{

		if (dayAbrevStock.equals(""))
		{
			IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
			dayAbrevStock = preferenceStore.getString("DAY_ABREV");

		}

		if (dayAbrevStock.equals(""))
		{
			dayAbrevStock = "j";

		}

		return dayAbrevStock;

	}

}
