import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;

public class CNDUI_Screen extends JFrame implements ActionListener
{
    private Container       con;
    private CNDUIController  rcCNDController;
    private String          currentUserName;
    
    // components for menu
    private JMenuBar   menuBar;
    private JMenu      mnFile;
    private JMenuItem  mntm1, mntm2;
  
    //Master Panel 
     private JPanel         mainPanel;
    
    //Head panel (North)
    private JPanel         headPanel;
    private JLabel         headTitle;
    private JButton        headBtnLogin;
    private JButton        headBtnLogout;
    
    //Main button panel(WEST)
    private JPanel         mainBtnsPanel;
    // Main buttons

    private JButton        mainBtnShowCNDMenu;
    private JButton        mainBtnManageOrder;
   
    private JPanel         homePanel;
    private JPanel         infoPanel;
    private JLabel         labelLoginUserName;
    private JTextArea      taMessage;
    
    //-------- Contents panel --------------
    // components for home panel
   
    private LoginPanel          cLoginPanel;
    private CNDMenuListPanel       cCNDMenuListPanel;
    private OrderListPanel cCNDOrderListPanel;
    private OrderDetailPanel cCNDOrderDetailPanel;


    private final static int WINDOW_X = 100;
    private final static int WINDOW_Y = 100;
    private final static int WINDOW_WIDTH = 900;
    private final static int WINDOW_HEIGHT = 600;
    /**
     * Constructor for objects of class CNDUIclass
     */
    public CNDUI_Screen(CNDUIController rCNDController)
    {
        this.rcCNDController = rCNDController;
        this.con = getContentPane();
        
        // Set frame
        setTitle("Chips 'N' Dips");
        setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createMasterPanelConpornents();
        currentUserName = "";
        setLoginUserName(currentUserName);
        
        homePanel = new JPanel();
       // homeImage = new JLabel();
        
        //------- Create main content panels
        //Home panel
        homePanel = new JPanel();
       
                
        homePanel.setBackground(Color.WHITE);
        mainPanel.add("Home", homePanel);
        
        cLoginPanel = new LoginPanel();
        mainPanel.add("Login", cLoginPanel);
        cCNDMenuListPanel = new CNDMenuListPanel();
        mainPanel.add("CNDMenuList", cCNDMenuListPanel);
        cCNDOrderListPanel = new OrderListPanel();
        mainPanel.add("OrderList", cCNDOrderListPanel);
        cCNDOrderDetailPanel = new OrderDetailPanel();
        mainPanel.add("OrderDetail", cCNDOrderDetailPanel);      
       
    }
    
    private void createMasterPanelConpornents()
    {
        // Add menu to frame
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntm1 = new JMenu("[1] Login");
        mnFile.add(mntm1);
        mntm1.addActionListener(this);
        
        mntm2 = new JMenu("[2] Exit");
        mnFile.add(mntm2);
        mntm2.addActionListener(this);
        
        //----------- Create main panels ------------
        con.setLayout(new BorderLayout());
        
        //head panel
        headPanel = new JPanel();
        headPanel.setBackground(Color.BLUE);
        headPanel.setLayout(new FlowLayout());
        headTitle = new JLabel("Chips 'N' Dips Restaurant Management System");
        headTitle.setForeground(Color.WHITE);
        headTitle.setPreferredSize(new Dimension(500, 30));
        headTitle.setFont(new Font("Arial", Font.BOLD , 18));
        headBtnLogin = new JButton("Login");
        headBtnLogin.addActionListener(this);
        headBtnLogout = new JButton("Logout");
        headBtnLogout.addActionListener(this);
        headPanel.add(headTitle);
        headPanel.add(headBtnLogin);
        headPanel.add(headBtnLogout);
        con.add(headPanel, BorderLayout.NORTH);
        
        //main content panel
        mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new CardLayout());
        con.add(mainPanel, BorderLayout.CENTER);
        
        //main operate buttons panel
        mainBtnsPanel = new JPanel();
        mainBtnsPanel.setLayout(new GridLayout(0,1));
        
