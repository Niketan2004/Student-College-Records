import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class course {
     @SuppressWarnings("static-access")
     public void Courses(Scanner sc, Connection con) {
          int choice;
          while (true) {
               System.out.println("----------------------------- WELCOME TO COURSE TAB --------------------");
               System.out.println("1. Add Courses \n 2.Remove Courses \n 3.View Courses \n 4.Exit");
               System.out.println("Enter Your Choice :- ");
               choice = sc.nextInt();
               sc.nextLine();
               if (choice == 4) {
                    System.out.println("Returning to HomePage!....");
                    Thread t = new Thread();
                    try {
                         t.sleep(10);
                    } catch (InterruptedException e) {

                         e.printStackTrace();
                    }
                    LoadingAnimation.exitAnimation();
                    break;
               }
               LoadingAnimation.animation();
               switch (choice) {
                    case 1 -> {
                         System.out.println("Enter Course Name :- ");
                         String course = sc.nextLine();
                         System.out.println("Enter the fees :-");
                         int fees = sc.nextInt();
                         LoadingAnimation.animation();
                         addCourse(course, con, fees);
                    }
                    case 2 -> {
                         System.out.println("Enter Course id :- ");
                         int course_id = sc.nextInt();

                         removeCourse(course_id, con);
                    }
                    case 3 -> {
                         System.out.println("--------------------------Displaying Courses---------------------------");
                         displayCourses(con);
                    }
                    default -> System.out.println("Correct Value...!");
               }
          }
     }

     private void addCourse(String course, Connection con, int fees) {
          try (PreparedStatement ps = con.prepareStatement(Query.addCourse)) {
               ps.setString(1, course);
               ps.setInt(2, fees);
               int rowsAffected = ps.executeUpdate();
               if (rowsAffected > 0) {
                    System.out.println("Course Inserted Successfully !");
               } else {
                    System.out.println("Error Occured! Failed to insert Course!");
               }
          } catch (Exception e) {
               System.out.println("Failed to insert Course!");
               e.printStackTrace();
          }
     }

     private void removeCourse(int course_id, Connection con) {
          try (PreparedStatement ps = con.prepareStatement(Query.removeCourse)) {
               ps.setInt(1, course_id);
               int rowsAffected = ps.executeUpdate();
               LoadingAnimation.animation();
               if (rowsAffected > 0) {
                    System.out.println("Course Deleted Successfully !");
               } else {
                    System.out.println("Problem Occures! Failed to  delete course!");
               }

          } catch (Exception e) {
               e.getMessage();
          }
     }

     private void displayCourses(Connection con) {
          try {
               PreparedStatement ps = con.prepareStatement(Query.displayCourses);
               ResultSet rs = ps.executeQuery();
              // LoadingAnimation.animation();
               System.out.println("+-----------+----------------------------+----------+----------------+");
               System.out.println("| course_id | course_name                | fees     | total_enrolled |");
               System.out.println("+-----------+----------------------------+----------+----------------+");

               while (rs.next()) {
                    int courseId = rs.getInt("course_id");
                    String courseName = rs.getString("course_name");
                    double fees = rs.getDouble("fees");
                    int totalEnrolled = rs.getInt("total_enrolled");

                    System.out.printf("| %9d | %-26s | %8.2f | %14d |\n", courseId, courseName, fees, totalEnrolled);
               }

               System.out.println("+-----------+----------------------------+----------+----------------+");

          } catch (Exception e) {
               System.out.println(e.getMessage());
          }
     }
}
