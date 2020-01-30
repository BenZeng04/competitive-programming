import java.io.*;

public class Main extends Template
{
    public static void main(String[] args) 
    {
        int n = readInt(), k = readInt();
        int[][] BIT = new int[n + 1][n + 1], base = new int[n + 1][n + 1];
        for(int i = 1; i <= n; i++)
            for(int j = 0; j < i; j++)
                base[i][n - i + j + 1] = readInt();
        long res = 0;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j <= i; j++)
            {
                int elem = base[n - j][n + j - i];
                for(int a = n - j; a <= n; a += a & -a)
                    for(int b = n + j - i; b <= n; b += b & -b)
                        BIT[a][b] = max(elem, BIT[a][b]);
            }

            for(int j = 0; j < i - k + 2; j++)
            {
                int subtr = 0;
                for(int a = n - j; a > 0; a -= a & -a)
                    for(int b = n - i + k - 1 + j; b > 0; b -= b & -b)
                        subtr = max(subtr, BIT[a][b]);
                res += subtr;
            }
        }
        println(res);
    }
}
class Template 
{ 
    /* General functions */
    private static byte[] buf = new byte[1024];
    private static int curChar, numChars;
    public static int MOD = (int) 1e9 + 7;
    public static <T>void print(T s){ System.out.print(s);}
    public static <T>void prints(T... s){ for(T t: s) {System.out.print(t); System.out.print(' ');}}
    public static <T>void println(T s){ System.out.println(s);}
    public static void println(){ System.out.println();}
    public static int readChar(){try{if (curChar >= numChars){curChar = 0;numChars = System.in.read(buf);}if (numChars == -1)return numChars;return buf[curChar++];}catch(IOException e){throw new Error("Failed to read from IO.");}}
    public static int readInt() { int c = readChar(), sgn = 1;while (space(c))  c = readChar();if (c == '-')  {sgn = -1;c = readChar();}int res = 0;do {res = (res << 1) + (res << 3);res += c - '0';c = readChar();}while (!space(c));return res * sgn;}
    public static int[] readArray(int size) { int[] ret = new int[size]; for(int i = 0; i < size; i++) ret[i] = readInt(); return ret; }
    public static String readString(){int c = readChar();while (space(c))c = readChar();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = readChar();}while (!space(c));return res.toString();}
    public static String readLine(){int c = readChar();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = readChar();}while (c != '\n');return res.toString();}
    public static double readDouble(){int c = readChar(), sgn = 1;while (space(c))c = readChar();if (c == '-'){sgn = -1;c = readChar();}double res = 0;while (!space(c) && c != '.'){if (c == 'e' || c == 'E')return res * pow(10, readInt());res *= 10;res += c - '0';c = readChar();}if (c == '.'){c = readChar();double m = 1;while (!space(c)){if (c == 'e' || c == 'E')return res * pow(10, readInt());m /= 10;res += (c - '0') * m;c = readChar();}}return res * sgn;}
    public static long readLong(){int c = readChar(), sgn = 1;while (space(c))c = readChar();if (c == '-'){sgn = -1;c = readChar();}long res = 0;do{res = (res << 1) + (res << 3);res += c - '0';c = readChar();}while (!space(c));return res * sgn;}
    private static boolean space(int c) { return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;}
    public static long pow(long x, long y, int m) {long res = 1;x = x % m;  while(y > 0) { if((y & 1) > 0) res = (res * x) % m; y = y >> 1; x = (x * x) % m;   } return res; }
    public static int pow(int x, int y, int m) {int res = 1;x = x % m;  while(y > 0) { if((y & 1) > 0) res = (res * x) % m; y = y >> 1; x = (x * x) % m;   } return res; }
    public static int pow(int x, int y) {int res = 1;  while(y > 0) { if((y & 1) > 0) res = (res * x); y = y >> 1; x = (x * x);   } return res; }
    public static int toInt(String s) { return Integer.parseInt(s); }
    public static int[] toInt(String[] s) { int[] ret = new int[s.length]; for(int i = 0; i < s.length; i++) ret[i] = toInt(s[i]); return ret; }
    public static int min(int a, int b) { return a > b? b: a; }
    public static int max(int a, int b) { return a > b? a: b; }
    public static int min(int... a) { int res = Integer.MAX_VALUE; for(int i: a) res = Math.min(res, i); return res; }
    public static int max(int... a) { int res = Integer.MIN_VALUE; for(int i: a) res = Math.max(res, i); return res; }
}
