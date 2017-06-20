import java.util.Scanner;
import java.io.*;
/*
*
* This class is a wrapper on the InputStream and PrintStream
* meant to prevent output and controlled behavior during testing.
*
*/
public class IOControl {
    private final Scanner scanner;
    private final PrintStream out;

    public IOControl(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }
    public void print(String output){
      out.println(output);
    }

    public int getInt() {
        return scanner.nextInt();
    }
}
