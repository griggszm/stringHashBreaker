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

    private String randomString() {
        Random r = new Random();
        int numLetters = r.nextInt(15) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numLetters; i++) {
            builder.append(ALPHABET.charAt(r.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }

    private String breakHash() {
        String random = "";
        while (!broken) {
            random = randomString();
            StringHash hash = new StringHash(random);
            attempts++;
            if (hash.getHash().equals(this.hash)) {
                broken = true;
            }
        }
        return random;
    }

    @Override
    public void run() {
        this.plaintext = breakHash();
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

