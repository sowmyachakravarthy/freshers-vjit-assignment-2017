import java.util.*;
import java.text.*;

public class CNDStaff
{
    private String ID;
    private String lastName;
    private String firstName;
    private String password;
    private byte    state;
    
    //private Order[] orderList;

    //protected byte  workState;  //0:not active  1:active (on wark)  2:finish work
    protected Date  startWorkTime;
    protected Date  finishWorkTime;
    //protected double    wageRate;

    //------------------------------------------------------------
    // constructor
    //------------------------------------------------------------
    public CNDStaff()
    {
        ID = "";
        lastName="";
        firstName="";
        startWorkTime = null;
        finishWorkTime = null;
        state = 0;
    }
    
    public CNDStaff( String newID, String newLastName, String newFirstName, String newPassword)
    {
        setID( newID);
        setLastName(newLastName);
        setFirstName(newFirstName);
        setPassword( newPassword);
        startWorkTime = null;
        finishWorkTime = null;
        state = 0;
        //workState = 0;
    }
 
    //------------------------------------------------------------
    // setter
    //------------------------------------------------------------
    protected void setID( String newID)
    {
        this.ID = newID;
    }
    protected void setLastName(String newLastName)
    {
        this.lastName = newLastName;
    }
    protected void setFirstName(String newFirstName)
    {
        this.firstName = newFirstName;
    }
    protected void setPassword(String newPassword)
    {
        this.password = newPassword;
    }
    protected void setWorkState(byte newState)
    {
        this.state = newState;
    }

   
    //------------------------------------------------------------
    // getter
    //------------------------------------------------------------
    public String getID()
    {
        return this.ID;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public String getFullName()
    {
        String fullName = this.firstName + " " + this.lastName;
        return fullName;
    }
    public String getPassword()
    {
        return this.password;
    }
   
    public static final byte WORKSTATE_NON_ACTIVE = 0;
    public static final byte WORKSTATE_ACTIVE = 1;
    public static final byte WORKSTATE_FINISH = 2;
    public byte getWorkState()
    {
        return this.state;
    }
    
    public String getStartTime()
    {
        if(startWorkTime == null)
            return "getStartTime Error";
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(startWorkTime);
    }
    
    public String getFinishTime()
    {
        if(finishWorkTime == null)
            return "getFinishTime Error";
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(finishWorkTime);
    }
    //------------------------------------------------------------
    // other methods
    //------------------------------------------------------------
    public void clockIn()
    {
        startWorkTime = new Date(System.currentTimeMillis());
        state = WORKSTATE_ACTIVE;
    }
    
    public boolean clockOut()
    {
        if(state != WORKSTATE_ACTIVE)
            return false;
        finishWorkTime = new Date(System.currentTimeMillis());
        state = WORKSTATE_FINISH;
        return true;
    }
    
   
}

