import java.io.IOException;

public class Main extends Template
{
    public static void main(String[] args) 
    {
        int N = ri();
        long C = rl();
        int[] x = ria(N);
        long best = 0;
        int l = 0, r = 0;
        Partial[] Deque = new Partial[N];
        for(int i = 1; i < N; i++)
        {
            Partial part = new Partial(x[i - 1], best + C);
            while(l - r > 1 && Deque[l - 2].intersect(Deque[l - 1]) > Deque[l - 2].intersect(part)) l--;
            Deque[l++] = part;
            while(r + 1 < l && Deque[r].cost(x[i]) >= Deque[r + 1].cost(x[i])) r++;
            best = Deque[r].cost(x[i]);
        }
        pln(best);
    }   
}

class Partial
{
    private long x, tot;
    public Partial(long a, long b) { x = a; tot = b; }
    public long cost(long y) { return (long) Math.pow(x - y, 2) + tot; }
    public long intersect(Partial comp) { return ((comp.tot - tot + (comp.x * comp.x) - (x * x)) / (2 * (comp.x - x))); }
}

class Template 
{ 
    /* General functions */
    private static byte[] buf = new byte[1024];
    private static int curChar, numChars;
    public static int MOD = (int) 1e9 + 7;
    public static <T>void p(T s){ System.out.print(s);}
    public static <T>void ps(T... s){ for(T t: s) {System.out.print(t); System.out.print(' ');}}
    public static <T>void pln(T s){ System.out.println(s);}
    public static void pln(){ System.out.println();}
    public static <T>void print(T s){ System.out.print(s);}
    public static <T>void prints(T... s){ for(T t: s) {System.out.print(t); System.out.print(' ');}}
    public static <T>void println(T s){ System.out.println(s);}
    public static void println(){ System.out.println();}
    public static int read(){try{if (curChar >= numChars){curChar = 0;numChars = System.in.read(buf);}if (numChars == -1)return numChars;return buf[curChar++];}catch(IOException e){throw new Error("Failed to read from IO.");}}
    public static char readChar() {int c = read(); while(space(c)) c = read(); return (char) c; }
    public static int readInt() { int c = read(), sgn = 1;while (space(c))  c = read();if (c == '-')  {sgn = -1;c = read();}int res = 0;do {res = (res << 1) + (res << 3);res += c - '0';c = read();}while (!space(c));return res * sgn;}
    public static int[] readArray(int size) { int[] ret = new int[size]; for(int i = 0; i < size; i++) ret[i] = readInt(); return ret; } 
    public static String readString(){int c = read();while (space(c))c = read();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = read();}while (!space(c));return res.toString();}
    public static String readLine(){int c = read();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = read();}while (c != '\n');return res.toString();}
    public static double readDouble(){int c = read(), sgn = 1;while (space(c))c = read();if (c == '-'){sgn = -1;c = read();}double res = 0;while (!space(c) && c != '.'){if (c == 'e' || c == 'E')return res * pow(10, readInt());res *= 10;res += c - '0';c = read();}if (c == '.'){c = read();double m = 1;while (!space(c)){if (c == 'e' || c == 'E')return res * pow(10, readInt());m /= 10;res += (c - '0') * m;c = read();}}return res * sgn;}
    public static long readLong(){int c = read(), sgn = 1;while (space(c))c = read();if (c == '-'){sgn = -1;c = read();}long res = 0;do{res = (res << 1) + (res << 3);res += c - '0';c = read();}while (!space(c));return res * sgn;}
    public static char rc() {int c = read(); while(space(c)) c = read(); return (char) c; }
    public static int ri() { int c = read(), sgn = 1;while (space(c))  c = read();if (c == '-')  {sgn = -1;c = read();}int res = 0;do {res = (res << 1) + (res << 3);res += c - '0';c = read();}while (!space(c));return res * sgn;}
    public static int[] ria(int size) { int[] ret = new int[size]; for(int i = 0; i < size; i++) ret[i] = readInt(); return ret; } 
    public static String rs(){int c = read();while (space(c))c = read();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = read();}while (!space(c));return res.toString();}
    public static String rln(){int c = read();StringBuilder res = new StringBuilder();do{res.appendCodePoint(c);c = read();}while (c != '\n');return res.toString();}
    public static double rd(){int c = read(), sgn = 1;while (space(c))c = read();if (c == '-'){sgn = -1;c = read();}double res = 0;while (!space(c) && c != '.'){if (c == 'e' || c == 'E')return res * pow(10, readInt());res *= 10;res += c - '0';c = read();}if (c == '.'){c = read();double m = 1;while (!space(c)){if (c == 'e' || c == 'E')return res * pow(10, readInt());m /= 10;res += (c - '0') * m;c = read();}}return res * sgn;}
    public static long rl(){int c = read(), sgn = 1;while (space(c))c = read();if (c == '-'){sgn = -1;c = read();}long res = 0;do{res = (res << 1) + (res << 3);res += c - '0';c = read();}while (!space(c));return res * sgn;}
    private static boolean space(int c) { return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;}
    public static long pow(long x, long y, int m) {long res = 1;x = x % m;  while(y > 0) { if((y & 1) > 0) res = (res * x) % m; y = y >> 1; x = (x * x) % m;   } return res; }
    public static int pow(int x, int y, int m) {int res = 1;x = x % m;  while(y > 0) { if((y & 1) > 0) res = (res * x) % m; y = y >> 1; x = (x * x) % m;   } return res; }
    public static int pow(int x, int y) {int res = 1;  while(y > 0) { if((y & 1) > 0) res = (res * x); y = y >> 1; x = (x * x);   } return res; }
    public static int parseInt(String s) { return Integer.parseInt(s); }
    public static int parseInt(double d) { return (int) d; }
    public static int[] parseInt(String[] s) { int[] ret = new int[s.length]; for(int i = 0; i < s.length; i++) ret[i] = pi(s[i]); return ret; }
    public static int pi(String s) { return Integer.parseInt(s); }
    public static int pi(double d) { return (int) d; }
    public static int[] pi(String[] s) { int[] ret = new int[s.length]; for(int i = 0; i < s.length; i++) ret[i] = pi(s[i]); return ret; }
    public static <T>String str(T t) { return String.valueOf(t); }
    public static String[] str(int[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(float[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(double[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(long[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(short[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(byte[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(char[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String[] str(boolean[] t) { String[] ret = new String[t.length]; for(int i = 0; i < t.length; i++) ret[i] = str(t[i]); return ret; }
    public static String join(String op, int... t) { String ret = ""; for(int tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, double... t) { String ret = ""; for(double tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, long... t) { String ret = ""; for(long tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, char... t) { String ret = ""; for(char tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, short... t) { String ret = ""; for(short tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, byte... t) { String ret = ""; for(byte tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, float... t) { String ret = ""; for(float tt: t) { ret += str(tt); ret += op; } return ret; }
    public static String join(String op, boolean... t) { String ret = ""; for(boolean tt: t) { ret += str(tt); ret += op; } return ret; }
    public static int min(int... a) { int res = Integer.MAX_VALUE; for(int i: a) res = Math.min(res, i); return res; }
    public static int max(int... a) { int res = Integer.MIN_VALUE; for(int i: a) res = Math.max(res, i); return res; }
    public static long min(long... a) { long res = Long.MAX_VALUE; for(long i: a) res = Math.min(res, i); return res; }
    public static long max(long... a) { long res = Long.MIN_VALUE; for(long i: a) res = Math.max(res, i); return res; }
    public static double min(double... a) { double res = Double.MAX_VALUE; for(double i: a) res = Math.min(res, i); return res; }
    public static double max(double... a) { double res = Double.MIN_VALUE; for(double i: a) res = Math.max(res, i); return res; }
    public static int[] tokens(String s) { return pi(s.split(" ")); }
    public static boolean pb(int x) { return x > 0; }
}
