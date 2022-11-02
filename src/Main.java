import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path ="finite-state-machine.txt";

        if (args.length > 0) {
            path = args[0];
        } else {
            System.out.println("\n\nNo path provided, using default path " + path);
        }

        FiniteStateMachine finiteStateMachine = FileReader.Read(path);

        if (finiteStateMachine == null) {
            System.out.println("\nFailed to read file");

            return;
        }

        finiteStateMachine.determineReachableStates();

        System.out.println("\nPlease enter w0");

        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();

        if (finiteStateMachine.isValid(word)) {
            System.out.println("\n\nw1w0 combination is valid\n\n");
        } else {
            System.out.println("\n\nw1w0 combination is invalid\n\n");
        }
    }
}
