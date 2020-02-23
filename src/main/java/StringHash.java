import java.util.Arrays;

public class StringHash {

    private String plainText;
    private String hash;

    /**
     * Creates the StringHash pair of (plaintext, hash)
     * Where hash is generated through the system
     *
     * @param plainText Plaintext string to hash
     */
    public StringHash(String plainText) {
        this.plainText = plainText;
        this.hash = SStrHash2();
    }

    private long normalize(long input) {
        while(input < 0) {
            input += 4294967296L;
        }
        while (input > 4294967296L) {
            input -= 4294967296L;
        }
        return input;
    }

    private long calculateFinal(long e) {
        return e - ((1L << 32L) * (e >> 31L));
    }

    /**
     * Converts the given String into a Hash.
     *
     * @return  Hash of Plaintext String
     */
    private String SStrHash2() {
        if(plainText.isEmpty()) {
            return "0";
        }
        byte[] charIndexes = new byte[plainText.length()];
        for(int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if(97 <= c && c <= 122) {
                charIndexes[i] = (byte)(c - 32);
            } else if(c == 47) {
                charIndexes[i] = (byte)92;
            } else {
                charIndexes[i] = (byte)c;
            }
        }
        long len = plainText.length();
        int index = 0;
        long a = 0;
        long b = 0x9e3779b9L;
        long c = 0x9e3779b9L;
        long[] p = {0, 8, 16, 24, 0, 8, 16, 24, 8, 16, 24};
        long[] r = {-13, 8, -13, -12, 16, -5, -3, 10, -15};

        while(len >= 12) {
            byte[] eightTo12 = Arrays.copyOfRange(charIndexes, index+8, index+12);
            byte[] fourToEight = Arrays.copyOfRange(charIndexes, index+4, index+8);
            byte[] zeroToFour = Arrays.copyOfRange(charIndexes, index, index+4);

            long eightTo12Byte = java.nio.ByteBuffer.wrap(eightTo12).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
            long fourToEightByte = java.nio.ByteBuffer.wrap(fourToEight).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
            long zeroToFourByte = java.nio.ByteBuffer.wrap(zeroToFour).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();

            a += eightTo12Byte;
            b += fourToEightByte;
            c += zeroToFourByte;

            for(int j = 0; j < r.length; j++) {
                long i = r[j];
                long tempA = a;
                long tempB = b;
                long tempC = c;
                c = normalize(tempB);
                b = tempA;
                if(i > 0) {
                    a = (tempC - tempB - tempA) ^ (tempA << i);
                } else {
                    long modifiedA = normalize(tempA);
                    long collected1 = (tempC - tempB - tempA);
                    long collected2 = (modifiedA) >> -i;
                    long collected3 = collected1 ^ collected2;
                    a = (tempC - tempB - tempA) ^ ((modifiedA) >> -i);
                }
            }

            index += 12;
            len -= 12;
        }
        long[] d = {c, b, a + plainText.length()};
        for(int j = 0; j < charIndexes.length - index; j++) {
            int n = (int)(j/4);
            int n2 = charIndexes[index+j];
            long n3 = p[j];
            d[n] += n2 << n3;
        }
        c = d[0];
        b = d[1];
        a = d[2];
        for(int j = 0; j < r.length; j++) {
            long i = r[j];
            long tempA = a;
            long tempB = b;
            long tempC = c;
            c = normalize(tempB);
            b = tempA;
            if(i > 0) {
                a = (tempC - tempB - tempA) ^ (tempA << i);
            } else {
                long modifiedA = normalize(tempA);
                long collected1 = (tempC - tempB - tempA);
                long collected2 = (modifiedA) >> -i;
                long collected3 = collected1 ^ collected2;
                a = (tempC - tempB - tempA) ^ ((modifiedA) >> -i);
            }
        }
        return "" + calculateFinal(normalize(a));
    }

    /**
     *
     *     while len(s) >= 12:
     *         a += int.from_bytes(s[8:12], "little")
     *         b += int.from_bytes(s[4:8], "little")
     *         c += int.from_bytes(s[:4], "little")
     *         for i in r:
     *             a, b, c = (c - b - a) ^ (a << i if i > 0 else (a & 0xFFFFFFFF) >> -i), a, b & 0xFFFFFFFF
     *         s = s[12:]
     *
     *     d = [c, b, a + l]
     *     for i in range(len(s)):
     *         d[i // 4] += s[i] << p[i]
     *     c, b, a = d
     *     for i in r:
     *         a, b, c = (c - b - a) ^ (a << i if i > 0 else (a & 0xFFFFFFFF) >> -i), a, b & 0xFFFFFFFF
     *
     *     return (lambda e: e - (1 << 32) * (e >> 31))(a & 0xFFFFFFFF)
     * @return
     */

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Plaintext: " + getPlainText() + " hashes to: " + getHash();
    }

}
