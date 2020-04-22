public class ArtAcademyIV extends Template
{
    static CVATTrick v, h;
    public static void main(String[] args)
    {
        int n = ri(), m = ri(), q = ri();
        v = new CVATTrick(n, m);
        h = new CVATTrick(m, n);
        while(q --> 0)
        {
            int type = ri();
            if(type == 1) place(ri(), ri(), ri()); // Time complexity: O(log(N + M) * Z), Z <= min(N, M)
            else if(type == 2) remove(ri(), ri(), ri()); // Time complexity: O(log(N + M) * Z), Z <= min(N, M)
            else if(type == 3) pln(query(ri(), ri(), ri())); // Time complexity: O(log(N + M) * Z), Z <= min(N, M)
        }
    }
    static void place(int x, int y, int z) { v.place(x, y, z); h.place(y, x, z); }
    static void remove(int x, int y, int z) { v.remove(x, y, z); h.remove(y, x, z); }
    static int query(int x, int y, int z) { return max(v.query(x, y, z), h.query(y, x, z)); }
    static class CVATTrick
    {
        protected BIT[] lower;
        protected int[][] upper;
        protected RMQ[] ans;
        public CVATTrick(int tiles, int rows)
        {
            tiles++;
            lower = new BIT[tiles];
            upper = new int[tiles][rows + 5];
            ans = new RMQ[tiles];
            for(int i = 0; i < tiles; i++)
            {
                lower[i] = new BIT(rows);
                ans[i] = new RMQ(rows);
            }
        }
        public void place(int x, int y, int radius)
        {
            int until = x + radius;
            for(; x < until; x++)
            {
                int backInd = y - 1;
                int frontInd = y + radius;

                int low = backInd > 0? lower[x].sum(backInd) : 0;
                int high = frontInd < lower[x].size()? lower[x].sum(frontInd) : 0;
                if(high != 0) high = upper[x][high];

                int backCount = low != 0? y - low : 0; // This is the size of the "chain" preceding the inserted chunk
                int frontCount = high != 0? high - radius - y + 1 : 0; // This is the size of the "chain" succeeding the inserted chunk

                lower[x].update(y, y - backCount);
                if(frontCount != 0)
                {
                    lower[x].update(y + radius, -(y + radius));
                    lower[x].update(y + frontCount + radius, radius + backCount);
                }
                else lower[x].update(y + radius, -(y - backCount));

                upper[x][backCount == 0? y: low] = frontCount == 0? y + radius - 1: high;
                // Updating RMQ
                ans[x].update(y - backCount, y, frontCount + radius);
                ans[x].update(y, y + radius, backCount + frontCount + radius);
                ans[x].update(y + radius, y + frontCount + radius, radius + backCount);
            }
        }
        public void remove(int x, int y, int radius)
        {
            int until = x + radius;
            for(; x < until; x++)
            {
                int start = lower[x].sum(y);
                int end = upper[x][start];

                lower[x].update(y, -start);
                lower[x].update(y + radius, y + radius);
                lower[x].update(end + 1, -(y + radius - start));

                upper[x][start] = y - 1;
                upper[x][y + radius] = end;
                // Updating RMQ
                ans[x].update(start, y, -end + y - 1);
                ans[x].update(y, y + radius, -(end - start + 1));
                ans[x].update(y + radius, end + 1, -(y + radius - start));
            }
        }
        public int query(int x, int y, int radius)
        {
            int until = x + radius, best = 0;
            for(; x < until; x++)
                best = max(best, ans[x].query(y, y + radius));
            return best;
        }
    }
}


class Template
{
    /* General functions */
    private static byte[] buf = new byte[1024];
    private static int curChar, numChars;
    public static final int MOD = (int) 1e9 + 7, INF = Integer.MAX_VALUE;
    public static final long INFL = Long.MAX_VALUE;
    public static <T>T pr(T s) {System.out.println(s); return s; }
    public static <T>void p(T s){ System.out.print(s);}
    public static <T>void ps(T... s){ for(T t: s) {System.out.print(t); System.out.print(' ');}}
    public static <T>void pln(T s){ System.out.println(s);}
    public static void pln(){ System.out.println();}
    public static <T>void print(T s){ System.out.print(s);}
    public static <T>void prints(T... s){ for(T t: s) {System.out.print(t); System.out.print(' ');}}
    public static <T>void println(T s){ System.out.println(s);}
    public static void println(){ System.out.println();}
    public static int read(){try{if (curChar >= numChars){curChar = 0;numChars = System.in.read(buf);}if (numChars == -1)return numChars;return buf[curChar++];}catch(java.io.IOException e){throw new Error("Failed to read from IO.");}}
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
    public static int gcd(int a, int b) { return b == 0? a: gcd(b, a % b); }
    public static int gcd(int... a) { int res = a[0]; for(int i = 1; i < a.length; i++) res = gcd(res, a[i]); return res; }
    public static int min(int... a) { int res = Integer.MAX_VALUE; for(int i: a) res = Math.min(res, i); return res; }
    public static int max(int... a) { int res = Integer.MIN_VALUE; for(int i: a) res = Math.max(res, i); return res; }
    public static long min(long... a) { long res = Long.MAX_VALUE; for(long i: a) res = Math.min(res, i); return res; }
    public static long max(long... a) { long res = Long.MIN_VALUE; for(long i: a) res = Math.max(res, i); return res; }
    public static double min(double... a) { double res = Double.MAX_VALUE; for(double i: a) res = Math.min(res, i); return res; }
    public static double max(double... a) { double res = Double.MIN_VALUE; for(double i: a) res = Math.max(res, i); return res; }
    public static int[] tokens(String s) { return pi(s.split(" ")); }
    public static boolean pb(int x) { return x > 0; }
    public static int choose(int n, int k) { int res = 1; for(int i = 1; i <= k; i++) res *= (n - i + 1); for(int i = 1; i <= k; i++) res /= i; return res; }
    public static class BIT
    {
        protected int a[];
        protected int n;
        private int LSB(int x) { return x & -x; }
        public BIT(int n) { a = new int[this.n = ++n]; }
        public void update(int index, int value) { for(int i = index; i < n; i += LSB(i)) a[i] += value; }
        public int sum(int index) { int ret = 0; for(int i = index; i > 0; i -= LSB(i)) ret += a[i]; return ret; }
        public int size() { return n; }
    }
    public static class RMQ
    {
        protected int[] t, d;
        protected int n, h;
        public RMQ(int N) { N += 5; n = 1; while(n < N) { n <<= 1; h++; } t = new int[N << 1]; d = new int[N]; n = N; }
        void apply(int p, int value) {t[p] += value;if (p < n) d[p] += value;}
        private void pushup(int p) {while (p > 1){ p >>= 1; t[p] = max(t[p<<1], t[p<<1|1]) + d[p];}}
        private void pushdown(int p) {for (int s = h; s > 0; --s) {int i = p >> s;if (d[i] != 0) {apply(i<<1, d[i]);apply(i<<1|1, d[i]);d[i] = 0;}}}
        public void update(int l, int r, int value) {l += n; r += n;int l0 = l, r0 = r;for (; l < r; l >>= 1, r >>= 1) {if ((l&1)>0) apply(l++, value);if ((r&1)>0) apply(--r, value);}pushup(l0);pushup(r0 - 1);}
        public int query(int l, int r) {l += n; r += n;pushdown(l);pushdown(r - 1);int res = -INF;for (; l < r; l >>= 1, r >>= 1) {if ((l&1)>0) res = max(res, t[l++]);if ((r&1)>0) res = max(t[--r], res);}return res;}
    }
}
