import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CNDData {
  //private final static String MENU_FILE = "dataFiles/menu_item.txt";
  //private static final String STAFF_FILE = "dataFiles/staff.txt";
  private final static String MENU_FILE = "menu_item.txt";
  private static final String STAFF_FILE = "staff.txt";
  
   private ArrayList<CNDStaff> staffList = new ArrayList<CNDStaff>();
   private ArrayList<CNDMenu>  menuList = new ArrayList<CNDMenu>();
   private ArrayList<CNDOrder> orderList = new ArrayList<CNDOrder>();     

   int     todaysOrderCounts;
	 
	// private Date    date; 
	public CNDData()
	{
	 //date = new Date();
	 todaysOrderCounts = 0;  //Load order file??
	}
	 
	public ArrayList<CNDStaff> getStaffList()
    {
     return staffList;
    }
     
  public ArrayList<CNDMenu> getMenuList()
  {
    return menuList;
  }

  public ArrayList<CNDOrder> getOrderList()
  {
    return orderList;
  }

//Find Menu Item using ID value
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

public void addOrderItem(int orderID, CNDMenu rItem, byte quantity)
{
    CNDOrder rOrder = findOrderByID(orderID);
    rOrder.addItem(rItem, quantity);
}

public boolean deleteOrderItem(int orderID, int index)
{
     CNDOrder rOrder = findOrderByID(orderID);
     if(rOrder == null)
       return false;
     return rOrder.deleteItem(index);
}

public void loadFiles() throws Exception
{
    loadStaffFile();
    loadMenuFile();    
}

private void loadStaffFile() throws Exception
{
   try {
	   
	   InputStream is = getClass().getResourceAsStream(STAFF_FILE);
	    InputStreamReader isr = new InputStreamReader(is);  
	    BufferedReader reader = new BufferedReader(isr);
	    
   //  BufferedReader reader = new BufferedReader(new FileReader(STAFF_FILE));
     String line = reader.readLine();
     System.out.println("In loadStaffFile");
     while (line != null) {
        String[] record = line.split(",");
        String id = record[0].trim();
        String password = record[1].trim();
        String firstName = record[2].trim();
        String lastName = record[3].trim();
        System.out.println("IN loadStaffFile ID"+id);
        CNDStaff staff = new CNDStaff(id,lastName, firstName, password);
    	staffList.add(staff);
		line = reader.readLine();		
     }
     reader.close();
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
    	 InputStream is = getClass().getResourceAsStream(MENU_FILE);
 	    InputStreamReader isr = new InputStreamReader(is);  
 	   BufferedReader reader = new BufferedReader(isr);
    //    BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE));
        String line = reader.readLine();

        while (line != null) {
            String[] record = line.split(",");

            String id = record[0].trim();
            String name = record[1].trim();
            String price = record[2].trim();
            String type = record[3].trim();

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

public int addOrder(int staffID, String staffName)
{
    int newOrderID = ++todaysOrderCounts;
    CNDOrder newOrder = new CNDOrder(staffID, staffName);
    newOrder.setOrderID( newOrderID);
    orderList.add(newOrder);
    return newOrderID;
}

public CNDOrder   findOrderByID(int id)
{
    Iterator<CNDOrder> it = orderList.iterator();
    CNDOrder           re = null;
    boolean         found = false;
    
    if(id < 0){
        return null;
    }
    
    while (it.hasNext() && !found) {
        re = it.next();  
        if( re.getOrderID() == id)
        {
            found = true;
        }
    }
    
    if(found)
        return re;
    else        
        return null;
}

public boolean closeOrder(int orderID)
{
    CNDOrder rOrder = findOrderByID(orderID);
   if(rOrder == null)
       return false;
    rOrder.setState(CNDOrder.ORDER_CLOSED);
    return true;
}

public boolean cancelOrder(int orderID)
{
    CNDOrder rOrder = findOrderByID(orderID);
   if(rOrder == null)
       return false;
    rOrder.setState(CNDOrder.ORDER_CANCELED);
    return true;
}

public boolean deleteOrder(int orderID)
{
    CNDOrder rOrder = findOrderByID(orderID);
   if(rOrder == null)
       return false;
    orderList.remove(rOrder);
    todaysOrderCounts--;
    return true;
}

public double getOrderTotalCharge(int orderID)
{
    CNDOrder  re = findOrderByID(orderID);
    if(re == null)
        return -1;
    return re.getTotal();
}

public int getOrderState(int orderID)
{
    CNDOrder  re = findOrderByID(orderID);
    if(re == null)
        return -1;
    return re.getState();
}

public CNDStaff findStaffByID(String inputID) {

	System.out.println("IN findStaffByID inputID"+inputID);
    Iterator<CNDStaff> it = staffList.iterator();
    CNDStaff           re = null;
    boolean         found = false;
    
    if(inputID==null || inputID==""){
        return null;
    }
    
    while (it.hasNext() && !found) {
    	System.out.println("IN findStaffByID re"+re);	
        re = (CNDStaff)it.next();  
        System.out.println("IN findStaffByID re"+re.getID());	
        if( re.getID().equals(inputID))
        {
        	System.out.println("IN findStaffByID FOUND");
            found = true;
        }
    }
    
    if(found)
        return re;
    else        
        return null;
}

public void addCNDMenu(int newID, String newName, double newPrice, byte newType) throws Exception
{
    CNDMenu newCNDMenu = new CNDMenu(newID, newName,newPrice, newType);
    menuList.add(newCNDMenu);
}
}