#pragma GCC optimize "Ofast"
#pragma GCC optimize "unroll-loops"
#pragma GCC optimize "omit-frame-pointer"
#pragma GCC optimize "prefetch-loop-arrays"
#pragma GCC target "sse,sse2,sse3,sse4,abm,avx,aes,sse4a,sse4.1,sse4.2,mmx,popcnt,tune=native"

#include <bits/stdc++.h>
#include <stdio.h>
#define getchar getchar_unlocked
#define bit BIT[row][a]
#define scan(x) do{while((x=buf[ptr++])<'0'); for(x-='0'; '0'<=(_=buf[ptr++]); x=(x<<3)+(x<<1)+_-'0');}while(0)
using namespace std;

char buf[20000000];
const int MC = 1000005, MN = 300005, BRUTE_FORCE_THRESHOLD = 1600;
vector<short> LOWK[2][MC]; 
unordered_map<int, int> BIT[MN][2]; 
int top[MC], vert[MC];
int n, k, ptr;
char _;

inline void update(int ind, int val, int row, int a)
{
    if(ind < 1 || ind >= MC) return;
    for(; ind < MC; ind += (ind & (-ind)))
    {
        if(bit.count(ind) != 0) 
            bit[ind] += val;
        else 
            bit.insert({ind, val});
    }
}
inline int sum(int ind, int row, int a)
{
    if(ind < 1 || ind >= MC) return 0;
    int result = 0;
    for(; ind > 0; ind -= (ind & (-ind)))
    {
        if(bit.count(ind) != 0) 
            result += bit[ind];
    }
    return result;
}
inline int updateAndQuery(int col, int row, int a)
{
    int l = sum(col - 1, row, a);
    int r = sum(col + 1, row, a);
    int upd = l + r + 1, neg = -upd;
    if(l > 0) upd -= r + 1;
    if(r > 0) neg += l + 1;
    // Update current tile
    update(col, upd, row, a);
    update(col + 1, neg, row, a);
    // Update l and r blocks
    if(l > 0)
        update(col - l, r + 1, row, a);
    if(r > 0)
        update(col + r + 1, -(l + 1), row, a);
    return l + r + 1;
}

int main() 
{
    fread(buf, sizeof(buf), 1, stdin);
    scan(n); scan(k);
    bool p = true;
    for(int Q = 0, col; Q++ < n; p ^= true)
    {
        scan(col);
        int horizInARow = 0, diagInARow = 0, otherDiag = 0;
        if(k >= BRUTE_FORCE_THRESHOLD) // Use BIT if brute force will TLE
        {
            if(k * top[col] < n) 
                horizInARow = updateAndQuery(col, top[col], p);
            // It is 100% GUARUNTEED that the win condition is NOT a diagonal (since k > 1600)
        }
        else // Use brute force for small values of K
        {
            int hl = 0, hr = 0, dl1 = 0, dr1 = 0, dl2 = 0, dr2 = 0;
            LOWK[p][col].push_back(1);
            LOWK[!p][col].push_back(0);
            for(int i = 1; i <= k; i++)
            {
                if(col - i <= 0) break;
                if(top[col] >= LOWK[p][col - i].size()) break;
                if(LOWK[p][col - i][top[col]] != 1) break;
                hl++;
            }
            for(int i = 1; i <= k; i++)
            {
                if(top[col] >= LOWK[p][col + i].size()) break;
                if(LOWK[p][col + i][top[col]] != 1) break;
                hr++;
            }
            for(int i = 1; i <= k; i++)
            {
                if(col - i <= 0) break;
                if(top[col] - i >= LOWK[p][col - i].size()) break;
                if(LOWK[p][col - i][top[col] - i] != 1) break;
                dl1++;
            }
            for(int i = 1; i <= k; i++)
            {
                if(top[col] + i >= LOWK[p][col + i].size()) break;
                if(LOWK[p][col + i][top[col] + i] != 1) break;
                dr1++;
            }
            for(int i = 1; i <= k; i++)
            {
                if(col - i <= 0) break;
                if(top[col] + i >= LOWK[p][col - i].size()) break;
                if(LOWK[p][col - i][top[col] + i] != 1) break;
                dl2++;
            }
            for(int i = 1; i <= k; i++)
            {
                if(top[col] - i >= LOWK[p][col + i].size()) break;
                if(LOWK[p][col + i][top[col] - i] != 1) break;
                dr2++;
            }
            horizInARow = hl + hr + 1;
            diagInARow = dl1 + dr1 + 1;
            otherDiag = dl2 + dr2 + 1;
        }
        top[col]++;
        if(prev[col] == p) vert[col]++;
        else vert[col] = 1;
        if(vert[col] >= k || horizInARow >= k || diagInARow >= k || otherDiag >= k)
        {
            printf("%s %d", p?"Ashley":"Oleg", Q);
            return 0;
        }
        prev.set(col, p);
    }
}
