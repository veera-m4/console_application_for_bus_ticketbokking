import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    private BusPath buspath;
    private Connection con;
    BusRoute(Connection con)
    {
        this.con = con;
    }

    public BusPath getBuspath() {
        return buspath;
    }

    public void setBuspath(BusPath buspath) {
        this.buspath = buspath;
    }
    public static List<Integer> getBusIdByRoute(BusPath buspath, Connection con) throws SQLException {
        Statement statement = con.createStatement();
        List<Integer> result = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement("select * from bustable where startplace = ? and endplace = ? ");
        stmt.setString(1, buspath.getStartPoint());
        stmt.setString(2, buspath.getEndPoint());
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getInt("id"));
        }
        return result;
    }
}
