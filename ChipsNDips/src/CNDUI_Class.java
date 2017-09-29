import java.util.Iterator;
import java.util.Scanner;

public class CNDUI_Class {
	
	private Scanner inputScanner;
    private String loginUserName;
    private CNDData crCNDData; 
    private String  todaysDate;
    
    public CNDUI_Class(CNDData rCNDData) {
        this.inputScanner = new Scanner(System.in);
        this.loginUserName = "";
        this.crCNDData = rCNDData;
    }
  
     private void setLoginUserName(String newName)
     {
         this.loginUserName = newName;  
     }
     
     public void setTodaysDate(String today)
     {
         this.todaysDate = today;
     }
    
     
     private void displayTitle(String title)
     {
         String output = String.format("// %-65s//", title);
         displayMessage("//////////////////////////////////////////////////////////////////////");
         displayMessage(output);
         displayMessage("//////////////////////////////////////////////////////////////////////");
     }
     
    public void clearScreen() {
        System.out.println('\u000c');
    }   
    
    
    public void displayMessage(String message)
    {
         System.out.println(message);
    }
    
    public void displayErrorMessage(String message)
    {
         System.out.println("** Error:" + message + "**");
    }   
    
    public String userInput()
    {
        String result = inputScanner.next();
        inputScanner.nextLine();
        return result;
    }
    
    public void setUserName( String userName)
    {
        setLoginUserName(userName);
    }
    
    public void finish()
    {
        clearScreen();
    }
    
    public void showMainMenu(int userType)
    {
        clearScreen();
        displayTitle("Main menu");
        if(loginUserName != "")
            displayMessage("Login user:" + loginUserName);
        displayMessage("----------[Menu]----------");
        displayMessage("[1] Login");
             
    }
   
     public void loginView()
    {
        clearScreen();
        displayTitle("Login");
    }
   
    public void showOrderMenu()
    {
        clearScreen();
        displayTitle("Order");
        displayMessage("[1] Create order");
        displayMessage("[2] Update order");
        displayMessage("[3] Close order");
        displayMessage("[4] Cancel order");
        displayMessage("[5] Show order list");
        displayMessage("[Q] Back to main menu");
        displayMessage("-----------------------------------------");
    }
    
    public void addCNDMenuView()
    {
        clearScreen();
        displayTitle("Add menu item");
    }
    
    public void editCNDMenuView(CNDMenu rCNDMenu)
    {
        clearScreen();
        showCNDMenuData(rCNDMenu);
        displayMessage("-----------------------------------------");
        displayMessage("[1]:Name");
        displayMessage("[2]:Price");
        displayMessage("[3]:Type");
        displayMessage("[4]:Set promotion price");
        displayMessage("[5]:Reset item state");
        displayMessage("[Q]:Quit");
        displayMessage("-----------------------------------------");
    }
  
    public void editOrderView()
    {
        clearScreen();
        displayTitle("Edit Order");
        displayMessage("[1] Add new item");
        displayMessage("[2] Delete item");
        displayMessage("[3] Show order detail");
        displayMessage("[Q] Quit");
        displayMessage("-----------------------------------------");
    }
    
    public void addOrderItemView()
    {
        clearScreen();
        displayTitle("Add Order Item");
        sub_showCNDMenuList();
    }
    
    public void deleteOrderItemView()
    {
        clearScreen();
        displayTitle("Delete Order Item");
    }
    
    public void closeOrderView()
    {
        clearScreen();
        displayTitle("Close order");
        showOrderList();
    }
    
    private void showOrderList() {
		// TODO Auto-generated method stub
		
	}

	public void cancelOrderView()
    {
        clearScreen();
        displayTitle("Cancel order");
        showOrderList();
    }
    
    public void generateReportView()
    {
        clearScreen();
        displayTitle("Generate reports");
        displayMessage("[1] Sales repors");
        displayMessage("[2] Payment list");
        displayMessage("[Q] Quit");
        displayMessage("-----------------------------------------");
    }
    

    public void showCNDMenuList()
    {
        clearScreen();
        displayTitle("Menu List");
        sub_showCNDMenuList();
    }
    
    private void sub_showCNDMenuList()
    {
        Iterator<CNDMenu> it = crCNDData.getMenuList().iterator();
        
        while (it.hasNext()) {
            CNDMenu re = (CNDMenu)it.next();
             byte menuType = re.getType();
             String strMenuType;
            switch( menuType)
            {
                case CNDMenu.MAINCOURSE:
                strMenuType = "Maincourse";
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
           if(re.getState() == CNDMenu.PROMOTION_ITEM)
           {
               output += " ** Today's Special!! **";
            }
            displayMessage(output);
        }
    }
    
  
    public void showCNDMenuData( CNDMenu rCNDMenu)
    {
        displayTitle("Menu item Data");
        displayMessage("Name:" + rCNDMenu.getName());
        
        displayMessage("Price:" + rCNDMenu.getPrice());
       
        
        byte menuType = rCNDMenu.getType();
        switch( menuType)
        {
            case CNDMenu.MAINCOURSE:
            displayMessage("Type:Maincourse");
            break;
            case CNDMenu.DRINKS:
            displayMessage("Type:Drinks");
            break;
            case CNDMenu.STARTERS:
            displayMessage("Type:Starters");
            break;
            case CNDMenu.DESSERT:
            displayMessage("Type:Dessert");
            break;
            default:
            displayMessage("Type:Undefined");
            break;
        }
           displayMessage("State: Regular item");
    }
}

