final int MC = 1000005, MN = 300305;
int[][][][] GRID = new int[2][4][MC][];
int[] top = new int[MC], queries = new int[MN], size = new int[MC];

int grid(int a, int b, int c, int d)
{
    if(a < 0 || b < 0 || c < 1 || d < 0) return 0;
    if(GRID[a][b][c] == null || d >= GRID[a][b][c].length) return 0;
    return GRID[a][b][c][d];
}
void setup()
{
    int n = readInt(), k = readInt(), p = 1;
    HashSet<Integer> tmp = new HashSet<>();
    for(int i = 1; i <= n; i++) 
    {
        tmp.add(queries[i] = readInt());
        size[queries[i]]++;
    }
    for(int i: tmp) 
        for(int a = 0; a < 2; a++) 
            for(int b = 0; b < 4; b++)
                GRID[a][b][i] = new int[size[i] + 5];
    for(int Q = 0; Q++ < n; p ^= 1)
    {
        int col = queries[Q], row = top[col];
        for(int i = 0; i < 4; i++)
        {
            int off1 = 0, off2 = 0;
            if(i != 1) off1 = 1;
            if(i == 1 || i == 2) off2 = 1;
            if(i == 3) off2 = -1;
            int l = grid(p, i, col - off1, row - off2);
            int r = grid(p, i, col + off1, row + off2);
            int upd = l + r + 1;
            if(upd >= k)
            {
                print((p == 1?"Ashley":"Oleg") + " " + Q);
                return;
            }
            GRID[p][i][col - off1 * l][row - off2 * l] = upd;
            GRID[p][i][col + off1 * r][row + off2 * r] = upd;
        }
        top[col]++;
        
    }
}
