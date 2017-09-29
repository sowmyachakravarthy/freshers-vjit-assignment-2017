

	import java.util.ArrayList;
import java.util.Iterator;

	public class CNDOrder
	{
	    final public static int ORDER_CLOSED = 1;
	    final public static int ORDER_CANCELED = 2;
	    
	    private int orderID;
	    private int staffID;
	    private String staffName;
	    private String date;
	    private int state;  //0:arrive 1:closed 2:canceled
	    private double total;
	    private ArrayList<CNDOrderDetail> orderDetailList = new ArrayList<CNDOrderDetail>();

	    /**
	     * Constructor for objects of class Order
	     */
	    public CNDOrder(int newStaffID, String newStaffName)
	    {
	        this.orderID =-1;
	        this.state = 0;
	        this.staffID = newStaffID;
	        this.staffName = newStaffName;
	        this.total = 0;
	    }
	    /**
	     *Getter
	     */
	    int getOrderID()
	    {
	        return this.orderID;
	    }
	    int getStaffID()
	    {
	        return this.staffID;
	    }
	    String getStaffName()
	    {
	        return this.staffName;
	    }
	    int getState()
	    {
	        return this.state;
	    }
	    double getTotal()
	    {
	        return this.total;
	    }
	    ArrayList<CNDOrderDetail> getOrderDetail()
	    {
	        return this.orderDetailList;
	    }
	    
	     /**
	     * Setter
	     */
	    public void setOrderID(int newID)
	    {
	        this.orderID = newID;
	    }
	    
	    public void setState(int state)
	    {
	        this.state = state;
	    }
	    
	    public void addItem(CNDMenu rNewMenuItem, byte quantity)
	    {
	        Iterator<CNDOrderDetail> it = orderDetailList.iterator();
	        CNDOrderDetail re;
	        
	        boolean found = false;
	        
	        while( it.hasNext() && !found)
	        {
	            re = it.next();
	            if( rNewMenuItem.getID() == re.getItemID())
	            {
	                found = true;
	                re.addQuantity(quantity);
	            }
	        }
	        
	        if(!found)
	        {
	            CNDOrderDetail detail = new CNDOrderDetail(rNewMenuItem, quantity);
	            orderDetailList.add(detail);
	            
	        }
	        
	        calculateTotal();
	    }
	    
	    public boolean deleteItem(int index)
	    {
	        try
	        {
	            orderDetailList.remove(index);
	            calculateTotal();
	            return true;
	        }
	        catch(Exception e)
	        {
	            //System.out.println(e.toString()  + ":" + e.getMessage());
	             return false;
	        }
	    }
	    
	    public void  calculateTotal()
	    {
	        total = 0;
	        CNDOrderDetail re;
	         Iterator<CNDOrderDetail> it = orderDetailList.iterator();
	         while (it.hasNext()) {
	            re = it.next();
	            total += re.getTotalPrice();
	        }
	    }
		public double getPrice1() {
			// TODO Auto-generated method stub
			return 0;
		}
		public int getPrice() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

