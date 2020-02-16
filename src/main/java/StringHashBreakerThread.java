import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringHashBreakerThread extends Thread {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private long attempts = 0;
    private boolean broken = false;

    private String hash;
    private String plaintext;

    public StringHashBreakerThread(String hash) {
        super();
        this.hash = hash;
        this.plaintext = "(unknown)";
    }

    void printAllKLength(char[] set, int k) {
        if(!broken) {
            int n = set.length;
            printAllKLengthRec(set, "", n, k);
        }
    }

    void printAllKLengthRec(char[] set,  String prefix, int n, int k) {
        if (k == 0) {
            StringHash hash = new StringHash(prefix);
            attempts++;
            if(hash.getHash().equals(this.hash)) {
                this.plaintext = prefix;
                System.out.println("Found! " + hash.getPlainText());
                this.broken = true;
            }
            return;
        }
        for (int i = 0; i < n; ++i) {
            String newPrefix = prefix + set[i];
            if(!broken) {
                printAllKLengthRec(set, newPrefix, n, k - 1);
            }
        }
    }

    private void breakHash() {
        while(!broken) {
            for(int i = 1; i < 12; i++) {
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

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
}

