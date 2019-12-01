int N;
final BigInteger zero = new BigInteger("0"), one = new BigInteger("1"), two = new BigInteger("2"), MOD = new BigInteger("1000000007"); BigInteger pisano = new BigInteger("1000000006");
BigInteger BN, Z;
final int mod = 1000000007;
long [][] base = new long[10][10], F = new long[10][10];

void multiply(long a[][], long b[][]) 
{ 
    long mul[][] = new long[N][N]; 
    for(int i = 0; i < N; i++) 
      for(int j = 0; j < N; j++) 
        for(int k = 0; k < N; k++) 
          mul[i][j] = (mul[i][j] + a[i][k] * b[k][j]) % mod; 
    for(int i = 0; i < N; i++) 
      System.arraycopy(mul[i], 0, a[i], 0, N);
} 

long query() 
{ 
    if(Z.compareTo(BN) <= 0) return 1;
    long res = 0; 
    
    Z = Z.subtract(BN).subtract(one);
    if(Z.and(one).equals(one)) multiply(F, base); 
    while(!Z.equals(zero))
    {
        Z = Z.shiftRight(1);
        multiply(base, base);
        if(Z.and(one).equals(one)) multiply(F, base); 
    }
    for(int i = 0; i < N; i++) res = (res + F[0][i]) % mod;
    return res;
}

void setup()
{
    N = readInt();
    BN = new BigInteger(N + "");
    for(int i = 2; i <= N; i++) pisano = pisano.multiply(MOD.pow(i).subtract(one));
    Z = new BigInteger(readString()).mod(pisano);
    for(int i = 0; i < N; i++) 
      base[0][i] = readInt();
    for(int i = 1; i < N; i++)
      base[i][i - 1] = 1;
    for(int i = 0; i < N; i++) 
      System.arraycopy(base[i], 0, F[i], 0, N);
    print(query());
}
