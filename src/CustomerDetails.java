import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetails {
    private String name;
    private String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    private int busId;


    public static List<CustomerDetails> getConstomerDetails(Connection connection,int busId) throws SQLException {
        List<CustomerDetails> result = new ArrayList<>();
        PreparedStatement pre = connection.prepareStatement("select* from customertable where busid = ?");
        pre.setInt(1,busId);
        ResultSet resultSet = pre.executeQuery();
        while(resultSet.next())
        {
            CustomerDetails temp = new CustomerDetails();
            temp.setName(resultSet.getString("name"));
            temp.setAddress(resultSet.getString("address"));
            result.add(temp);
        }
        return result;
    }
    public void saveCustomerDetails(Connection con) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO customertable (name, address, busid) VALUES (?, ?, ?)");
        preparedStatement.setString(1, this.getName());
        preparedStatement.setString(2, this.getAddress());
        preparedStatement.setInt(3,this.busId);
        preparedStatement.executeUpdate();

    }


}
