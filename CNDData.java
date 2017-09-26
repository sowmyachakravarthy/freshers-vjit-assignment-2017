import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;



public class CNDData {
	 private final static String MENU_FILE = "dataFiles/menu_item.txt";
	private static final String STAFF_FILE = "dataFiles/staff.txt";
	private static final CNDStaff CNDMenu = null;
	private static final CNDStaff Iterator = null;
	
	private ArrayList<CNDStaff> staffList = new ArrayList<CNDStaff>();
	 private ArrayList<CNDMenu> menuList = new ArrayList<CNDMenu>();
	     

	 
	 public ArrayList<CNDStaff> getStaffList()
     {
         return staffList;
     }
     
public ArrayList<CNDMenu> getMenuList()
{
    return menuList;
}



//----------------------------------------------------------
// Find menu item from ID
//----------------------------------------------------------
public CNDMenu  findCNDMenuByID(int id)
{
    Iterator<CNDMenu> it = menuList.iterator();
    CNDMenu           re = null;
    boolean         found = false;
    
    if(id < 0){
        return null;
    }
    
    while (it.hasNext() && !found) {
        re = (CNDMenu)it.next();  
        if( re.getID() == id)
        {
            found = true;
        }
    }
    
    if(found)
        return re;
    else        
        return null;
}

public void loadFiles1() throws Exception
{
    loadStaffFile();
    loadMenuFile();
    
}

private void loadStaffFile() throws Exception
{
        try {
        BufferedReader reader = new BufferedReader(new FileReader(STAFF_FILE));
        String line = reader.readLine();
        System.out.println("In loadStaffFile");
        while (line != null) {
            String[] record = line.split(",");

            String id = record[0].trim();
            String password = record[1].trim();
            String firstName = record[2].trim();
            String lastName = record[3].trim();
           
            CNDStaff staff = new CNDStaff(Integer.parseInt(id),lastName, firstName, password);
			staffList.add(staff);
            
        reader.close();
        }
        }
     catch (IOException ioe) {
    	 System.out.println("In loadStaffFile exception");
        String message = ioe.getMessage() + ioe.getStackTrace();
        throw new Exception(message);
    }
}

void loadMenuFile() throws Exception
{
    try {
        BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE));
        String line = reader.readLine();

        while (line != null) {
            String[] record = line.split(",");

            String id = record[0].trim();
            String name = record[1].trim();
            String price = record[2].trim();
            String type = record[3].trim();

            // Add the data from file to the registerCourses array list
            CNDMenu rCNDMenu = new CNDMenu(Integer.parseInt(id), name, Double.parseDouble(price), Byte.parseByte(type));
            menuList.add(rCNDMenu);
            line = reader.readLine();
        }
        reader.close();
    } catch (IOException ioe) {
        String message = ioe.getMessage() + ioe.getStackTrace();
        throw new Exception(message);
    }
}



public CNDStaff findStaffByID(int inputID) {
	// TODO Auto-generated method stub
	return null;
}

}
