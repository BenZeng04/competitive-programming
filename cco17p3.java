import java.io.*;
import java.util.*;

public class Main extends Template
{
    static class Query implements Comparable<Query>
    {
        public long block, segStart, segEnd;
        public long queryPos;
        public int val, ind;
        public char qt;
        private long nextBlock(int x) { return ((1l << x) - 1) << 1; }
        private int getOrder()  { return qt == 'Q'? -1 : 1; }
        public Query(long x, long y, int v, char q) // Q (uery), U (pdate), R (everse)
        {
            qt = q;
            if(q == 'Q') ind = v;
            else val = v;
            
            long start = 1;
            int firstBlock = 60;
            for(; x > 1; x = (x >> 1)) 
            {
                if((x & 1) > 0) start += nextBlock(firstBlock);
                start++;
                firstBlock--;
            }
            
            long duration = nextBlock(firstBlock) << 1 | 1;
            if(q == 'Q') queryPos = start;
            else
            {
                segStart = start;
                segEnd = start + duration;
            }
            
            start = 1;
            firstBlock = 60;
            for(; y > 1; y = (y >> 1)) 
            {
                if((y & 1) > 0) start += nextBlock(firstBlock);
                start++;
                firstBlock--;
            }
            
            duration = nextBlock(firstBlock) << 1 | 1;
            if(q == 'R') block = start + duration;
            else block = start;
        }

        @Override
        public int compareTo(Query t) 
        {
            if(t.block == block)
                return t.getOrder() - getOrder();
            return block > t.block? 1 : -1;
        }
    }
    static long[] mp;
    static Query[] queries;
    static int[] AliceTree;
    static int lsb(int x) { x++; return (x & (-x)); }
    static int sum(int x) { int res = 0; for(; x > 0; x -= lsb(x)) res += AliceTree[x]; return res; }
    static void update(int x, int v) { for(; x < AliceTree.length; x += lsb(x)) AliceTree[x] += v; }
    public static void main(String[] args) throws IOException
    {
        int n = in(), q = in(), ptr = 0;
        mp = new long[(n << 1) + q];
        AliceTree = new int[(n << 1) + q];
        queries = new Query[(n << 1) + q];
        for(int i = 0; i < n; i++)
        {
            long a = in(0l), b = in(0l);
            int c = in();
            Query blockStart = new Query(a, b, c, 'U');
            Query blockEnd = new Query(a, b, c, 'R');
            queries[ptr] = blockStart;
            mp[ptr++] = blockStart.segStart;
            queries[ptr] = blockEnd;
            mp[ptr++] = blockEnd.segEnd;
        }
        for(int i = 0; i < q; i++)
        {
            long a = in(0l), b = in(0l);
            int c = i;
            Query intermediate = new Query(a, b, c, 'Q');
            queries[ptr] = intermediate;
            mp[ptr++] = intermediate.queryPos;
        }
        Arrays.sort(mp);
        Arrays.sort(queries);
        int[] res = new int[q];
        for(int i = 0; i < queries.length; i++)
        {
            if(queries[i].qt == 'U')
            {
                int ind1 = Arrays.binarySearch(mp, queries[i].segStart), ind2 = Arrays.binarySearch(mp, queries[i].segEnd) + 1;
                update(ind1, queries[i].val);
                update(ind2, -queries[i].val);
            }
            else if(queries[i].qt == 'R')
            {
                int ind1 = Arrays.binarySearch(mp, queries[i].segStart), ind2 = Arrays.binarySearch(mp, queries[i].segEnd) + 1;
                update(ind1, -queries[i].val);
                update(ind2, queries[i].val);
            }
            else
            {
                int ind = Arrays.binarySearch(mp, queries[i].queryPos);
                int val = sum(ind);
                res[queries[i].ind] = val;
            }
        }
        for(int i: res) outln(i);
        close();
    }
}
class Template 
{ 
    private final static int BUFFER_SIZE = 1 << 24; 
    private static DataInputStream din = new DataInputStream(System.in);
    private static byte[] buffer = new byte[BUFFER_SIZE]; 
    private static int bufferPointer = 0, bytesRead = 0; 
    private static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static void fillBuffer() throws IOException { bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); if (bytesRead == -1) buffer[0] = -1; } 
    private static byte read() throws IOException { if (bufferPointer == bytesRead)  fillBuffer();  return buffer[bufferPointer++]; }
    public static <T>void out(T s){out.print(s);}
    public static <T>void outln(T s){out.println(s);}
    public static void outln(){out.println();}
    public static String inln() throws IOException {while (bufferPointer > 0 && buffer[bufferPointer - 1] == '\r') read();int cnt = 0;byte c;while ((c = read()) != '\n' && c != '\0') if (c != '\r') buffer[cnt++] = c;return new String(buffer, 0, cnt);}
    public static String in(String s) throws IOException {int cnt = 0;byte c;do {c = read();} while (c <= ' ');do {buffer[cnt++] = c;} while ((c = read()) > ' ');return new String(buffer, 0, cnt);}
    public static int in() throws IOException{ int ret = 0; byte c = read(); while (c <= ' ') c = read(); boolean neg = (c == '-'); if (neg) c = read(); do{ret = ret * 10 + c - '0'; }while ((c = read()) >= '0' && c <= '9'); if (neg) return -ret; return ret; } 
    public static long in(long l) throws IOException{ long ret = 0; byte c = read(); while (c <= ' ') c = read(); boolean neg = (c == '-'); if (neg) c = read(); do{ret = ret * 10 + c - '0'; }while ((c = read()) >= '0' && c <= '9'); if (neg) return -ret; return ret; }
    public static double in(double d) throws IOException {double ret = 0, div = 1; byte c = read(); while (c <= ' ') c = read(); boolean neg = (c == '-'); if (neg) c = read(); do { ret = ret * 10 + c - '0'; } while ((c = read()) >= '0' && c <= '9'); if (c == '.') { while ((c = read()) >= '0' && c <= '9') { ret += (c - '0') / (div *= 10); } } if (neg) return -ret; return ret; } 
    public static char getchar() throws IOException{return (char) read();}
    public static void flush(){if(out == null) return; out.flush();} 
    public static void close(){if(out == null) return; out.close();}
}
