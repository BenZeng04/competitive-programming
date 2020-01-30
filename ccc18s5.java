import java.io.*;
import java.util.*;

public class DMOPC extends Template
{
    static DisjointSet[] DS = new DisjointSet[2];
    static PriorityQueue<Edge> PQ = new PriorityQueue<>();
    public static void main(String[] args) throws IOException
    {
        int[] in = readArray(4);
        DS[0] = new DisjointSet(in[0]);
        DS[1] = new DisjointSet(in[1]);
        long saved = 0;
        for(int i = 0; i < in[2]; i++) 
        {
            Edge e = readEdge(1);
            PQ.add(e);
            saved += (long) e.weight * in[0];
        }
        for(int i = 0; i < in[3]; i++)
        {
            Edge e = readEdge(0);
            PQ.add(e);
            saved += (long) e.weight * in[1];
        }
        while(!PQ.isEmpty()) 
        {
            Edge cur = PQ.poll();
            saved -= DS[cur.type].union(cur.from, cur.to)? (long) cur.weight * DS[1 - cur.type].edgesLeft: 0;
        }
        println(saved);
    }
}

class Template 
{ 
    /* General functions */
    private static InputStream stream = System.in;
    private static byte[] buf = new byte[1024];
    private static int curChar, numChars;
    public static <T>void print(T s){ System.out.print(s);}
    public static <T>void println(T s){ System.out.println(s);}
    public static void println(){ System.out.println();}
    public static int readChar(){try{if (curChar >= numChars){curChar = 0;numChars = stream.read(buf);}if (numChars == -1)return numChars;return buf[curChar++];}catch(IOException e){throw new Error("Failed to read from IO.");}}
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
    public static class Pair<T1, T2> { T1 first; T2 second; public Pair(T1 c, T2 d) { first = c; second = d; } }
    public static class Edge implements Comparable<Edge> { int from, to, weight, type; public Edge(int f, int t, int w, int ty) { from = f; to = t; weight = w; type = ty; } public int compareTo(Edge e) { return weight - e.weight; } }
    public static class DisjointSet
    {
        int[] DS;
        int edgesLeft;
        public DisjointSet(int n) { edgesLeft = n; DS = new int[n + 1]; for(int i = 0; i <= n; i++) DS[i] = i; }
        public int root(int n) { return DS[n] = (n == DS[n]? n: root(DS[n])); }
        public boolean connected(int x, int y) { return root(x) == root(y); }
        public boolean union(int x, int y) { int rootx = root(x), rooty = root(y); if(rootx == rooty) return false; DS[rootx] = rooty; edgesLeft--; return true; }
    }
    public static Edge readEdge(int type) { return new Edge(readInt(), readInt(), readInt(), type); }
    /* End */
}
