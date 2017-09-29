import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CNDController {
	

    //define scene
    public final static int SCENE_MAIN_MENU = 0;    //main menu
    public final static int SCENE_LOGIN = 1;        //login
    public final static int SCENE_LOGOUT = 2;
    public final static int SCENE_MENU_LIST = 3;
    private CNDUI_Class cView; //Reference of userinterface
    private CNDData    cCNDData;
    
    //parameter
    private int scene;
    private int state;  //normally "0", if something happen (ex. quit program) this have some value.
    private int userType;
    private String currentUserID;
    private String currentUserName;
    private String    todaysDate;
    
    public CNDController()
    {
        this.cCNDData = new CNDData();
        this.cView = new CNDUI_Class(this.cCNDData);
        this.scene = SCENE_MAIN_MENU;
        this.currentUserID = "";
        this.currentUserName = "";
        
        //get todays date
        Date date = new Date();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
        todaysDate = stf.format(date);
        cView.setTodaysDate(todaysDate);
        
        try
        {
            cCNDData.loadFiles();
            this.state = 0;
        }
        catch(Exception de)
        {
            this.state = -1;
            printErrorMessageToView(de.getMessage());
        }
    }
    
    /***********************************************************
     * Main menu 
     **********************************************************/
    //----------------------------------------------------------
    // Select number from mein menu
    //----------------------------------------------------------
    private void selectCNDMenu()
    {
         cView.showMainMenu( userType);
         
         int selection = 0;   
    }
         /*while( selection == 0 && this.state == 0)
         {
            try
            {
                printMessageToView("Please make a selection:");
                 String key = cView.userInput();
                
               /* if(key.equalsIgnoreCase("Q"))   //quit program
                {
                    printMessageToView("Are you sure to quit program? (Y:YES)");
                    key = cView.userInput();
                    if (key.equalsIgnoreCase("Y")) {
                        this.state = 1; //If state > 0, program will finish 
                    }
                    else
                    {
                        //reflesh view
                        cView.showMainMenu(userType);
                    }*/
               // }
         //}
               
            
                
                //String errMessage = e.toString()  + ":" + e.getMessage();
                //printMessageToView(errMessage);
            
         
    
    private boolean selectionCheck(int selection) {
		// TODO Auto-generated method stub
		return false;
	}

	public void mainLoop()
    {
         while( state == 0)
        {
            switch( this.scene)
            {
                case SCENE_MAIN_MENU:
                   selectCNDMenu();
                   break;
                case SCENE_LOGIN:
                    userLogin();    
                    break;
                case SCENE_LOGOUT:
                    userLogout();    
                    break;
                case SCENE_MENU_LIST:
                    showCNDMenuList();
                    break;
                default:
                    this.scene = SCENE_MAIN_MENU;
                    break;
            }
            if(state == -1) //error
                printErrorMessageToView("Error");
        }
        
        //finish program
        cView.finish();
    }
    //----------------------------------------------------------
    // Check if the number selected is appropriate and
    // if the user is eligible to operate
    //----------------------------------------------------------
   
    /***********************************************************
     * Login mode
     **********************************************************/
    private void userLogin()
    {
        cView.loginView();
        
        String key = cView.userInput();
        if( key.equalsIgnoreCase("Q"))// back to main menu
        {
            scene = SCENE_MAIN_MENU;
            return;
        }
        
    }
    
    //----------------------------------------------------------
    // Find user
    //----------------------------------------------------------
    private void loginCheck( )
    {
        String searchClassName = null;
        String     inputID = "";
        String  iuputPassword = "";
        String  key = "";
            
        printMessageToView("Enter your ID.");
        while(inputID=="")
        {
            key = cView.userInput();
            try{
                inputID = key;
            }
            catch(Exception e)
            {
                //printMessageToView("Only number is accepted.\nEnter your ID.");
            }
        }
        printMessageToView("Enter your password.");
        iuputPassword = cView.userInput();
            
        //---------search user----------
        CNDStaff   rCNDStaff = cCNDData.findStaffByID(inputID);

        
        if( rCNDStaff != null)//User data is found
        {
            //Search only particular target(Manager or Employee)
            if( rCNDStaff.getClass().getName().equalsIgnoreCase(searchClassName))
            {
                if(rCNDStaff.getPassword().equals(iuputPassword))
                {
                    printMessageToView("Login successful!!");
                    
                    currentUserID = inputID;
                    currentUserName = rCNDStaff.getFullName();
                    cView.setUserName(currentUserName);  //show user name on the view
                }
                else
                {
                    printMessageToView("Enter the correct password.");
                }
            }
            else    //ID is found but type(Manager or Employee) is umnatching
            {
                printMessageToView("Not found.");
            }
        }
        else
            printMessageToView("Not found.");
        

        pause(2);    
    }   
    //----------------------------------------------------------
    // Logout (Set state as Anonymous)
    //----------------------------------------------------------
    private void userLogout()
    {
        //cView.loginView();
        printMessageToView("Are you sure to log out? (YES:y)");
        
        String key = cView.userInput();
        if(key.equalsIgnoreCase("Y"))
        {
            currentUserID = "";
            currentUserName = "";
            cView.setUserName(currentUserName);
        }
        scene = SCENE_MAIN_MENU;
    }
    /***********************************************************
     * Edit employee mode
     **********************************************************/
     //----------------------------------------------------------
    // Choose edit mode (1:Add 2:Update 3:Delete)
    //----------------------------------------------------------
    /*private void chooseEditStaffMode()
    {
        String  key;
        int     inputNumber = 0;
        
        cView.CNDstaffManagementView();
        printMessageToView("Choose number:");
        key = cView.userInput();
        
        if(key.equalsIgnoreCase("Q"))   //Back to main menu
        {
            scene = SCENE_MAIN_MENU;
            return;
        }
        
        while(inputNumber == 0)
        {
            try
            {
                inputNumber = Integer.parseInt(key);     
                switch(inputNumber)
                {
                    case 1: //add new employee
                        addNewStaff();
                    break;
                    case 2:
                        updateStaff();
                    break;
                    case 3:
                        deleteStaff();
                    break;
                    default:
                        printMessageToView("Choose 1 to 3:");
                        key = cView.userInput();
                    break;
                }
            }
            catch(Exception e)
            {
                printMessageToView("Choose 1 to 3:");
                key = cView.userInput();
            }
        }
    }   
    //----------------------------------------------------------
    // Add new staff
    //----------------------------------------------------------
    private void addNewStaff()
    {
        int newID=0;
        String newFirstName;
        String newLastName;
        String newPassword;
        String key;
        
        boolean done = false;
        //-------------------- loop until new staff is added or enter "Q" ----------
        while(!done)
        {
            cView.addNewStaffView();
            newID = generateID();
            if (newID == 0)
            {
                //back to mein menu
                scene = SCENE_MAIN_MENU;
                return;
            }

            printMessageToView("Enter first name:");
            newFirstName = cView.userInput();
            printMessageToView("Enter last name:");
            newLastName = cView.userInput();    
            printMessageToView("Enter password:");
            newPassword = cView.userInput();
            
            printMessageToView("Is the staff manager?(Y/N)");
            key = cView.userInput();
            int staffType = 0;  //1:manager 2:employee
            
            while(staffType == 0)
            {
                if(key.equalsIgnoreCase("Y"))
                {
                    staffType = 1;
                    break;
                }
                else if(key.equalsIgnoreCase("N"))
                {
                    staffType = 2;
                    break;
                }
                else
                {
                    printMessageToView("Please enter 'Y' or 'N'");
                    key = cView.userInput();   
                }
            }
            //Check all input
            printMessageToView("--------------------------------------");
            printMessageToView("NewID:" + newID);
            printMessageToView("New staff name:" + newFirstName + " " + newLastName);
            printMessageToView("Password:" + newPassword);
            switch(staffType)
            {
                case 1:
                printMessageToView("The staff will be added as manager.");
                break;
                case 2:
                printMessageToView("The staff will be added as employee.");
                break;
            }
            
            printMessageToView("\nOK? (Y:yes)");
            key = cView.userInput();
            
            if(key.equalsIgnoreCase("Y"))
            {
                boolean isManager = false;
                if(staffType == 1)
                    isManager = true;
                try
                {
                    cDatabase.addStaff(newID, newLastName, newFirstName, newPassword, isManager);
                    printMessageToView("New staff information is added.");
                    done = true;
                }
                catch(DatabaseException dbe)
                {
                    printErrorMessageToView(dbe.getErrMessage());
                    pause(2);
                }
            }
        }
        //---------------- Staff is added (loop end)-----------------------------
        
        printMessageToView("Please enter something to exit");
        key = cView.userInput();
        scene = SCENE_MAIN_MENU;
    }*/
   
    //----------------------------------------------------------
    // Make and check new ID (used by addEmployee method only)
    //----------------------------------------------------------
   /* private int generateCNDMenuID()
    {
        String newID = "";
        String key;
        
        printMessageToView("Choose user ID for new staff:");
        key = cView.userInput();
        
        while(newID == "")
        {
            //Back to main menu
            if(key.equalsIgnoreCase("Q"))
                return 0;
            try
            {
                ///newID = Integer.parseInt(key);
                if(newID > 9999)
                {
                    printMessageToView( "Please enter less than 10000");
                    key = cView.userInput();
                    newID = 0;
                }
                else
                {
                    //Check if there is same ID
                    CNDStaff   rStaff = cCNDData.findStaffByID(newID);
                    //if(found)
                    if(rStaff != null)
                    {
                        printMessageToView( "ID:" + newID + "is already used by " + rStaff.getFirstName() + " "
                                            + rStaff.getLastName() + ".");
                        printMessageToView("Please try another number:");
                        key = cView.userInput();
                        newID = 0;
                    }
                }
            }
            catch(Exception e)
            {
                printMessageToView("Please enter valid integer.");
                key = cView.userInput();
            }

        }
        return newID;
    }*/
    
    //----------------------------------------------------------
    // Update staff info
    //----------------------------------------------------------
   /* private void updateStaff()
    {
        String  key;
        int     inputNumber = 0;
        Staff   rStaff = null;
        boolean done = false;    
        
        cView.showStaffList();

        //------------- Input ID check ----------------
        while(inputNumber == 0)
        {
            printMessageToView("Choose user ID to edit:");
            key = cView.userInput();
            
            if(key.equalsIgnoreCase("Q"))  
            {
                //scene = SCENE_MAIN_MENU;
                printMessageToView("Transaction is canceled.");
                pause(2);
                return;
            }
            
            try
            {
                //findUser
                inputNumber = Integer.parseInt(key);     
                rStaff = cDatabase.findStaffByID(inputNumber);

                if(rStaff == null)
                {
                    inputNumber = 0;
                    printErrorMessageToView("ID is not found.");
                }
            }
            catch(Exception e)
            {
                printErrorMessageToView("ID must be valid number.");
            }
        }
        //------------- Input ID check end ----------------
        
        //------------- Choose update staff menu ----------------
        cView.updateStaffView(rStaff);
        inputNumber = 0;
        
        while(inputNumber == 0)
        {
            key = cView.userInput();
            
            if(key.equalsIgnoreCase("Q"))
            {
                printMessageToView("Transaction is canceled.");
                pause(2);
                return;
            }
            
            try{
                inputNumber = Integer.parseInt(key);
                if(inputNumber < 1 || 5 < inputNumber)
                {
                    inputNumber = 0;
                    printMessageToView("Input 1 to 5");
                }
            }
            catch(Exception e)
            {
                printMessageToView("Input valid integer:");
            }
        }
        //------------- Choose update staff menu End----------------
       
         //------------- Edit Staff data ----------------
       DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       while(!done){
           cView.clearScreen();
           cView.showStaffData(rStaff);
           try
           {
                switch(inputNumber)
                {
                    case 1: //edit last name
                        printMessageToView("Input new last name:");
                        key = cView.userInput();
                        //rStaff.setLastName(key);
                        cDatabase.editStaffData(rStaff, cDatabase.EDIT_LAST_NAME, key);
                        cView.showStaffData(rStaff);
                        printMessageToView("Please enter something to exit");
                        key = cView.userInput();
                        done = true;
                    break;
                    case 2: //edit first name
                        printMessageToView("Input new first name:");
                        key = cView.userInput();
                        //rStaff.setFirstName(key);
                        cDatabase.editStaffData(rStaff, cDatabase.EDIT_FIRST_NAME, key);
                        cView.showStaffData(rStaff);
                        printMessageToView("Please enter something to exit");
                        key = cView.userInput();
                        done = true;
                    break;
                    case 3:// Forth clock out
                        byte state = rStaff.getWorkState();
                        switch(state)
                        {
                            case Staff.WORKSTATE_ACTIVE:
                                rStaff.clockOut();
                                printMessageToView("Staff:" + rStaff.getFullName() + " has been clocked out.");
                                pause(2);
                                
                                break;
                            case Staff.WORKSTATE_FINISH:
                                printErrorMessageToView("Staff:" + rStaff.getFullName() + " already clocked out.");
                                pause(3);
                                break;
                           default:
                                printErrorMessageToView("Staff:" + rStaff.getFullName() + "has not been on work today.");
                                pause(3);
                                break;
                        }
                        done = true;
                    break;
                    case 4://change start time
                        if(rStaff.getWorkState() == 0)
                        {
                            printErrorMessageToView("You can not change start time of the staff not working.");
                            pause(3);
                            return;
                        }
                        
                        printMessageToView("Enter new start time (HH:mm)");
                        key = cView.userInput();
                        if(key.equalsIgnoreCase("Q"))
                        {
                            printMessageToView("Transaction is canceled.");
                            pause(2);
                            return;
                        }
                        key = todaysDate + " " + key + ":00"; //YYYY/MM/DD HH:mm:ss
                        try
                        {
                            Date newTime = sdf.parse(key);
                            if(rStaff.changeStartTime(newTime))
                            {
                                printMessageToView("Start time has been changed.");
                                printMessageToView("Please enter something to exit");
                                key = cView.userInput();
                                done = true;
                            }
                            else
                            {
                                printErrorMessageToView("changeStartTime error");
                                pause(3);
                            }
                        }
                        catch(ParseException pe)
                        {
                            printErrorMessageToView("Parse error");
                            printMessageToView("Follow the format 'HH:mm' (ex: 16:30)");
                            pause(3);
                        }
                    break;
                    case 5://change finish time
                        if(rStaff.getWorkState() != Staff.WORKSTATE_FINISH)
                        {
                            printErrorMessageToView("You can not change finish time of the staff not working.");
                            pause(3);
                            return;
                        }
                        
                        printMessageToView("Enter new finish time (HH:mm)");
                        key = cView.userInput();
                        if(key.equalsIgnoreCase("Q"))
                        {
                            printMessageToView("Transaction is canceled.");
                            pause(2);
                            return;
                        }
                        key = todaysDate + " " + key + ":00";   //YYYY/MM/DD HH:mm:ss
                        
                        try
                        {
                            Date newTime = sdf.parse(key);
                            if(rStaff.changeFinishTime(newTime))
                            {
                                printMessageToView("Finish time has been changed.");
                                printMessageToView("Please enter something to exit");
                                key = cView.userInput();
                                done = true;
                            }
                            else
                            {
                                printErrorMessageToView("changeFinishTime error");
                                pause(3);
                            }
                        }
                        catch(ParseException pe)
                        {
                            printErrorMessageToView("Parse error");
                            printMessageToView("Follow the format 'HH:mm' (ex: 16:30)");
                            pause(3);
                        }
                    break;
                    default:
                        printErrorMessageToView("This line must not be used!!");
                        printErrorMessageToView("Check Controller class");
                        pause(2);
                    break;
                }
            }
            catch(DatabaseException dbe)
            {
                printErrorMessageToView(dbe.getErrMessage());
                pause(3);
            }
            //------------- End of edit Staff data ----------------
        }
        //----- end loop(loop until done is true) ---------
                
        if(rStaff.getID() == currentUserID)
        {
            currentUserName = rStaff.getFullName();
            cView.setUserName(currentUserName);
        }
    }
    
    //----------------------------------------------------------
    // Delete staff
    //----------------------------------------------------------
    private void deleteStaff()
    {
        String  key;
        int     inputNumber = 0;
        Staff   rStaff = null;
        
        cView.showStaffList();
        printMessageToView("Choose user ID to delete:");
        key = cView.userInput();
        
        if(key.equalsIgnoreCase("Q"))   //Back to main menu
        {
            scene = SCENE_MAIN_MENU;
            return;
        }
        
         while(inputNumber == 0)
        {
            try
            {
                //findUser
                inputNumber = Integer.parseInt(key);     
                rStaff = cDatabase.findStaffByID(inputNumber);
                
                if(rStaff == null)
                {
                    printMessageToView("ID is not found.");
                    pause(2);
                    // back to main menu
                    scene = SCENE_MAIN_MENU;
                    return;
                }
                
                printMessageToView("Staff ID:" + rStaff.getID() + " Name:" + rStaff.getFirstName()
                            + " " + rStaff.getLastName() + "will be deleted. OK? (YES:y)");
                
                key = cView.userInput();
                if(!key.equalsIgnoreCase("Y"))
                {
                    printMessageToView("The transaction is canceled.");
                    pause(2);
                    scene = SCENE_MAIN_MENU;
                    return;
                }
                
                cDatabase.deleteStaff(rStaff);
                /*if(rStaff.getClass().getName().equalsIgnoreCase("Manager"))
                    cDatabase.updateStaffFile(true);//update manager file
                else
                    cDatabase.updateStaffFile(false);//update employee file*/
                    
        /*        printMessageToView("Deleted.");
                pause(2);
            }
            catch(Exception e)
            {
                printErrorMessageToView("ID must be valid number. Input again:");
                key = cView.userInput();
            }
        }
    }*/
     /***********************************************************
     * Edit menu item mode
     **********************************************************/
    //----------------------------------------------------------
    // Choose edit mode (1:Add 2:Update 3:Delete)
    //----------------------------------------------------------
  /*  private void chooseEditCNDMenuMode()
    {
        String  key;
        int     inputNumber = 0;
        
        cView.choseEditCNDMenuView();
        printMessageToView("Choose number:");
        key = cView.userInput();
        
        if(key.equalsIgnoreCase("Q"))   //Back to main menu
        {
            scene = SCENE_MAIN_MENU;
            return;
        }
        
        while(inputNumber == 0)
        {
            try
            {
                inputNumber = Integer.parseInt(key);     
                switch(inputNumber)
                {
                    case 1: //add new employee
                        addNewCNDMenu();
                    break;
                   /* case 2:
                        updateCNDMenu();
                    break;
                    case 3:
                        deleteCNDMenu();
                    break;
                    default:
                        printMessageToView("Choose 1 to 3:");
                        key = cView.userInput();
         
    
    //----------------------------------------------------------
    // Make and check new ID (used by addMenuItem method only)
    //----------------------------------------------------------
    private int generateCNDMenuID()
    {
        int newID = 0;
        String key;
        
        printMessageToView("Choose ID for new item:");
        key = cView.userInput();
        
        while(newID == 0)
        {
            //Back to main menu
            if(key.equalsIgnoreCase("Q"))
                return 0;
            try
            {
                newID = Integer.parseInt(key);
                if(newID > 9999)
                {
                    printMessageToView( "Please enter less than 10000");
                    key = cView.userInput();
                    newID = 0;
                }
                else
                {
                    //Check if there is same ID
                    CNDMenu   rCNDMenu = cCNDData.findCNDMenuByID(newID);
                    //if(found)
                    if(rCNDMenu != null)
                    {
                        printMessageToView( "ID:" + newID + "is already used by " + rCNDMenu.getName());
                        printMessageToView("Please try another number:");
                        key = cView.userInput();
                        newID = 0;
                    }
                }
            }
            catch(Exception e)
            {
                printMessageToView("Please enter valid integer.");
                key = cView.userInput();
            }

        }
        return newID;
    }
    //----------------------------------------------------------
    // Add new menu item
    //----------------------------------------------------------
    private void addNewCNDMenu()
    {
        int newID=0;
        String   newName;
        double  newPrice;
        byte     newType;
        String key;
        
        cView.addCNDMenuView();
        //cView.clearScreen();
        //displayTi("********* Add new item **********");
        
        boolean done = false;
        //-------------------- loop until new item is added or enter "Q" ----------
        while(!done)
        {
            newID = generateCNDMenuID();
            if (newID == 0)
            {
                //back to mein menu
                //scene = SCENE_MAIN_MENU;
                return;
            }

            printMessageToView("Enter item name:");
            newName = cView.userInput();
            
            newPrice = 0;
            printMessageToView("Enter price:");
            key = cView.userInput();
            while(newPrice == 0)
            {
                try
                {
                    newPrice = Double.parseDouble(key);
                    if(newPrice <= 0)
                    {
                        printMessageToView("Enter positive number:");
                        key = cView.userInput();
                        newPrice = 0;
                    }
                }
                catch(Exception e)
                {
                    printMessageToView("Enter valid number:");
                    key = cView.userInput();
                }
            }
            
            newType = 0;
            printMessageToView("Enter item type(1:MAINCOURSE 2:DRINKS 3:STARTERS 4:DESSERT):");
            key = cView.userInput();
            while(newType == 0)
            {
                try
                {
                    newType = Byte.parseByte(key);
                    if(newType < 1 || 4< newType)
                    {
                        printMessageToView("Enter 1 to 4:");
                        key = cView.userInput();
                        newType = 0;
                    }
                }
                catch(Exception e)
                {
                    printMessageToView("Enter valid number:");
                    key = cView.userInput();
                }
            }
            
            //Check all input
            printMessageToView("NewID:" + newID);
            printMessageToView("New item name:" + newName);
            printMessageToView("New item price:" + newPrice);
            switch(newType)
            {
                case CNDMenu.MAINCOURSE:
                    printMessageToView("New item type:MAINCOURSE");
                    break;
                case CNDMenu.DRINKS:
                    printMessageToView("New item type:DRINKS");
                    break;
                case CNDMenu.STARTERS:
                    printMessageToView("New item type:STARTERS");
                    break;
                case CNDMenu.DESSERT:
                    printMessageToView("New item type:DESSERT");
                    break;
            }   
            
            printMessageToView("\nOK? (Y:yes)");
            key = cView.userInput();
            
            if(key.equalsIgnoreCase("Y"))
            {
                try
                {
                    cCNDData.addCNDMenu(newID, newName, newPrice, newType);
                    printMessageToView("New menu item is added.");
                }
                catch(Exception dbe)
                {
                    printErrorMessageToView("Add menu item error.");
                }
                
                done = true;
            }
        }
        //---------------- MenuItem is added (loop end)-----------------------------
        
        printMessageToView("Please enter something to exit");
        key = cView.userInput();
        //scene = SCENE_MAIN_MENU;
    }
    
    
    private int generateCNDMenuID() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int generateCNDMenuID() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int generateCNDMenuID() {
		// TODO Auto-generated method stub
		return 0;
	}

	//----------------------------------------------------------
    // Edit menu item
    //----------------------------------------------------------
    /*private void updateMenuItem()
    {
        String  key = "";
        int     inputNumber = 0;
        CNDMenu   rCNDMenu = null;
        
        cView.showMenuList();
        printMessageToView("-----------------------------------------");
        
        Object rCNDMenu;
		Object cCNDData;
		while(rCNDMenu == null)
        {
            printMessageToView("Choose menu ID to edit:");
            key = cView.userInput();
            if(key.equalsIgnoreCase("Q"))   //Back to main menu
            {
                printMessageToView("Transaction is canceled.");
                pause(2);
                return;
            }
            
            try
            {
                //findUser
                inputNumber = Integer.parseInt(key);     
                rCNDMenu = (cCNDData).findCNDMenuID(inputNumber);

                if(rCNDMenu == null)
                {
                    printErrorMessageToView("ID is not found.");
                }
            }
            catch(Exception e)
            {
                printErrorMessageToView("ID must be valid number.");
            }
        }
        

            //-------------- Choose Edit number -----------------
          /*  cView.editCNDMenuView(rCNDMenu);
            cView.clearScreen();
            cView.showCNDMenuData(rCNDMenu);
            printMessageToView("\nChoose Edit number\n");
            printMessageToView("1:Name");
            printMessageToView("2:Price");
            printMessageToView("3:Type");
            printMessageToView("4:Set promotion price");
            printMessageToView("5:Reset item state");
            printMessageToView("Q:Quit");
            printMessageToView("Choose Edit number:");
            inputNumber = 0;
            
            while(inputNumber == 0)
            {
                key = cView.userInput();
                if(key.equalsIgnoreCase("Q"))   //Back to main menu
                {
                    printMessageToView("Transaction is canceled.");
                    pause(2);
                    return;
                }
                
                try{
                    inputNumber = Integer.parseInt(key);
                    if(inputNumber < 1 || 5 < inputNumber)
                    {
                        inputNumber = 0;
                        printMessageToView("Enter 1 to 5:");
                    }
                }
                catch(Exception e)
                {
                    printMessageToView("Input valid integer:");
                }
            }
            //-------------- End choosing edit number -----------------
            
            //-------------- Edit item start -----------------
        
        boolean done = false;
        while(!done)
        {
            cView.clearScreen();
            cView.showCNDMenuData(rCNDMenu);
            printMessageToView("-----------------------------------------");
            try
            {
                switch(inputNumber)
                {
                    case 1: //edit name
                        printMessageToView("Input new name:");
                        key = cView.userInput();
                        cCNDData.editCNDMenuData(rCNDMenu, cCNDData.EDIT_ITEM_NAME, key);
                        cView.showCNDMenuData(rCNDMenu);
                        printMessageToView("Please enter something to exit");
                        key = cView.userInput();
                    break;
                    case 2: //edit price
                        printMessageToView("Input new price:");
                        key = cView.userInput();
                        cCNDData.editCNDMenuData(rCNDMenu, cCNDData.EDIT_ITEM_PRICE, key);
                        cView.showCNDMenuData(rCNDMenu);
                        printMessageToView("Please enter something to exit");
                        key = cView.userInput();
                    break;
                    case 3: //edit type
                        printMessageToView("Input new type(1:Maincourse 2:Drinks 3:Starters 4:Dessert):");
                        key = cView.userInput();
                        cCNDData.editCNDMenuData(rCNDMenu, cCNDData.EDIT_ITEM_TYPE, key);
                        cView.showCNDMenuData(rCNDMenu);
                        printMessageToView("Please enter something to exit");
                        key = cView.userInput();
                    break;
                    case 4:
                        printMessageToView("Input promotion price( normaly $" + rMenuItem.gerRegularPrice() + "):");
                        key = cView.userInput();
                        double promotionPrice = Double.parseDouble(key);
                        if(promotionPrice >= rCNDMenu.gerRegularPrice())
                        {
                            printErrorMessageToView("Promotion Price(" + promotionPrice
                                + ") should be lower than normal price(" + rCNDMenu.gerRegularPrice() + ")!!");
                           pause(2);
                           continue;
                        }
                        else if(promotionPrice < 0)
                        {
                            printErrorMessageToView("Enter positive number.");
                           pause(2);
                           continue;
                        }
                        else
                        {
                            /////database//////
                            cDatabase.setCNDMenuAsPromotionItem(rCNDMenu, promotionPrice);
                            cView.showCNDMenuData(rCNDMenu);
                            printMessageToView("Please enter something to exit");
                            key = cView.userInput();
                        }
                    break;
                    case 5:
                        cCNDData.resetMenuState(rCNDMenu);
                        cView.showCNDMenuData(rCNDMenu);
                        printMessageToView("Item state have been initialized.");
                         printMessageToView("Please enter something to exit");
                         key = cView.userInput();
                    break;
                    default:
                        printMessageToView("This line must not be execute!! Please check program.(Controller class)");
                        pause(2);
                    break;
                }
                done = true;
            }
            catch(Exception dbe)
            {
                printErrorMessageToView(dbe.getErrMessage());
                pause(2);
            }
            catch( Exception e)
            {
                printErrorMessageToView("'" + key +  "'" + "is not acceptable. Please enter only number.");
                pause(2);
            }
            //-------------- Edit item end -----------------
        }
        //back to main menu
        //scene = SCENE_MAIN_MENU;
    }
    
    //----------------------------------------------------------
    // Delete menuItem
    //----------------------------------------------------------
    /*private void deleteMenuItem()
    {
        String  key;
        int     inputNumber = 0;
        MenuItem   rMenuItem = null;
        
         while(inputNumber == 0)
        {
            try
            {
                //findUser
                while(rMenuItem == null)
                {
                    cView.showMenuList();
                    printMessageToView("Choose menu item ID to delete:");   
                    key = cView.userInput();
                    if(key.equalsIgnoreCase("Q"))   //Back to main menu
                    {
                        //scene = SCENE_MAIN_MENU;
                        return;
                    }
                    inputNumber = Integer.parseInt(key);     
                    rMenuItem = cDatabase.findMenuItemByID(inputNumber);
                    if(rMenuItem == null)
                    {
                        printMessageToView("Item is not found.:");
                        pause(2);
                    }
                }
  
                printMessageToView("MenuItem ID:" + rMenuItem.getID());
                printMessageToView(" Name:" + rMenuItem.getName());
                printMessageToView("Price:" + rMenuItem.getPrice());
                printMessageToView("will be deleted. OK? (YES:y)");
                
                key = cView.userInput();
                if(!key.equalsIgnoreCase("Y"))
                {
                    printMessageToView("The transaction is canceled.");
                    pause(2);
                    //scene = SCENE_MAIN_MENU;
                    return;
                }
                
                cDatabase.deleteMenuItem(rMenuItem);
                    
                printMessageToView("Deleted.");
                pause(2);
            }
            catch(Exception e)
            {
                printMessageToView("ID must be valid number.");
                pause(2);
            }
        }
    }
    
     /***********************************************************
     * Display database data
     **********************************************************/
    private void showCNDMenuList()
    {
        cView.showCNDMenuList();
        printMessageToView("Please enter something to exit.");
        cView.userInput();
        // back to main menu
        scene = SCENE_MAIN_MENU;
    }
    
 
    private void printMessageToView(String message)
    {
        cView.displayMessage(message);
    }
    
    private void printErrorMessageToView(String message)
    {
        cView.displayErrorMessage(message);
    }
    
    // create pause for some seconds
    private void pause( long secs)
    {
        try
        {
            Thread.currentThread().sleep(secs * 1000);
        }
        catch(InterruptedException e)
        {
           e.printStackTrace();
        }
    scene = SCENE_MAIN_MENU;
    

}
}
