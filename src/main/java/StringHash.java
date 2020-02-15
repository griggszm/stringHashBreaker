public class StringHash {

    private String plainText;
    private String hash;
    private long a;
    private long b;
    private long c;

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

    /**
     * Gets the hash of a byte array of upper case characters
     *
     * @param bytes Byte array of uppercase chars
     * @param length Length of byte array
     * @param initializer Initialize value; for this impl, always zero
     * @return String hash
     */
    private String hash(byte[] bytes, int length, int initializer) {
        a = b = (0x9e3779b9L);  /* the golden ratio; an arbitrary value */
        c = initializer;
        int k = 0;

        while(length >= 12) {
            a += (bytes[k + 0] +(bytes[k + 1]<<8) +(bytes[k + 2]<<16) +(bytes[k + 3]<<24));
            b += (bytes[k + 4] +(bytes[k + 5]<<8) +(bytes[k + 6]<<16) +(bytes[k + 7]<<24));
            c += (bytes[k + 8] +(bytes[k + 9]<<8) +(bytes[k + 10]<<16)+(bytes[k + 11]<<24));
            mix();
            k += 12;
            length -= 12;
        }

        c += length;
        switch (length) {
            case 11:
                c+=(bytes[k + 10]<<24L);
            case 10:
                c+=(bytes[k + 9]<<16L);
            case 9 :
                c+=(bytes[k + 8]<<8L);
                /* the first byte of c is reserved for the length */
            case 8 :
                b+=(bytes[k + 7]<<24L);
            case 7 :
                b+=(bytes[k + 6]<<16L);
            case 6 :
                b+=(bytes[k + 5]<<8L);
            case 5 :
                b+=bytes[k + 4];
            case 4 :
                a+=(bytes[k + 3]<<24L);
            case 3 :
                a+=(bytes[k + 2]<<16L);
            case 2 :
                a+=(bytes[k + 1]<<8L);
            case 1 :
                a+=bytes[k];
                /* case 0: nothing left to add */
        }
        mix();
        return c+"";
    }

    /**
     * Converts the given String into a Hash.
     *
     * @return  Hash of Plaintext String
     */
    private String SStrHash2() {
        byte[] bytes = new byte[0x400];
        int length = 0;
        for(char c : plainText.toCharArray()) {
            if(c < 'a' || c > 'z') {
                if(c == '/') {
                    bytes[length] = '\\';
                } else {
                    bytes[length] = (byte)c;
                }
            } else {
                bytes[length] = (byte)((c) - 0x20);
            }
            length++;
        }
        return hash(bytes, length, 0);
    }

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

    /**
     * Treat the input like a C++ Unsigned Integer.
     *
     * @param x Long X
     * @return  Long C as Unsigned Int X
     */
    private long normalize(long x) {
        while(x > (long)((long)Integer.MAX_VALUE + (long)Integer.MAX_VALUE) + (long)2) {
            x -= Integer.MAX_VALUE;
            x--;
            x -= Integer.MAX_VALUE;
            x--;
        }
        while(x < 0) {
            x += Integer.MAX_VALUE;
            x++;
            x += Integer.MAX_VALUE;
            x++;
        }
        return x;
    }
    /**
     *  Mix up the pieces of the Hash and do math on them
     */
    private void mix() {
        a -= b;
        a = normalize(a);
        a -= c;
        a = normalize(a);
        long x = (c>>13L);
        x = normalize(x);
        a ^= x;
        a = normalize(a);
        b -= c;
        b = normalize(b);
        b -= a;
        b = normalize(b);
        x = a << 8;
        x = normalize(x);
        b ^= x;
        b = normalize(b);
        c -= a;
        c = normalize(c);
        c -= b;
        c = normalize(c);
        x = b >> 13;
        x = normalize(x);
        c ^= x;
        c = normalize(c);
        a -= b;
        a = normalize(a);
        a -= c;
        a = normalize(a);
        x = c >> 12;
        x = normalize(x);
        a ^= x;
        a = normalize(a);
        b -= c;
        b = normalize(b);
        b -= a;
        b = normalize(b);
        x = a << 16;
        x = normalize(x);
        b ^= x;
        b = normalize(b);
        c -= a;
        c = normalize(c);
        c -= b;
        b = normalize(b);
        x = b >> 5;
        x = normalize(x);
        c ^= x;
        c = normalize(c);
        a -= b;
        a = normalize(a);
        a -= c;
        a = normalize(a);
        x = c >> 3;
        x = normalize(x);
        a ^= x;
        a = normalize(a);
        b -= c;
        b = normalize(b);
        b -= a;
        b = normalize(b);
        x = a << 10;
        x = normalize(x);
        b ^= x;
        b = normalize(b);
        c -= a;
        c = normalize(c);
        c -= b;
        c = normalize(c);
        x = b >> 15;
        x = normalize(x);
        c ^= x;
        c = normalize(c);
    }
}
