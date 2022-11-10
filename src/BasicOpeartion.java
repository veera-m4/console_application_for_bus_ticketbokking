import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BasicOpeartion extends BusRoute{
    private Connection con;
    BusDetails busDetails;
    CustomerDetails customerDetails;
    BusRoute busRoute;
    Scanner sc = new Scanner(System.in);
    BasicOpeartion(Connection con)
    {
        super(con);
        this.con= con;
    }
    public void addBus() throws SQLException {
        busDetails = new BusDetails(con);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the bus name");
        busDetails.setBusName(sc.nextLine().toLowerCase());
        System.out.println("Enter the bus number");
        busDetails.setBusnumber(sc.nextLine().toLowerCase());
        System.out.println("Enter the driver name");
        busDetails.setDriverName(sc.nextLine().toLowerCase());
        System.out.println("Enter the seat capacities");
        busDetails.setCapacities(sc.nextInt());
        sc.nextLine();
        System.out.println("Enter the Bus starting point");
        BusPath temp = new BusPath();
        temp.setStartPoint(sc.nextLine().toLowerCase());
        System.out.println("Enter the Bus ending point");
        temp.setEndPoint(sc.nextLine().toLowerCase());
        busDetails.setBusPath(temp);
        busDetails.saveDetails();
    }
    public void getBusesByRoute() throws SQLException {
        BusPath busPath = new BusPath();
        System.out.println("Enter the starting point");
        busPath.setStartPoint(sc.nextLine().toLowerCase());
        System.out.println("Enter the Ending point");
        busPath.setEndPoint(sc.nextLine().toLowerCase());
        List<Integer> busList = getBusIdByRoute(busPath,con);

        if(busList.isEmpty())
        {
            System.out.println("No bus found for the root");
        }
        else
        {
            System.out.println("Available buses are");
            for(int i : busList)
            {
                BusDetails busDetails1 = new BusDetails(con);
                busDetails1.populateBusDetails(i);
                System.out.print(busDetails1.getBusName()+"   ");
                System.out.println(busDetails1.getAvailability());
                System.out.println();
                System.out.println();
            }
        }

    }
    public void getPassengerListForBus() throws SQLException {
        System.out.println("Enter the bus name");
        try {
            String busName = sc.nextLine().toLowerCase();
            int busId = getBusId(busName, con);
            List<CustomerDetails> customerDetails1 = CustomerDetails.getConstomerDetails(con, busId);
            for (CustomerDetails customerDetails2 : customerDetails1) {
                System.out.println("name  :  "+customerDetails2.getName());
                System.out.println("Address   : "+customerDetails2.getAddress());
                System.out.println();
                System.out.println();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    public static int getBusId(String busName,Connection con) throws SQLException {
        PreparedStatement pre = con.prepareStatement("select* from bustable where busname = ?");
        pre.setString(1,busName);
        ResultSet result =pre.executeQuery();
        result.next();
        return result.getInt("id");
    }

    public void BookTicket() throws SQLException {
        getBusesByRoute();
        CustomerDetails customerDetails1 = new CustomerDetails();
        System.out.println("Enter your name");
        String name = sc.nextLine().toLowerCase();
        System.out.println("Enter your address");
        String Address = sc.nextLine().toLowerCase();
        System.out.println("Enter the bus  name");
        int busId = getBusId(sc.nextLine().toLowerCase(),con);
        System.out.println("Enter the number of tickets");
        int number_of_tickets = sc.nextInt();
        customerDetails1.setName(name);
        customerDetails1.setAddress(Address);
        customerDetails1.setBusId(busId);
        for(int i=0;i<number_of_tickets;i++)
        {
            customerDetails1.saveCustomerDetails(con);
        }
        BusDetails.updateDetails(BusDetails.availabilityCount(con,busId)-number_of_tickets,busId,con);

    }
    public void cancelTicket() throws SQLException, InterruptedException {
        System.out.println("Enter your name");
        String name = sc.nextLine().toLowerCase();
        System.out.println("Enter your address");
        String address = sc.nextLine().toLowerCase();
        System.out.println("Enter the bus name ");
        String busName = sc.nextLine().toLowerCase();
        int busId =  getBusId(busName,con);
        BusDetails.cancelTicket(con,name, address,busId);

    }

}
