import java.io.PrintWriter;

public class TableGeneratorThread extends Thread {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-";
    private long attempts = 0;
    private PrintWriter writer;

    public TableGeneratorThread(PrintWriter writer) {
        super();
        this.writer = writer;
    }

    private void saveToFile(String plaintext, String hash) {
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

