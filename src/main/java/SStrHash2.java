public class SStrHash2 {

    private static final long[] p = {0, 8, 16, 24, 0, 8, 16, 24, 8, 16, 24};
    private static final long[] r = {-13, 8, -13, -12, 16, -5, -3, 10, -15};

    private static long normalize(long input) {
        return input & 0b011111111111111111111111111111111L;
    }

    private static long calculateFinal(long e) {
        return e - ((1L << 32L) * (e >> 31L));
    }

    /**
     * Converts the given String into a Hash.
     *
     * @return  Hash of Plaintext String
     */
    public static String SStrHash2(String plainText) {
        int len = plainText.length();
        byte[] charIndexes = new byte[len];
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
        //byte[] charIndexes2 = plainText.toUpperCase().replace((char)47, (char)92).getBytes();

        int index = 0;
        long a = 0;
        long b = 0x9e3779b9L;
        long c = 0x9e3779b9L;
        long tempA;
        long tempB;
        long tempC;


        while(len >= 12) {
            byte[] eightTo12 = new byte[4];
            eightTo12[0] = charIndexes[index+8];
            eightTo12[1] = charIndexes[index+9];
            eightTo12[2] = charIndexes[index+10];
            eightTo12[3] = charIndexes[index+11];

            byte[] fourToEight = new byte[4];
            fourToEight[0] = charIndexes[index+4];
            fourToEight[1] = charIndexes[index+5];
            fourToEight[2] = charIndexes[index+6];
            fourToEight[3] = charIndexes[index+7];

            byte[] zeroToFour = new byte[4];
            zeroToFour[0] = charIndexes[index];
            zeroToFour[1] = charIndexes[index+1];
            zeroToFour[2] = charIndexes[index+2];
            zeroToFour[3] = charIndexes[index+3];

            long eightTo12Byte = java.nio.ByteBuffer.wrap(eightTo12).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
            long fourToEightByte = java.nio.ByteBuffer.wrap(fourToEight).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
            long zeroToFourByte = java.nio.ByteBuffer.wrap(zeroToFour).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();

            a += eightTo12Byte;
            b += fourToEightByte;
            c += zeroToFourByte;

            for(int j = 0; j < r.length; j++) {
                long i = r[j];
                tempA = a;
                tempB = b;
                tempC = c;
                c = normalize(tempB);
                b = tempA;
                if(i > 0) {
                    a = (tempC - tempB - tempA) ^ (tempA << i);
                } else {
                    a = (tempC - tempB - tempA) ^ (normalize(tempA) >> -i);
                }
            }

            index += 12;
            len -= 12;
        }
        long[] d = {c, b, a + plainText.length()};
        for(int j = 0; j < charIndexes.length - index; j++) {
            int n = (j/4);
            int n2 = charIndexes[index+j];
            long n3 = p[j];
            d[n] += n2 << n3;
        }
        c = d[0];
        b = d[1];
        a = d[2];
        for(int j = 0; j < r.length; j++) {
            long i = r[j];
            tempA = a;
            tempB = b;
            tempC = c;
            c = normalize(tempB);
            b = tempA;
            if(i > 0) {
                a = (tempC - tempB - tempA) ^ (tempA << i);
            } else {
                a = (tempC - tempB - tempA) ^ (normalize(tempA) >> -i);
            }
        }
        return "" + calculateFinal(normalize(a));
    }

}
