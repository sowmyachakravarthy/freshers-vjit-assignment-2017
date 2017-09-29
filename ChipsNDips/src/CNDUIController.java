import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CNDUIController {
	private CNDUI_Screen cView;
    private CNDData    cCNDData;
	private String errorMessage;
	private int         currentUserID;
	private String      currentUserName;
   
	private int         todaysOrderCnt;     //Today's order count
    private double      totalSales;         //Today's total sales
    private int         todaysCancelCnt;    //Today's cancel count
    private double      cancelTotal;        //Total cost of today's canceled orders
    


 public CNDUIController()
 {
	System.out.println("In CNDController");
    this.cCNDData = new CNDData();
    try
    {
    	System.out.println("In CNDController in try");
        cCNDData.loadFiles();
    }
    catch (Exception e){
    	
    }
    
    cView = new CNDUI_Screen( this);     
    cView.setVisible(true);   
}
 
private void  setErrorMessage(String errorMessage)
{
    this.errorMessage = errorMessage;
}

public String  getErrorMessage()
{
    String result = this.errorMessage;
    this.errorMessage = "";
    return result;
}

public int getTodaysOrderCnt()
{
    return this.todaysOrderCnt;
}

public int getTodaysCancelCnt()
{
    return this.todaysCancelCnt;
}

public double getTotalSales()
{
    return this.totalSales;
}

public double getCancelTotal()
{
    return this.cancelTotal;
}

public int createOrder()
{
    return cCNDData.addOrder(currentUserID, currentUserName);
   
}

public String getCurrentUserName()
{
    return this.currentUserName;
}

public boolean closeOrder(int closeOrderID)
{
    CNDOrder rOrder = cCNDData.findOrderByID(closeOrderID);
    if( currentUserID != rOrder.getStaffID())
    {
        setErrorMessage("You are not eligible to delete the order.\n(The order belonges to " + rOrder.getStaffName() + ")");
        return false;    
    }
    
    if(rOrder.getState() != 0)
    {
        setErrorMessage("The order is already closed or canceled.");
        return false;
    }
    cCNDData.closeOrder(closeOrderID);
    todaysOrderCnt++;
    totalSales += rOrder.getTotal();
    return true;
}


public boolean cancelOrder(int cancelOrderID)
{
    CNDOrder rOrder = cCNDData.findOrderByID(cancelOrderID);
    if( currentUserID != rOrder.getStaffID())
    {
        setErrorMessage("You are not eligible to delete the order.\n(The order belonges to " + rOrder.getStaffName() + ")");
        return false;    
    }
    
    if( rOrder.getState() != 0)
    {
        setErrorMessage("The order is already closed or canceled.");
        return false;
    }

    cCNDData.cancelOrder(cancelOrderID);
    todaysCancelCnt++;
    cancelTotal += rOrder.getTotal();
    return true;
}

public int getOrderState(int orderID)
{
    return cCNDData.getOrderState(orderID);
}

public ArrayList<String> createOrderItemlList(int orderID)
{
    CNDOrder rOrder = cCNDData.findOrderByID(orderID);
    ArrayList<String> initData = new ArrayList<String>();

    if(rOrder == null)
    {
        initData.add("No order information");
        return initData;
    }
    
    String output;

    Iterator<CNDOrderDetail> it = rOrder.getOrderDetail().iterator();
    CNDOrderDetail    re;
    int count = 0;
    
    while (it.hasNext()) {
        re = it.next();
        output = String.format("%-4d|%-24s|%5d|%5.2f",
                                ++count, re.getItemName(), re.getQuantity(), re.getTotalPrice());
       initData.add(output);
    }
    if(initData.isEmpty())
        initData.add("No item");
   
    return initData;
}   

public double getOrderTotalCharge(int orderID)
{
    return cCNDData.getOrderTotalCharge(orderID);
}

public boolean addNewOrderItem(int orderID, int addItemID, byte addItemQuantity)
{
    CNDOrder rOrder = cCNDData.findOrderByID(orderID);
    if( currentUserID != rOrder.getStaffID())
    {
        setErrorMessage("You are not eligible to edit the order.\nThe order belonges to " + rOrder.getStaffName() + ")");
        return false;    
    }
    
    CNDMenu    rNewItem = null;
    
    rNewItem = cCNDData.findCNDMenuByID(addItemID);
    if(rNewItem == null)
    {
        setErrorMessage("MenuID[" + addItemID + "]is not found.");
        addItemID = 0;
        return false;
    }
       
     cCNDData.addOrderItem(orderID, rNewItem, addItemQuantity);
     return true;
}

public boolean deleteOrderItem(int orderID, int deleteNo)
{
    CNDOrder rOrder = cCNDData.findOrderByID(orderID);
    if( currentUserID != rOrder.getStaffID())
    {
        setErrorMessage("You are not eligible to delete the order.\nThe order belonges to " + rOrder.getStaffName() + ")");
        return false;    
    }
    
    deleteNo -=1;  //index  starts from zero
    if(!cCNDData.deleteOrderItem(orderID, deleteNo))
    {
        setErrorMessage("Not found.");
        return false;
    }
    return true;
}

 
 public boolean loginCheck( String inputID, String inputPassword)
 {
     CNDStaff   rStaff = cCNDData.findStaffByID(inputID);
    System.out.println("Staff Info"+rStaff);
    
    if( rStaff != null)//User data is found
    {        
        if(rStaff.getPassword().equals(inputPassword))
            {               
               // String currentUserID = inputID;
                String currentUserName = rStaff.getFullName();
                cView.setLoginUserName(currentUserName);  //show user name on the view
              return true; //Login success
            }
            else
            {
                setErrorMessage("Please enter correct password");            
                return false;
            }               
    }
    else
    {
        setErrorMessage("Employee Record Not found.");
        return false;
    }
 }
 
 public void userLogout()
 {    
     cView.setLoginUserName("");
 }

public ArrayList<String> createCNDMenuList(int disuplayMenuType) {

    Iterator<CNDMenu> it = cCNDData.getMenuList().iterator();
    ArrayList<String> initData = new ArrayList<String>();
    System.out.println("CNDUIController createCNDMenuList disuplayMenuType"+disuplayMenuType);
    while (it.hasNext()) {
    	CNDMenu re = (CNDMenu)it.next();
         byte menuType = re.getType();
         if(disuplayMenuType!= 0 && disuplayMenuType != menuType)
            continue;
         String strMenuType;
        switch( menuType)
        {
            case CNDMenu.MAINCOURSE:
            strMenuType = "Main Course";
            break;
            case CNDMenu.DRINKS:
            strMenuType = "Drinks";
            break;
            case CNDMenu.STARTERS:
            strMenuType = "Starters";
            break;
            case CNDMenu.DESSERT:
            strMenuType = "Dessert";
            break;
            default:
            strMenuType = "Undefined";
            break;
        }
        String output = String.format("Menu ID:%4d  Name:%-20s  Price:%5.2f Type:%s",
                                        re.getID(),re.getName(),re.getPrice(),strMenuType);
        System.out.println("CNDUIController createCNDMenuList output"+output);
   
        initData.add(output);
    }
    if(initData.isEmpty())
        initData.add("No orders available");
    return initData;
 }

//Creates list of orders
 public ArrayList<String>  createOrderList()
 {
    Iterator<CNDOrder> it = cCNDData.getOrderList().iterator();
    String state;
    ArrayList<String> initData = new ArrayList<String>();
    String output;
    
    while (it.hasNext()) {
        CNDOrder re = it.next();
        switch(re.getState())
        {
            case CNDOrder.ORDER_CLOSED:
                state = "Closed";
            break;
            case CNDOrder.ORDER_CANCELED:
                state = "Canceled";
            break;
            default:
                state = "-";
            break;
        }
        
        output = String.format("Order ID:%4d  Staff:%-20s  Total:Rs%5.2f  Status:%-8s\n",
                                        re.getOrderID(),re.getStaffName(),re.getTotal(),state);
        initData.add(output);
    }
    if(initData.isEmpty())
        initData.add("No orders available");
    return initData;
 }


}