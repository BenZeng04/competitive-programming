int n, q;
int [] coords1, coords2;
int [] BIT;
int [] baseArray, size, ppa;

void setup()
{
  n = readInt();
  q = readInt();
  size = new int[n + 1];
  ppa = new int[n + 1];
  
  int tot = 1;
  for(int i = 0; i < n; i++) 
  {
    size[i] = readInt();
    ppa[i] = tot;
    tot *= size[i];
  }

  BIT = new int[tot + 10];
  baseArray = new int[tot + 10];
  coords1 = new int[n + 1];
  coords2 = new int[n + 1];
  for(int i = 0; i < q; i++) 
  {
    int qType = readInt();
    if(qType == 1)
    {
      int index = 0;
      for(int j = 0; j < n; j++) 
      {
        coords1[j] = readInt();
        index += (coords1[j] - 1) * ppa[j];
      }
      int offset = readInt();
      rawUpdate(0, 0, offset - baseArray[index]);
      baseArray[index] = offset;
    }
    else
    {
      for(int j = 0; j < n; j++) 
        coords1[j] = readInt();
      for(int j = 0; j < n; j++) 
        coords2[j] = readInt();
      println(psa(0, 0));
    }
  }
}

void rawUpdate(int index, int dimen, long offset) 
{
  if(dimen == n) BIT[index] += offset;
  else 
  {
    for(int i = coords1[dimen]; i <= size[dimen]; i += lsb(i)) 
      rawUpdate(index + ppa[dimen] * (i - 1), dimen + 1, offset);
  }
}

int lsb(int i)
{
  return i & -i;
}

long psa(int dimen, int index) 
{
  long result = 0;
  if(dimen == n) result += BIT[index];
  else 
  {
    for(int i = coords2[dimen]; i > 0; i -= lsb(i)) 
      result += psa(dimen + 1, index + (i - 1) * ppa[dimen]); 
    for(int i = coords1[dimen] - 1; i > 0; i -= lsb(i)) 
      result -= psa(dimen + 1, index + (i - 1) * ppa[dimen]);
  }
  return result;
}
