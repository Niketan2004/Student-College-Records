public class LoadingAnimation {
     // Define the characters for the loading animation
     static char[] animationChars = new char[] { '|', '/', '-', '\\' };

     public static void animation() {

          // Simulate loading by printing animation characters

          try {
               System.out.println("Loading...");
               Thread.sleep(100);
          } catch (InterruptedException e) {

               e.printStackTrace();
          }
          for (int i = 0; i < 25; i++) {
               try {
                    Thread.sleep(100); // Simulate a delay
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               System.out.print("\r" + "Processing " + animationChars[i % 4] + " "); // Use carriage return to overwrite
                                                                                     // the previous line
          }
          System.out.println();
     }

     public static void exitAnimation() {
          // Simulate loading by printing animation characters
          System.out.println("Exiting...");
          for (int i = 0; i < 25; i++) {
               try {
                    Thread.sleep(100); // Simulate a delay
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               System.out.print("\r" + "Saving data... " + animationChars[i % 4] + " "); // Use carriage return to
                                                                                         // overwrite
                                                                                         // the previous line
          }
          System.out.println();
          System.out.println("\nExited Successfully!");
     }
}