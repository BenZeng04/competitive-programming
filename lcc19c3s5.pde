final int MC = 1000005, MN = 300305, BRUTE_FORCE_THRESHOLD = 1200;
int n, k;
int[][][] LOWK = new int[2][MC][];
int[] top = new int[MC], vert = new int[MC], prev = new int[MC], queries = new int[MN], size = new int[MC];
HashMap<Integer, Integer>[][] BIT = new HashMap[MN][2]; 

void update(int ind, int val, int row, int a)
{
    if(BIT[row][a] == null) BIT[row][a] = new HashMap<>();
    if(ind < 1 || ind >= MC) return;
    for(; ind < MC; ind += (ind & (-ind)))
    {
        Integer e = BIT[row][a].get(ind);
        if(e != null) BIT[row][a].put(ind, e + val);
        else BIT[row][a].put(ind, val);
    }
}
int sum(int ind, int row, int a)
{
    if(BIT[row][a] == null) BIT[row][a] = new HashMap<>();
    if(ind < 1 || ind >= MC) return 0;
    int result = 0;
    for(; ind > 0; ind -= (ind & (-ind)))
    {
        Integer e = BIT[row][a].get(ind);
        if(e != null) 
            result += e;
    }
    return result;
}
int updateAndQuery(int col, int row, int a)
{
    int l = sum(col - 1, row, a, r = sum(col + 1, row, a), upd = l + r + 1, neg = -upd;
    if(l > 0) upd -= r + 1;
    if(r > 0) neg += l + 1;
    // Update current tile
    update(col, upd, row, a);
    update(col + 1, neg, row, a);
    // Update l and r blocks
    if(l > 0) update(col - l, r + 1, row, a);
    if(r > 0) update(col + r + 1, -(l + 1), row, a);
    return l + r + 1;
}
void setup()
{
    n = readInt(); k = readInt();
    int p = 1;
    if(k < BRUTE_FORCE_THRESHOLD) // Precomputing the size that the brute force array should be (Obviously only if brute force is neccesary)
    {
        HashSet<Integer> tmp = new HashSet<>();
        for(int i = 1; i <= n; i++) 
        {
            tmp.add(queries[i] = readInt());
            size[queries[i]]++;
        }
        for(int i: tmp) 
        {
            LOWK[0][i] = new int[size[i] + 5];
            LOWK[1][i] = new int[size[i] + 5];
        }
    }
    for(int Q = 0, col; Q++ < n; p ^= 1)
    {
        int horizInARow = 0, diagInARow = 0, otherDiag = 0;
        if(k >= BRUTE_FORCE_THRESHOLD) // Use BIT if brute force will TLE
        {
            col = readInt();
            if(k * top[col] < n) 
                horizInARow = updateAndQuery(col, top[col], p);
            // At this threshold, the k value is too large for there to possibly be a diagonal of length k
        }
        else // Use brute force for small values of K
        {
            col = queries[Q];
            LOWK[p][col][top[col]] = 1;
            LOWK[1 - p][col][top[col]] = 0;
            for(int t = 1; t <= 6; t++)
            {
                int l, h;
                for(int i = 1; i <= k; i++)
                {
                    if(t % 2 == 1) l = col - i; else l = col + i;
                    if(t == 3 || t == 6) h = top[col] - i; else if(t == 4 || t == 5) h = top[col] + i; else h = top[col];
                    if(l <= 0 || h < 0 || h > top[l] || LOWK[p][l] == null || LOWK[p][l][h] != 1) break;
                    if(t < 3) horizInARow++; else if(t < 5) diagInARow++; else otherDiag++;
                }
            }
            horizInARow++; diagInARow++; otherDiag++;
        }
        // Vertical case
        top[col]++;
        if(prev[col] == p) vert[col]++; else vert[col] = 1;
        if(vert[col] >= k || horizInARow >= k || diagInARow >= k || otherDiag >= k)
        {
            print((p == 1?"Ashley":"Oleg") + " " + Q);
            return;
        }
        prev[col] = p;
    }
}
