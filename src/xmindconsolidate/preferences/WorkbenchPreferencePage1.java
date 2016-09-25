package xmindconsolidate.preferences;
         

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


import xmindconsolidate.Activator;

/**
 * Prefrences page
 * 
 * @author mlaval
 *
 */

public class WorkbenchPreferencePage1 extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage {

  public WorkbenchPreferencePage1() {
    super(GRID);

  }

  public void createFieldEditors() {
    addField(new StringFieldEditor("DAY_ABREV", "Day Abreviation (default =d)",
        getFieldEditorParent()));
    /*
    addField(new BooleanFieldEditor("BOOLEAN_VALUE",
        "&An example of a boolean preference", getFieldEditorParent()));

    addField(new RadioGroupFieldEditor("CHOICE",
        "An example of a multiple-choice preference", 1,
        new String[][] { { "&Choice 1", "choice1" },
            { "C&hoice 2", "choice2" } }, getFieldEditorParent()));
    addField(new StringFieldEditor("MySTRING1", "A &text preference:",
        getFieldEditorParent()));
    addField(new StringFieldEditor("MySTRING2", "A &text preference:",
        getFieldEditorParent()));
     */
  }

  @Override
  public void init(IWorkbench workbench) {
    setPreferenceStore(Activator.getDefault().getPreferenceStore());
    setDescription("Parameters");
  }
} 