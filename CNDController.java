import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CNDController {
	private CNDUI_Screen cView;
    private CNDData    cCNDData;
	private String errorMessage;
	private String      todaysDate;
	private int userType;
    //public class CNDData {
	
	public final static int USER_ANONYMOUS = 0;


public CNDController()
{
	System.out.println("In CNDController");
    this.cCNDData = new CNDData();
    try
    {
    	System.out.println("In CNDController in try");
        //cCNDData.loadFiles();
    }
    catch (Exception e){
    	
    }
    
    cView = new CNDUI_Screen( this);
    
    Date date = new Date();
    SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
    todaysDate = stf.format(date);
    
    cView.setVisible(true);
    //cView.setTodaysDate(todaysDate);
    
   
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

/***********************************************************
 * Login 
 **********************************************************/
//----------------------------------------------------------
// Find user
//----------------------------------------------------------  
public boolean loginCheck( int inputID, String inputPassword)
{
    String searchClassName;
    
    //---------search user----------
    CNDStaff   rStaff = cCNDData.findStaffByID(inputID);

    
    if( rStaff != null)//User data is found
    {        
        if(rStaff.getPassword().equals(inputPassword))
            {               
                int currentUserID = inputID;
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


     
        

//----------------------------------------------------------
// Logout (Set state as Anonymous)
//----------------------------------------------------------
public void userLogout()
{
    
    int currentUserID = 0;
    cView.setLoginUserName("");
}
public ArrayList<String> createCNDMenuList(int i) {
	// TODO Auto-generated method stub
	return null;
}
}