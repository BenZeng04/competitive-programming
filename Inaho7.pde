void setup()
{
  int n = readInt(), t = readInt();
  int l = floor(pow(5 * pow(10, 6), (1.0f / n)));
  
  int [] exp = new int[n + 1];
  exp[0] = 1;
  for(int i = 1; i <= n; i++)
      exp[i] = exp[i - 1] * l;
  int [] BIT = new int[(int)pow(l, n)];
  for(int i = 0; i < BIT.length; i++) BIT[i] = readInt();
  for(int i = t == 1? 0: n - 1; t == 1? (i < n):(i >= 0); i = t == 1? i + 1: i - 1)
  {
    for(int j = t == 1? exp[n] - 1: 0; t == 1? (j >= 0): j < exp[n]; j = t == 1? j - 1: j + 1)
    {
      int ch = child(i, j, l, exp);
      if(ch != -1) BIT[ch] = t == 1? BIT[ch] - BIT[j]: BIT[ch] + BIT[j];
    }
  }
  for(int i: BIT) print(i+" ");
}

int child(int i, int j, int l, int[] exp)
{
  int val = (j % exp[i + 1]) / exp[i], simBIT = (val + 1) + ((val + 1) & -(val + 1)) - 1;
  if(simBIT >= l) return -1; else return (exp[i] * (simBIT - val)) + j;
}
