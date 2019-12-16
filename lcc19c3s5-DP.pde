final int[][] MAT = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
int[][][][] GRID = new int[2][4][1000005][];
int[] top = new int[1000005], height = new int[1000005], queries = new int[300305];

int grid(int a, int b, int c, int d)
{
    if(a < 0 || b < 0 || c < 1 || d < 0 || GRID[a][b][c] == null || d >= GRID[a][b][c].length) return 0;
    else return GRID[a][b][c][d];
}

void setup()
{
    int n = readInt(), k = readInt(), p = 1;
    HashSet<Integer> uc = new HashSet<>();
    for(int i = 1; i <= n; i++) 
    {
        uc.add(queries[i] = readInt());
        height[queries[i]]++;
    }
    for(int i: uc) for(int a = 0; a < 2; a++) for(int b = 0; b < 4; b++) GRID[a][b][i] = new int[height[i]];
    for(int Q = 0; Q++ < n; p ^= 1)
    {
        int col = queries[Q], row = top[col];
        for(int i = 0; i < 4; i++)
        {
            int l = grid(p, i, col - MAT[i][0], row - MAT[i][1]);
            int r = grid(p, i, col + MAT[i][0], row + MAT[i][1]);
            GRID[p][i][col - MAT[i][0] * l][row - MAT[i][1] * l] = l + r + 1;
            GRID[p][i][col + MAT[i][0] * r][row + MAT[i][1] * r] = l + r + 1;
            if(l + r + 1 >= k)
            {
                print((p == 1?"Ashley":"Oleg") + " " + Q);
                return;
            }
        }
        top[col]++;
    }
}
