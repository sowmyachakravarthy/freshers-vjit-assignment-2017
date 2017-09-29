import java.util.*;
import java.text.*;

public class CNDStaff
{
    private String ID;
    private String lastName;
    private String firstName;
    private String password;
    private byte    state;
        
   
 
    public CNDStaff()
    {
        ID = "";
        lastName="";
        firstName="";
        state = 0;
    }
    
    public CNDStaff( String newID, String newLastName, String newFirstName, String newPassword)
    {
        setID( newID);
        setLastName(newLastName);
        setFirstName(newFirstName);
        setPassword( newPassword);
        state = 0;       
    }
    
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
    
}
