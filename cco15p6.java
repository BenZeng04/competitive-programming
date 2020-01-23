import java.io.*;

public class Main extends Template
{
    public static DifferenceArray2D da;
    public static DisjointSet[] ds;
    public static BIT bit;
    public static int N, K, M;
    public static int shovel;
    public static double size;
    public static void main(String[] args) 
    {
        N = readInt();
        K = readInt();
        shovel = N - K + 1;
        size = shovel * shovel;
        M = readInt();
        da = new DifferenceArray2D(N, N);
        for(int m = 0; m < M; m++)
        {
            int S = readInt();
            int[] coords = readArray(S * 2);
            inclusionExclusion(S, coords);
        }
        
        da.build();
        bit = new BIT(M + 5);
        ds = new DisjointSet[shovel + 1];
        boolean[][] vis = new boolean[shovel + 1][shovel + 1];
        for(int i = 1; i <= shovel; i++)
        {
            ds[i] = new DisjointSet(shovel + 1);
            for(int j = 1; j <= shovel; j++)
                if(da.get(i, j) != 0) bit.update(da.get(i, j), 1);
        }
        
        int Q = readInt();
        while(Q-- > 0)
        {
            int qType = readInt();
            if(qType == 2) println((bit.sum(M + 1) - bit.sum(readInt() - 1)) / size);
            else
            {
                int x = readInt(), y = readInt();
                for(int i = Math.max(1, x - K + 1); i <= x && i <= shovel; i++)
                {
                    for(int j = Math.max(1, y - K + 1); j <= y && j <= shovel; j = ds[i].root(j + 1))
                    {
                        if(vis[i][j]) continue;
                        vis[i][j] = true;
                        if(da.get(i, j) != 0) bit.update(da.get(i, j), -1);
                        ds[i].union(j, j + 1);
                    }
                }
            }
        }
    }
    public static void inclusionExclusion(int D, int[] list)
    {
        for(int i = 1; i < 1 << D; i++) // Union of D rectangles
        {
            boolean valid = true;
            int x1 = 1, y1 = 1, x2 = N, y2 = N;
            for(int j = 0; j < D; j++) 
            {
                if(((i >> j) & 1) > 0) 
                {
                    int xs = Math.max(1, list[j << 1] - K + 1), ys = Math.max(1, list[j << 1 | 1] - K + 1), xe = list[j << 1], ye = list[j << 1 | 1];
                    x1 = max(x1, xs);
                    y1 = max(y1, ys);
                    x2 = min(x2, xe);
                    y2 = min(y2, ye);
                    if(x1 > x2 || y1 > y2) 
                    {
                        valid = false;
                        break;
                    }
                }
            }
            if(valid)
            {
                if((bitCount(i) & 1) > 0) da.rangeUpdate(x1, y1, x2, y2, 1);
                else da.rangeUpdate(x1, y1, x2, y2, -1);
            }
        }
    }
    private static int bitCount(int x) { int ret = 0; while(x > 0) { ret += x & 1; x >>= 1; } return ret; }
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
    public static int min(int... a) { int res = Integer.MAX_VALUE; for(int i: a) res = Math.min(res, i); return res; }
    public static int max(int... a) { int res = Integer.MIN_VALUE; for(int i: a) res = Math.max(res, i); return res; }
    public static class Rect { int x1, y1, x2, y2; public Rect(int t1, int t2, int t3, int t4) {x1 = t1; y1 = t2; x2 = t3; y2 = t4;} }
    public static class BIT
    {
        protected int[] a;
        protected int n;
        private int LSB(int x) { return x & -x; }
        public BIT(int n) { a = new int[this.n = ++n]; }
        public void update(int index, int value) { for(int i = index; i < n; i += LSB(i)) a[i] += value; }
        public int sum(int index) { index = min(index, n - 1); int ret = 0; for(int i = index; i > 0; i -= LSB(i)) ret += a[i]; return ret; }
        public int size() { return n; }
    }
    public static class DifferenceArray2D
    {
        protected int[][] a;
        protected int n, m;
        public DifferenceArray2D(int n, int m) { this.n = n; this.m = m; a = new int[n + 5][m + 5]; }
        public void rangeUpdate(int xs, int ys, int xe, int ye, int val) { xe++; ye++; a[xs][ys] += val; a[xe][ye] += val; a[xs][ye] -= val; a[xe][ys] -= val; }
        public int get(int x, int y) { return a[x][y]; }
        public void build() { for(int i = 1; i <= n; i++) for(int j = 1; j <= m; j++) a[i][j] += a[i][j - 1]; for(int i = 1; i <= m; i++) for(int j = 1; j <= n; j++) a[j][i] += a[j - 1][i]; }
    }
    public static class DisjointSet
    {
        protected int[] DS;
        public DisjointSet(int n) { DS = new int[n + 1]; for(int i = 0; i <= n; i++) DS[i] = i; }
        public int root(int n) { return DS[n] = (n == DS[n]? n: root(DS[n])); }
        public boolean connected(int x, int y) { return root(x) == root(y); }
        public boolean union(int x, int y) { int rootx = root(x), rooty = root(y); if(rootx == rooty) return false; DS[min(rootx, rooty)] = max(rootx, rooty); return true; }
    }
}
