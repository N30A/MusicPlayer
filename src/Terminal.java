import java.util.Scanner;

public class Terminal {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String input(String prompt) {
        System.out.print(prompt + " > ");
        return SCANNER.nextLine();
    }
}
