import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
        System.out.println("2: Generate table");

        String value = in.nextLine();
        if(value.equals("0")) {
            computeHash(in);
        } else if(value.equals("1")) {
            breakHash(in);
        } else if(value.equals("2")) {
            generateTable(in);
        } else {
            System.out.println("Invalid option: " + value);
        }
    }

    private static void computeHash(Scanner in) {
        System.out.println("Enter string to compute hash for (or press enter to exit)");
        String plaintext = in.nextLine();
        if(!plaintext.isEmpty()) {
            System.out.println(SStrHash2.hash(plaintext));
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
        long time = System.currentTimeMillis();
        thread.run();
        System.out.println("Attempts: " + thread.getAttempts());
        System.out.println("Plaintext collision: " + thread.getPlaintext());
        System.out.println("Time taken: " + (System.currentTimeMillis() - time) + " ms");
        exec.shutdown();
    }

    private static void generateTable(Scanner in) {
            System.out.print("Enter output file name: ");
            String path = in.nextLine();

            TableGeneratorThread thread = new TableGeneratorThread(path);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Entries: " + thread.getAttempts());
                }
            }, 0, 5, TimeUnit.SECONDS);
            long time = System.currentTimeMillis();
            thread.run();

    }
}
