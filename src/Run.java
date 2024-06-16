import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) throws Exception {
        course c = new course();
        Enrollment en = new Enrollment();
        Display d = new Display();

        Scanner sc = new Scanner(System.in);
        Connection con = null;
        try {

            Properties prop = new Properties();
            InputStream inStream = new FileInputStream(new File("src/db.properties"));
            prop.load(inStream);
            String db_url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            con = DriverManager.getConnection(db_url, username, password);

        } catch (Exception e) {
            e.getMessage();

        }
        try {
            while (true) {
                System.out.println(
                        "------------------ WELCOME TO RANDOM COLLEGE OF TECHNOLOGY ! -----------------------");

                System.out.println("1.Courses \n 2.Enrollment \n 3.Display \n 4.Exit");
                System.out.println("Choose your Option :- ");
                int choice = sc.nextInt();
                if (choice == 4) {

                    LoadingAnimation.exitAnimation();
                    break;
                }
                LoadingAnimation.animation();
                switch (choice) {
                    case 1 -> c.Courses(sc, con);
                    case 2 -> en.Enroll(sc, con);
                    case 3 -> d.Show(sc, con);
                    default -> System.out.println("Enter Valid Number !");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