        mainBtnShowCNDMenu = new JButton("Show menu");
        mainBtnShowCNDMenu.addActionListener(this);
        mainBtnsPanel.add(mainBtnShowCNDMenu);
        
        mainBtnManageOrder = new JButton("Order management");
        mainBtnManageOrder.addActionListener(this);
        mainBtnsPanel.add(mainBtnManageOrder);       
        
        con.add(mainBtnsPanel, BorderLayout.EAST);
        
        //Information panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        labelLoginUserName = new JLabel();
        labelLoginUserName.setPreferredSize(new Dimension(150, 50));
        taMessage = new JTextArea(3,50);
        taMessage.setEditable(false);
        taMessage.setText("Welcome!!");
        taMessage.setOpaque(true);
        LineBorder border = new LineBorder(Color.BLACK, 3, true);
        taMessage.setBorder(border);
        taMessage.setBackground(Color.WHITE);
        infoPanel.add(labelLoginUserName);
        infoPanel.add(taMessage);
        con.add(infoPanel, BorderLayout.SOUTH);
    }
    

    public void setLoginUserName(String newName)
    {
        currentUserName = newName;
         if(newName == "")
         {
             labelLoginUserName.setText("Please login first.");
         }
         else
         {
            labelLoginUserName.setText("<html>Login user<br>" + newName + "</html>");
        }
    }

    
    public void displayMessage(String message)
    {
        taMessage.setForeground(Color.BLACK);
        taMessage.setText(message);
    }
    
    public void displayErrorMessage(String message)
    {
        taMessage.setForeground(Color.RED);
        taMessage.setText(message);
    }
    
  
    final static int DIALOG_YES = JOptionPane.YES_OPTION;
    final static int DIALOG_NO = JOptionPane.NO_OPTION;
    final static int DIALOG_CANCEL = JOptionPane.CANCEL_OPTION;
    
    public int showYesNoDialog(String title, String message)
    {
        int option = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);
        return option;
    }
    
    public int showYesNoCancelDiaglog(String title, String message)
    {
        int option = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_CANCEL_OPTION, 
        JOptionPane.QUESTION_MESSAGE);
        return option;
    }
    
    public void showErrorDialog(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public void showConfirmDialog(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }
        
    private int getIDfromString(String stringLine, int length)
    {
        int index = stringLine.indexOf("ID:"); //Search string of "ID:"
        if(index == -1)
        {
            showErrorDialog("Error", "String 'ID:' is not found!!");
            return -1;
        }
        
        try
        {
            String strID = stringLine.substring(index + 3, index + 3 + length);
            int id = Integer.parseInt(strID.trim());
            return id;
        }
        catch(Exception e)
        {
            showErrorDialog("Error", "Parse error");
            return -1;
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
   	  if(ae.getSource() == mntm2)
      {
         System.exit(0);
      }
      else if (ae.getSource() == mainBtnShowCNDMenu)
      {
        System.out.println("In ActionPerforemd mainBtnShowCNDMenu");
    	changeMainPanel("CNDMenuList");
    	cCNDMenuListPanel.init();
      } 
      else if (ae.getSource() == mainBtnManageOrder)
      {
          changeMainPanel("OrderList");
          cCNDOrderListPanel.init();
      }    	       
    	           	          	        
        else if (ae.getSource() == headBtnLogin || ae.getSource() == mntm1) {
            changeMainPanel("Login");
            cLoginPanel.init();
            displayMessage("Enter your login ID and password.");
        }
        else if (ae.getSource() == headBtnLogout) {
            if( showYesNoDialog("Logout","Are you sure to logout?") == DIALOG_YES)
            {
            	rcCNDController.userLogout();
                changeMainPanel("Home");
                
            }
        }
      	    
   }
    
  private class LoginPanel extends JPanel implements ActionListener
  {
    private JLabel          lblUserID;
    private JTextField      tfUserID;
    private JLabel          lblPassword;
    private JPasswordField  pwPassword;
    private JButton         btnLoginOK;
    public LoginPanel()
    {
    
        GridBagLayout gbLayout = new GridBagLayout();
        this.setLayout( gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        lblUserID = new JLabel("UserID:");
        lblUserID.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbLayout.setConstraints(lblUserID, gbc);
        this.add(lblUserID);
        
        tfUserID = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbLayout.setConstraints(tfUserID, gbc);
        this.add(tfUserID);
        
        lblPassword = new JLabel("Password:");
        lblPassword.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbLayout.setConstraints(lblPassword, gbc);
        this.add(lblPassword);
        
        pwPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbLayout.setConstraints(pwPassword, gbc);
        this.add(pwPassword);
        
        
        btnLoginOK = new JButton("Login");
        btnLoginOK.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbLayout.setConstraints(btnLoginOK, gbc);
        this.add(btnLoginOK);
    }
        
    private void setUserID(String id)
    {
        tfUserID.setText(id);
    }
        
    private void setPassword(String password)
    {
        pwPassword.setText(password);
    }
        
    public void init()
    {
        setUserID("");
        setPassword("");
        tfUserID.setBackground( UIManager.getColor( "TextField.background" ) ); 
    }
         
    public void actionPerformed(ActionEvent ae) {
       if (ae.getSource() == btnLoginOK)
       {
         System.out.println("In actionPerformed btnLoginOK");
         if (btnLoginOK.getVerifyInputWhenFocusTarget()) {
           btnLoginOK.requestFocusInWindow();
           if (!btnLoginOK.hasFocus()) {    
                return;
           }
        }  
            
       char[] password;
            
            byte state = -1;
            
            String inputID = tfUserID.getText();
            System.out.println("In actionPerformed btnLoginOK inputID"+inputID);
            if(inputID.equals(""))
            {
                displayErrorMessage("Enter user ID");
                return;
            }
            
 
            password= pwPassword.getPassword();
            String inputPassword = new String(password);
            if(inputPassword.equals(""))
            {
                displayErrorMessage("Enter password");
                return;
            }
            
            if( rcCNDController.loginCheck(inputID, inputPassword))
            {
                showConfirmDialog("Message", "Login success!!");
                displayMessage("Welcome, " + currentUserName);
                tfUserID.setText("");
                pwPassword.setText("");
                changeMainPanel("Home");
            }
            else
            {
                displayErrorMessage(rcCNDController.getErrorMessage());
            }
        }
     }
    }
    
    private void  changeMainPanel(String panelName)
    {
        ((CardLayout) mainPanel.getLayout()).show( mainPanel, panelName);
       
    }
    
   //Menu List 
    private class CNDMenuListPanel extends JPanel implements ActionListener
    {
        private JScrollPane     scrollPanel;
        private JTextArea       displayArea;
        private JPanel          btnPanel;
        private JButton         btnAll;
        private JButton         btnMaincourse;
        private JButton         btnDrinks;
        private JButton         btnStarters;
        private JButton         btnDessert;
        
        public CNDMenuListPanel()
        {
        	System.out.println("In CNDMenuListPanel Constructor");
            this.setLayout( new BorderLayout());
            displayArea = new JTextArea();
            displayArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,16));
            displayArea.setEditable(false);
            displayArea.setMargin(new Insets(5, 5, 5, 5));
            scrollPanel = new JScrollPane(displayArea);
            scrollPanel.setPreferredSize(new Dimension(200, 400));
            add(scrollPanel, BorderLayout.CENTER);
            
           btnPanel = new JPanel();
           btnPanel.setLayout( new FlowLayout());
           btnAll = new JButton("All");
           btnAll.addActionListener(this);
           btnMaincourse = new JButton("Maincourse");
           btnMaincourse.addActionListener(this);
           btnDrinks = new JButton("Drinks");
           btnDrinks.addActionListener(this);
           btnStarters = new JButton("Starters");
           btnStarters.addActionListener(this);
           btnDessert = new JButton("Dessert");
           btnDessert.addActionListener(this);
           
           btnPanel.add(btnAll);
           btnPanel.add(btnStarters);
           btnPanel.add(btnMaincourse);
           btnPanel.add(btnDrinks);
           btnPanel.add(btnDessert);
           
           add(btnPanel, BorderLayout.SOUTH);
        }
    
        public void init()
        {
            showCNDMenuList(0);
        }
        
        private void showCNDMenuList(int CNDMenuType)
        {
        	System.out.println("In showCNDMenuList");
            displayArea.setText("");
            ArrayList<String> CNDMenuList = rcCNDController.createCNDMenuList(CNDMenuType);
            System.out.println("In showCNDMenuList CNDMenuList size"+CNDMenuList.size());
            for(int i = 0; i < CNDMenuList.size(); i++)
                displayArea.append(CNDMenuList.get(i) + "\n");
            System.out.println("In showCNDMenuList displayArea "+displayArea);
        }
        
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnAll)
            {
                showCNDMenuList(0);
            }
            else if (ae.getSource() == btnMaincourse)
            {
                showCNDMenuList(CNDMenu.MAINCOURSE);                
            }
            else if (ae.getSource() == btnDrinks)
            {
                showCNDMenuList(CNDMenu.DRINKS);               
            }
            else if (ae.getSource() == btnStarters)
            {
                showCNDMenuList(CNDMenu.STARTERS);
            }
            else if (ae.getSource() == btnDessert)
            {
                showCNDMenuList(CNDMenu.DESSERT);              
            }
        }
    }
    
    private class OrderListPanel extends JPanel implements ActionListener
    {
        private JScrollPane     scrollPanel;
        private JButton         btnNewOrder;
        private JButton         btnEditOrder;
        private JButton         btnCloseOrder;
        private JButton         btnCancelOrder;
        private JLabel          lblTotalSales;
        private JLabel          lblTotalCount;
        private JLabel          lblCancelTotal;
        private JLabel          lblCancelCount;
        private JList           displayList;
        
        public OrderListPanel()
        {
            GridBagLayout gbLayout = new GridBagLayout();
            this.setLayout( gbLayout);
            GridBagConstraints gbc = new GridBagConstraints();
            scrollPanel = new JScrollPane();
            scrollPanel.setPreferredSize(new Dimension(500, 300));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4;
            gbLayout.setConstraints(scrollPanel, gbc);
            this.add(scrollPanel);
            
            lblTotalCount = new JLabel();
            lblTotalCount.setFont(new Font(Font.MONOSPACED,Font.PLAIN,16));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbLayout.setConstraints(lblTotalCount, gbc);
            this.add(lblTotalCount);
            
            lblTotalSales = new JLabel();
            lblTotalSales.setFont(new Font(Font.MONOSPACED,Font.PLAIN,16));
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(lblTotalSales, gbc);
            this.add(lblTotalSales);
            
            lblCancelCount = new JLabel();
            lblCancelCount.setFont(new Font(Font.MONOSPACED,Font.PLAIN,16));
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(lblCancelCount, gbc);
            this.add(lblCancelCount);
            
            lblCancelTotal = new JLabel();
            lblCancelTotal.setFont(new Font(Font.MONOSPACED,Font.PLAIN,16));
            gbc.gridx = 2;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(lblCancelTotal, gbc);
            this.add(lblCancelTotal);
            
            btnNewOrder     = new JButton("New");
            btnNewOrder.addActionListener(this);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gbc.weightx = 0.25;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbLayout.setConstraints(btnNewOrder, gbc);
            this.add(btnNewOrder);
            
            btnEditOrder    = new JButton("Edit");
            btnEditOrder.addActionListener(this);
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbLayout.setConstraints(btnEditOrder, gbc);
            this.add(btnEditOrder);
            
            btnCloseOrder   = new JButton("Close");
            btnCloseOrder.addActionListener(this);
            gbc.gridx = 2;
            gbc.gridy = 3;
            gbLayout.setConstraints(btnCloseOrder, gbc);
            this.add(btnCloseOrder);
            
            btnCancelOrder  = new JButton("Cancel");
            btnCancelOrder.addActionListener(this);
            gbc.gridx = 3;
            gbc.gridy = 3;
            gbLayout.setConstraints(btnCancelOrder, gbc);
            this.add(btnCancelOrder);
            
            displayList = new JList();
        }
        
        private void setTotalCount( int count)
        {
            lblTotalCount.setText("Today's order: " + count);
        }
        
        private void setTotalSales( double sales)
        {
            lblTotalSales.setText("Total:Rs " + sales);
        }
        
        private void setCancelCount( int count)
        {
            lblCancelCount.setText("Canceled orders: " + count);
        }
        
        private void setCancelTotal( double sales)
        {
            lblCancelTotal.setText("Cancel total:Rs " + sales);
        }
        
        private void showOrderList()
        {
            displayList.setListData(rcCNDController.createOrderList().toArray());
            scrollPanel.getViewport().setView(displayList);
            
            setTotalCount(rcCNDController.getTodaysOrderCnt());
            setTotalSales(rcCNDController.getTotalSales());
            setCancelCount(rcCNDController.getTodaysCancelCnt());
            setCancelTotal(rcCNDController.getCancelTotal());
            
        }
        
        public void init()
        {
            showOrderList();
        }
        
        private int getSelectedOrderID()
        {
            String orderLine = (String)displayList.getSelectedValue();
            if (orderLine == null)
                return -1;
                
            return getIDfromString( orderLine, 4);
        }
        
        private String getSelectedOrderStaffName()
        {
            String stringLine = (String)displayList.getSelectedValue();
            if (stringLine == null)
                return null;
                
            int index = stringLine.indexOf("Name:"); 
            if(index == -1)
            {
                showErrorDialog("Error", "String 'Name:' is not found!!");
                return null;
            }
            

            String staffName = stringLine.substring(index + 5, index + 5 + 22);
            return staffName.trim();
        }
        
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnNewOrder)
            {
                
                changeMainPanel("OrderDetail");
                int orderID = rcCNDController.createOrder();
                String staffName = rcCNDController.getCurrentUserName();
                cCNDOrderDetailPanel.init(orderID, staffName);
               
            }
            else if (ae.getSource() == btnEditOrder)
            {
                int orderID = getSelectedOrderID();
                String staffName = getSelectedOrderStaffName();
                if(orderID == -1) return;
                    
                ((CardLayout) mainPanel.getLayout()).show( mainPanel, "OrderDetail");
                
                cCNDOrderDetailPanel.init(orderID, staffName);
            }
            else if (ae.getSource() == btnCloseOrder)
            {
                int orderID = getSelectedOrderID();
                if(orderID == -1) return;
                
                if( showYesNoDialog("Close order","Are you sure to close the order?") == DIALOG_YES)
                {
                    if( !rcCNDController.closeOrder(orderID))
                        displayErrorMessage(rcCNDController.getErrorMessage());
                    showOrderList();
                }
            }
            else if (ae.getSource() == btnCancelOrder)
            {
                int orderID = getSelectedOrderID();
                if(orderID == -1) return;
                
                if( showYesNoDialog("Close order","Are you sure to close the order?") == DIALOG_YES)
                {
                    if(!rcCNDController.cancelOrder(orderID))
                        displayErrorMessage(rcCNDController.getErrorMessage());
                    showOrderList();
                }
            }
        }
    }
    
    //Order Detail Panel      
    private class OrderDetailPanel extends JPanel implements ActionListener, ListSelectionListener
    {
        //Right
        private JLabel          lblRightTitle;
       
        private JScrollPane     menuScrollPanel;
        private JButton         btnAll;
        private JButton         btnMain;
        private JButton         btnDrink;
        private JButton         btnStarter;
        private JButton         btnDessert;
        
        //Left
        private JLabel          lblLeftTitle;
        private JLabel          lblLeftInfo;
        private JScrollPane     orderScrollPanel;
      
        private JPanel          btnPanel;
        private JButton         btnAddItem;
        private JButton         btnDeleteItem;
        private JLabel          lblQuantity;
        private JTextField      tfQuantity;
        
        private JLabel              lblTotalSales;
        private JLabel              lblOrderState;
        private JLabel              lblStaffName;
        private JList               orderItemList;
        private JList               menuList;
        
        private int             currentOrderID;
        private int             orderItemCnt;
        private int             currentOrderState;
        
        private JPanel          orderDetailPanel;
        private JPanel          menuListPanel;
        
        public OrderDetailPanel()
        {
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
           
            
            orderDetailPanel = new JPanel();
           
            GridBagLayout gbLayout = new GridBagLayout();
            orderDetailPanel.setLayout( gbLayout);
            GridBagConstraints gbc = new GridBagConstraints();
            
            lblLeftTitle = new JLabel("Order detail");
            
           
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbLayout.setConstraints(lblLeftTitle, gbc);
            orderDetailPanel.add(lblLeftTitle);
            
            lblLeftInfo = new JLabel("No.   Item name                Quantity    price");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 4;
            gbLayout.setConstraints(lblLeftInfo, gbc);
            orderDetailPanel.add(lblLeftInfo);
            
            orderScrollPanel = new JScrollPane();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.ipadx = 0;
            gbc.ipady = 0;
            gbc.weighty = 1.0;
            
            gbLayout.setConstraints(orderScrollPanel, gbc);
            orderDetailPanel.add(orderScrollPanel);
            
            lblTotalSales = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weighty = 0;
            gbc.gridwidth = 4;
        
            gbLayout.setConstraints(lblTotalSales, gbc);
            orderDetailPanel.add(lblTotalSales);
            
            lblOrderState = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbLayout.setConstraints(lblOrderState, gbc);
            orderDetailPanel.add(lblOrderState);
            
            lblStaffName = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 4;
            gbLayout.setConstraints(lblStaffName, gbc);
            orderDetailPanel.add(lblStaffName);
            
            lblQuantity = new JLabel("Quantity");
            gbc.ipadx = 20;
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(lblQuantity, gbc);
            orderDetailPanel.add(lblQuantity);
            
            tfQuantity = new JTextField();
            tfQuantity.setInputVerifier(new IntegerInputVerifier(1,100));
            tfQuantity.addActionListener(this);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbLayout.setConstraints(tfQuantity, gbc);
            orderDetailPanel.add(tfQuantity);
            
            btnAddItem  = new JButton("Add");
            btnAddItem.addActionListener(this);
            gbc.gridx = 2;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            gbc.gridheight = 2;
            gbLayout.setConstraints(btnAddItem, gbc);
            orderDetailPanel.add(btnAddItem);
            
            btnDeleteItem   = new JButton("Delete");
            btnDeleteItem.addActionListener(this);
            gbc.gridx = 3;
            gbc.gridy = 6;
            gbLayout.setConstraints(btnDeleteItem, gbc);
            orderDetailPanel.add(btnDeleteItem);
            
            
            //Right panel            
            menuListPanel = new JPanel();
            
            menuListPanel.setLayout( gbLayout);
            
            lblRightTitle = new JLabel("Menu list");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.ipadx = 0;
            gbc.gridwidth = 5;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbLayout.setConstraints(lblRightTitle, gbc);
            menuListPanel.add(lblRightTitle);
            
            menuScrollPanel = new JScrollPane();
        
            gbc.gridy = 1;
            gbc.weighty = 1.0;
            
            gbLayout.setConstraints(menuScrollPanel, gbc);
            menuListPanel.add(menuScrollPanel);
            
            btnAll  = new JButton("All");
            btnAll.addActionListener(this);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbLayout.setConstraints(btnAll, gbc);
            menuListPanel.add(btnAll);
            
            btnMain  = new JButton("Main Course");
            btnMain.addActionListener(this);
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbLayout.setConstraints(btnMain, gbc);
            menuListPanel.add(btnMain);
            
            btnDrink  = new JButton("Drinks");
            btnDrink.addActionListener(this);
            gbc.gridx = 2;
            gbc.gridy = 2;
            gbLayout.setConstraints(btnDrink, gbc);
            menuListPanel.add(btnDrink);
            
            btnStarter  = new JButton("Starters");
            btnStarter.addActionListener(this);
            gbc.gridx = 3;
            gbc.gridy = 2;
            gbLayout.setConstraints(btnStarter, gbc);
            menuListPanel.add(btnStarter);
            
            btnDessert  = new JButton("Desserts");
            btnDessert.addActionListener(this);
            gbc.gridx = 4;
            gbc.gridy = 2;
            gbLayout.setConstraints(btnDessert, gbc);
            menuListPanel.add(btnDessert);
            
            LineBorder border = new LineBorder(Color.BLACK, 1, false);
            menuListPanel.setBorder(border);
            orderDetailPanel.setBorder(border);
            this.add(orderDetailPanel);
            this.add(menuListPanel);
                                               
            orderItemList   = new JList();
            orderItemList.setFont(new Font(Font.MONOSPACED,Font.PLAIN,10));
            orderItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            menuList = new JList();
            menuList.addListSelectionListener(this);
            menuList.setFont(new Font(Font.MONOSPACED,Font.PLAIN,10));
            menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
       }
        
        public void init(int orderID, String staffName)
        {
            currentOrderID = orderID;
            currentOrderState = rcCNDController.getOrderState(orderID);
            switch(currentOrderState)
            {
                case CNDOrder.ORDER_CLOSED:
                    setOrderState("Closed");
                break;
                case CNDOrder.ORDER_CANCELED:
                    setOrderState("Canceled");
                break;
                default:
                break;
            }
            
             if(currentOrderState != 0)
            {
                btnAddItem.setEnabled(false);
                btnDeleteItem.setEnabled(false);
            }
            else
            {
                btnAddItem.setEnabled(true);
                btnDeleteItem.setEnabled(true);
            }
            
            refleshOrderDetailList();
            menuList.setListData(rcCNDController.createCNDMenuList(0).toArray());
            menuScrollPanel.getViewport().setView(menuList);
            tfQuantity.setText("");
            tfQuantity.setBackground( UIManager.getColor( "TextField.background" ) );
            setStaffName(staffName);
        }
        
        private void setTotal(double total)
        {
            lblTotalSales.setText("Total charge: Rs" + total);
        }
        
        private void setOrderState(String state)
        {
            lblOrderState.setText("Order state: " + state);
        }
        
        private void setStaffName(String name)
        {
            lblStaffName.setText("Staff name: " + name);
        }
        
        private void refleshOrderDetailList()
        {
            ArrayList<String> list = rcCNDController.createOrderItemlList(currentOrderID);
            setTotal(rcCNDController.getOrderTotalCharge(currentOrderID));
            orderItemCnt = list.size();
            orderItemList.setListData(list.toArray());
            orderScrollPanel.getViewport().setView(orderItemList);
        }
        
        private int getOrderDetailIndexFromString(String orderLine)
        {
            try
            {
                String strIndex = orderLine.substring(0, 4);
                int index = Integer.parseInt(strIndex.trim());
                return index;
            }
            catch(Exception e)
            {
               
                return -1;
            }
        }
        
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnAddItem)
            {
                if (btnAddItem.getVerifyInputWhenFocusTarget()) {
                  btnAddItem.requestFocusInWindow();
                    if (!btnAddItem.hasFocus()) {    
                        return;
                    }
                }  
                
                String menuLine = (String)menuList.getSelectedValue();
                if (menuLine == null)
                    return;

                int     id = getIDfromString( menuLine, 4);
                if(id == -1)
                    return;
                if( tfQuantity.getText().equals(""))
                {
                    showErrorDialog("Error", "Enter quantity!!");
                    return;
                }
                byte    quantity = Byte.parseByte(tfQuantity.getText().trim());
               
                displayMessage("Menu ID = "+ id + " Quantity = " + quantity);
                if( rcCNDController.addNewOrderItem(currentOrderID, id, quantity) == false)
                {
                    displayErrorMessage("addNewOrderItem Error!!\n" + rcCNDController.getErrorMessage());
                }
                refleshOrderDetailList();
               
                orderItemList.ensureIndexIsVisible(orderItemCnt-1);
                
            }
            else if (ae.getSource() == btnDeleteItem)
            {
                String orderLine = (String)orderItemList.getSelectedValue();
                if(orderLine == null)
                    return;
                    
                int     index = getOrderDetailIndexFromString(orderLine);
                if(index == -1)
                    return;
                if( rcCNDController.deleteOrderItem(currentOrderID, index) == false)
                {
                    displayErrorMessage("deleteOrderItem Error!!\n" + rcCNDController.getErrorMessage());
                }
                refleshOrderDetailList();
            }
             else if (ae.getSource() == btnAll)
            {
                menuList.setListData(rcCNDController.createCNDMenuList(0).toArray());
                menuScrollPanel.getViewport().setView(menuList);
            }
             else if (ae.getSource() == btnMain)
            {
                
                menuList.setListData(rcCNDController.createCNDMenuList(CNDMenu.MAINCOURSE).toArray());
                menuScrollPanel.getViewport().setView(menuList);
            }
             else if (ae.getSource() == btnDrink)
            {
               
                menuList.setListData(rcCNDController.createCNDMenuList(CNDMenu.DRINKS).toArray());
                menuScrollPanel.getViewport().setView(menuList);
            }
             else if (ae.getSource() == btnStarter)
            {
                
                menuList.setListData(rcCNDController.createCNDMenuList(CNDMenu.STARTERS).toArray());
                menuScrollPanel.getViewport().setView(menuList);
            }
             else if (ae.getSource() == btnDessert)
            {
                menuList.setListData(rcCNDController.createCNDMenuList(CNDMenu.DESSERT).toArray());
                menuScrollPanel.getViewport().setView(menuList);
            }
        }
        
        public void valueChanged( ListSelectionEvent e ) {
            if( e.getValueIsAdjusting() == true ){  
                if( e.getSource() == menuList ){
                     tfQuantity.setText("1");
                }
            }
        }
    }
      
    private class IntegerInputVerifier extends InputVerifier{
        private int state = 0;  
        private int MAX = 0;
        private int MIN = 0;
        
        public IntegerInputVerifier()
        {
            super();
        }
        
        public IntegerInputVerifier(int min)
        {
            super();
            MIN = min;
            state = 1;
        }
        
        public IntegerInputVerifier(int min, int max)
        {
            super();
            MIN = min;
            MAX = max;
            state = 2;
        }
        
        @Override public boolean verify(JComponent c)
        {
            JTextField textField = (JTextField)c;
            boolean result = false;
            
            try
            {
                int number = Integer.parseInt(textField.getText());
                
                switch(state)
                {
                    case 0:
                        result = true;
                    case 1:
                        if( number < MIN)
                        {
                           
                            displayErrorMessage("Minimum input is " + MIN);
                            textField.setBackground( Color.red );
                            result = false;
                        }
                        else
                        {
                            textField.setBackground( UIManager.getColor( "TextField.background" ) );  
                            result = true;
                        }
                    break;
                    case 2:
                        if( number < MIN)
                        {
                            displayErrorMessage("Minimum input is " + MIN);
                            textField.setBackground( Color.red );
                            result = false;
                        }
                        else
                        {
                            if(number > MAX)
                            {
                                displayErrorMessage("Maximum input is " + MAX);
                                textField.setBackground( Color.red );
                                result = false;
                            }
                            else
                            {
                                textField.setBackground( UIManager.getColor( "TextField.background" ) );  
                                result = true;
                            }
                        }
                    break;
                }
            }catch(NumberFormatException e) {
                  displayErrorMessage("Only number is allowed.");
                  textField.setBackground( Color.red );
                result = false;
            }
            return result;
        }
    }

		
}

    