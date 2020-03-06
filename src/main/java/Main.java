import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter an option");
        System.out.println("0: Compute hash");
        System.out.println("1: Break hash");

        String value = in.nextLine();
        if(value.equals("0")) {
            computeHash(in);
        } else if(value.equals("1")) {
            breakHash(in);
        } else {
            System.out.println("Invalid option: " + value);
        }
    }

    private static void computeHash(Scanner in) {
        System.out.println("Enter string to compute SStrHash2 for (or press enter to exit)");
        String plaintext = in.nextLine();
        if(!plaintext.isEmpty()) {
            System.out.println(SStrHash2.SStrHash2(plaintext));
            computeHash(in);
        }
    }

    private static void breakHash(Scanner in) {
        System.out.println("Enter string to break hash of");
        String hash = in.nextLine();
        StringHashBreakerThread thread = new StringHashBreakerThread(hash);
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Attempts: " + thread.getAttempts());
            }
        }, 0, 5, TimeUnit.SECONDS);
        thread.run();
        System.out.println(thread.getPlaintext());
    }
}
