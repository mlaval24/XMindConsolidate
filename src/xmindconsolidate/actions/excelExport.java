package xmindconsolidate.actions;

import com.jacob.com.*;
import com.jacob.activeX.*;

public class excelExport
{
  public static void main(String[] args)
  {
	/*  
    ActiveXComponent xl = new ActiveXComponent("MsProject.Application");
    Object xlo = xl.getObject();
	*/
	  
    ActiveXComponent xl = new ActiveXComponent("Excel.Application");
    Object xlo = xl.getObject();
    try {
      System.out.println("version="+xl.getProperty("Version"));
      System.out.println("version="+Dispatch.get((Dispatch) xlo, "Version"));
      xl.setProperty("Visible", new Variant(true));
      Object workbooks = xl.getProperty("Workbooks").toDispatch();
      Object workbook = Dispatch.get((Dispatch) workbooks,"Add").toDispatch();
      Object sheet = Dispatch.get((Dispatch) workbook,"ActiveSheet").toDispatch();
      Object a1 = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
                                  new Object[] {"A1"},
                                  new int[1]).toDispatch();
      Object a2 = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
                                  new Object[] {"A2"},
                                  new int[1]).toDispatch();
      Dispatch.put((Dispatch) a1, "Value", "123.456");
      Dispatch.put((Dispatch) a2, "Formula", "=A1*2");
      System.out.println("a1 from excel:"+Dispatch.get((Dispatch) a1, "Value"));
      System.out.println("a2 from excel:"+Dispatch.get((Dispatch) a2, "Value"));
      Variant f = new Variant(false);
      //Dispatch.call(workbook, "Close", f);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      xl.invoke("Quit", new Variant[] {});
    }
    
  }
 
}