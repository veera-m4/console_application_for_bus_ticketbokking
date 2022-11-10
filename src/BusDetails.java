import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BusDetails {
    private String busName;
    private String busnumber;
    private String DriverName;
    private int capacities;
    private final Connection con;
    private int availability;

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public BusPath getBusPath() {
        return busPath;
    }

    public void setBusPath(BusPath busPath) {
        this.busPath = busPath;
    }

    private  BusPath busPath;
    BusDetails(Connection con)
    {
        this.con= con;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public int getCapacities() {
        return capacities;
    }

    public void setCapacities(int capacities) {
        this.capacities = capacities;
    }

    public void populateBusDetails(int id) throws SQLException {
        PreparedStatement stmt=con.prepareStatement("select * from bustable where id = ?");
        stmt.setInt(1,id);
        ResultSet rs=stmt.executeQuery();
        rs.next();
        this.busName = rs.getString("busname");
        this.busnumber = rs.getString("busnumber");
        this.DriverName = rs.getString("busdriver");
        this.busPath = new BusPath();
        busPath.setStartPoint(rs.getString("startplace"));
        busPath.setStartPoint(rs.getString("endplace"));
        this.availability = rs.getInt("available");
    }
    public void saveDetails() throws SQLException {
        PreparedStatement stmt=con.prepareStatement("INSERT INTO bustable (busname, busnumber, busdriver, startplace, endplace, capacity, available) VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1,this.busName);
        stmt.setString(2,this.busnumber);
        stmt.setString(3,this.DriverName);
        stmt.setString(4,this.busPath.getStartPoint());
        stmt.setString(5,this.busPath.getEndPoint());
        stmt.setInt(6,this.capacities);
        stmt.setInt(7,this.capacities);
        stmt.executeUpdate();
    }
    public static  void updateDetails(int availability,int busId,Connection con) throws SQLException {
        PreparedStatement stmt=con.prepareStatement("UPDATE bustable SET available = ? WHERE (id = ?);");
        stmt.setInt(1,availability);
        stmt.setInt(2,busId);
        stmt.executeUpdate();
    }
    public  static int availabilityCount(Connection con,int id) throws SQLException {
        PreparedStatement stmt=con.prepareStatement("select * from bustable where id= ?");
        stmt.setInt(1,id);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return  resultSet.getInt("available");
    }
    public static void cancelTicket(Connection con,String name,String address, int busid) throws SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println(busid);
        PreparedStatement stmt = con.prepareStatement("select * from customertable where name = ? and address =? and busid = ? ");
        stmt.setString(1,name);
        stmt.setString(2, address);
        stmt.setInt(3,busid);
        ResultSet result = stmt.executeQuery();

        PreparedStatement stmt1 = con.prepareStatement("select count(*) as count from customertable where name = ? and address =? and busid = ? ");
        stmt1.setString(1,name);
        stmt1.setString(2, address);
        stmt1.setInt(3,busid);
        ResultSet result2 = stmt1.executeQuery();
        result2.next();
        int no_tickets = result2.getInt("count");
        System.out.println("You booked "+no_tickets);
        while(true)
        {
            System.out.println("Enter the number tickets to cancel ");
            int count = sc.nextInt();
            if(count > no_tickets) {
                System.out.println("You booked only " + no_tickets);
                System.out.println("If you want to return to main menu enter 1");
                int n = sc.nextInt();
                if (n == 1) {
                    return;
                }
            }
            else {
                int i=0;
                while(result.next() && i<count)
                {
                    PreparedStatement pre = con.prepareStatement("DELETE FROM customertable WHERE (id = ?)");
                    pre.setInt(1,result.getInt("id"));
                    int row = pre.executeUpdate();
                    System.out.println(row+"  tickets cancelled");
                    System.out.println("Returning to main menu");
                    System.out.println();
                    System.out.println();
                    Thread.sleep(2000);
                    return;
                }
            }
        }

    }
}
