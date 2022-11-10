import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String []args) {
        String var1 = "jdbc:mysql://localhost:3306/jdbc";
        String var2 = "root";
        String var3 = "Manikandan@4";
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con;
            con = DriverManager.getConnection(var1, var2, var3);
            BasicOpeartion basicOpeartion = new BasicOpeartion(con);
            while(true) {
                System.out.println("1.Add bus");
                System.out.println("2.Check the Availability");
                System.out.println("3.bookTicket");
                System.out.println("4.Get the list of passenger");
                System.out.println("5.Cancel the tickets");
                System.out.println("6.Exit");
                System.out.println("Please enter your choice");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        basicOpeartion.addBus();
                    }
                    case 2 -> {
                        basicOpeartion.getBusesByRoute();
                    }
                    case 3 -> {
                        basicOpeartion.BookTicket();
                    }
                    case 4 -> {
                        basicOpeartion.getPassengerListForBus();
                    }
                    case 5 ->{
                        basicOpeartion.cancelTicket();
                    }
                    case 6 -> {
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("Enter the valid choice");
                    }
                }
            }

        } catch (Exception var5) {
            System.out.println(var5);
        }

    }
}
