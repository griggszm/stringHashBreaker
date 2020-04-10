import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TableGeneratorThread extends Thread {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-";
    private long attempts = 0;
    private String basePath;
    private List<PrintWriter> printWriters;
    private static final int DIVISIONS = 20;

    public TableGeneratorThread(String basePath) {
        super();
        this.basePath = basePath;
        this.printWriters = new ArrayList<>();
        try {
            for (int i = 0; i < DIVISIONS + 1; i++) {
                printWriters.add(new PrintWriter(basePath + i + ".txt"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot create files");
        }
    }

    private PrintWriter getWriterFor(String hash) {
        long converted = Long.parseLong(hash);
        long steps = Integer.MAX_VALUE / (DIVISIONS / 2);
        int whichWriter = 0;
        if(converted > 0) {
            whichWriter += (DIVISIONS / 2);
        } else {
            converted = -converted;
        }
        while(converted > 0) {
            converted -= steps;
            whichWriter++;
        }
        return printWriters.get(whichWriter);
    }

    private void saveToFile(String plaintext, String hash) {
        PrintWriter writer = getWriterFor(hash);
        writer.println(hash + "|" + plaintext);
    }

    void printAllKLength(char[] set, int k) {
        int n = set.length;
        printAllKLengthRec(set, "", n, k);
    }

    void printAllKLengthRec(char[] set, String prefix, int n, int k) {
        if (k == 0) {
            String hash = SStrHash2.hash(prefix);
            saveToFile(prefix, hash);
            attempts++;
            return;
        }
        for (int i = 0; i < n; ++i) {
            String newPrefix = prefix + set[i];
            printAllKLengthRec(set, newPrefix, n, k - 1);

        }
    }

    private void breakHash() {
        while (true) {
            for (int i = 1; i < 12; i++) {
                printAllKLength(ALPHABET.toCharArray(), i);
            }
        }
    }

    @Override
    public void run() {
        breakHash();
    }

    public long getAttempts() {
        return attempts;
    }

    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }
}

