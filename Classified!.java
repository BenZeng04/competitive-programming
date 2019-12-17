import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
class Game
{
    ArrayList<Card> playField = new ArrayList<>();
    ArrayList<Card> hand = new ArrayList<>();
    int cash = 0;
    int damageDealt = 0;
    public Game copy()
    {
        Game g = new Game();
        for(Card c: playField) g.playField.add(c.copy());
        for(Card c: hand) g.hand.add(c.copy());
        g.cash = cash;
        g.damageDealt = damageDealt;
        return g;
    }
}
class Card
{
  int cost = -1;
  int HP = -1;
  int ATK = -1;
  int MVMT = -1;
  int RNG = -1;
  int ID = -1;
  int x = 0, y = 0; 
  boolean canAttack = false;
  boolean canMove = false; 
  boolean hasT = false;
  int player = -1; 
 
  public Card copy()
  {
    Card c = new Card(ID, ATK, HP, MVMT, RNG, cost);
    c.x = x;
    c.y = y;
    c.canAttack = canAttack;
    c.canMove = canMove;
    c.player = player;
    c.hasT = hasT;
    return c;
  }
  public Card(int ID, int ATK, int HP, int MVMT, int RNG, int cost)
  {
    this.cost = cost;
    this.HP = HP;
    this.ATK = ATK;
    this.MVMT = MVMT;
    this.RNG = RNG;
    this.ID = ID;
  }
}
public class AClassifiedProblem {
    static int highest = 0;
    static Card [] collection = new Card[11];
    static void attackPlayer(int attacker, Game g)
    {
        ArrayList<Card> playField = g.playField;
        int opp = playField.get(attacker).player % 2 + 1;
        int atk = playField.get(attacker).ATK;

        for(Card c: playField)
        {
          if(c.ID == 4 && c.player == opp)
          {
            attackCard(attacker, playField.indexOf(c), g, true);
            return;
          }
        }
        
        g.damageDealt += atk;
        highest = Math.max(g.damageDealt, highest);
    }
    static void attackCard(int attacker, int takeHit, Game g, boolean original)
    {
        ArrayList<Card> playField = g.playField;
        boolean hasBen20 = false;
        int indexOfBen20 = -1;
        
        for(Card c: playField)
        {
          if(c.ID == 4 && c.player == playField.get(takeHit).player)
          {
            if(playField.get(takeHit) != c)  
            {
              indexOfBen20 = playField.indexOf(c);
              hasBen20 = true;
            }
          }
        }
        int spellAttack = -123123132;
        if(attacker <= -1) { spellAttack = attacker; attacker = -1;
            } 
        int atk;
        if(hasBen20) takeHit = indexOfBen20; 
        if(spellAttack != -123123132) atk = spellAttack * -1;
        else atk = playField.get(attacker).ATK;
        for(Card c: playField)
          if(c.player == playField.get(takeHit).player && c.ID == 1) atk = Math.max(0, atk - 10);
        
        playField.get(takeHit).HP -= atk;
        
        if(attacker >= 0)
        {
            if(playField.get(takeHit).HP <= 0)
            {
              if(playField.get(attacker).ID == 9)
                  g.cash += 3;
            }
            if(original)
            {
                for(Card c: playField)
                {
                  if(c.player == playField.get(takeHit).player) 
                  {
                    if(c.ID == 2)
                    {
                      int index = playField.indexOf(c);
                      playField.get(index).HP -= 2;
                      attackCard(index, attacker, g, false);
                    }
                  }
                }
            }
        }
    }
    static void placeCard(int player, Card baseCard, int x, int y, Game g, boolean effectActive)
    {
        ArrayList<Card> playField = g.playField;
        Card temp = baseCard.copy();
        temp.player = player;
        temp.x = x;
        temp.y = y;
        temp.canAttack = true;
        temp.canMove = true;
        if(effectActive)
        {
            for(Card c: playField) 
            {
                if(c.ID == 7 && c.player == 1)
                {
                    temp.ATK += 2;
                    if(temp.hasT) temp.RNG++;
                }
            }
        }
        playField.add(temp);
        if(effectActive)
        {
            if(temp.ID == 0)
                for(Card c: playField) if(c.player == 1) c.ATK += 6;
            if(temp.ID == 5)
            {
                for(int i = 5; i >= 1; i--)
                {
                  for(Card c: playField)
                  {
                    if(c.player == 2 && c.y == i)
                    {
                      int maxDistMove = 0;
                      for(int j = i + 1; j <= 6; j++)
                      {
                        boolean canMove = true;
                        for(Card d: playField)
                          if(d.x == c.x && d.y == j)
                            canMove = false;
                        if(canMove)
                        {
                          maxDistMove = j - c.y;
                        }
                        else break;
                      }
                      c.y += maxDistMove;
                    }
                  }
                }
            }
            if(temp.ID == 7)
            {
                for(Card c: playField)
                {
                    if(c.player == 1)
                    {
                        c.ATK += 2;
                        if(c.hasT) c.RNG++;
                    }
                }
            }
            if(temp.ID == 10)
            {
                Card mostExpensive = new Card(0, 0, 0, 0, 0, 0);
                int hC = -1;
                for(Card c: playField)
                {
                    if(c.cost >= hC && c.ID != -1 && c.player == 2)
                    {
                        hC = c.cost;
                        mostExpensive = c;
                    }
                }
                mostExpensive.ID = -1;
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        collection[0] = new Card(0, 9, 26, 3, 3, 8);
        collection[1] = new Card(1, 10, 20, 3, 3, 10);
        collection[2] = new Card(2, 9, 12, 1, 1, 8);
        collection[3] = new Card(3, 7, 15, 1, 4, 6);
        collection[4] = new Card(4, 5, 28, 2, 2, 9);
        collection[5] = new Card(5, 3, 7, 2, 5, 4);
        collection[6] = new Card(6, 4, 15, 1, 1, 7);
        collection[7] = new Card(7, 7, 9, 2, 2, 4);
        collection[8] = new Card(8, 5, 12, 4, 2, 2);
        collection[9] = new Card(9, 10, 13, 2, 3, 5);
        collection[10] = new Card(10, 7, 13, 3, 2, 4);
        collection[2].hasT = true; collection[5].hasT = true; collection[6].hasT = true; collection[7].hasT = true;
        Game OG = new Game();
        int N = readInt(), M = readInt();
        OG.cash = M;
        for(int i = 0; i < N; i++)
            OG.hand.add(collection[SToID(readLine())].copy());
        int C = readInt();
        for(int i = 0; i < C; i++)
        {
            String s = next();
            if(s.equals("Ben")) s += " "+next();
            int ID = SToID(s);
            int x = readInt();
            int y = readInt();
            int atk = readInt();
            int hp = readInt();
            int mvmt = readInt();
            int rng = readInt();
            int player = readInt();
            placeCard(player, new Card(ID, atk, hp, mvmt, rng, collection[ID].cost), x, y, OG, false);
        }
        recur(OG);
        System.out.println(highest);
    }
    static void recur(Game g)
    {
        // Cases to check for: All possible placements, moves, attacks.
        boolean [][] availible = new boolean [2][6];
        boolean [][] attackSlot = new boolean [2][6];
        int [][] indexAt = new int[2][6];
        int [] rangeBuff = new int[2];
        for(int i = 0; i < 2; i++) for(int j = 0; j < 6; j++) 
        {
            availible[i][j] = true;
            attackSlot[i][j] = false;
            indexAt[i][j] = -1;
        }
        for(int i = 0; i < g.playField.size(); i++) //preprocessing
        {
            //System.out.println(g.playField.get(i).ATK+" pre");
            availible[g.playField.get(i).x - 1][g.playField.get(i).y - 1] = false;
            indexAt[g.playField.get(i).x - 1][g.playField.get(i).y - 1] = i;
            if(g.playField.get(i).player == 2) attackSlot[g.playField.get(i).x - 1][g.playField.get(i).y - 1] = true;
            if(g.playField.get(i).ID == 6 && g.playField.get(i).player == 1) rangeBuff[g.playField.get(i).x - 1] += 2;
        }
        for(int n = 0; n < g.hand.size(); n++)
        {
            if(g.hand.get(n).cost <= g.cash)
            {
                for(int i = 0; i < availible.length; i++)
                {
                    if(availible[i][0])
                    {
                        Game newG = g.copy();
                        placeCard(1, newG.hand.get(n), i + 1, 1, newG, true);
                        //System.out.println(newG.hand.get(n).ID);
                        newG.cash -= newG.hand.get(n).cost;
                        newG.hand.remove(n);
                        recur(newG);
                    }
                }
            }
        }
        for(int n = 0; n < g.playField.size(); n++)
        {
            if(g.playField.get(n).canAttack && g.playField.get(n).player == 1)
            {
                for(int q = 0; q < 2; q++)
                {
                  int rng = g.playField.get(n).RNG + rangeBuff[g.playField.get(n).x - 1];
                  for (int i = 1; i <= rng; i++)
                  {
                    int mx = g.playField.get(n).x, my = g.playField.get(n).y;
                    if (q == 0) my -= i; 
                    if (q == 1) my += i; 
                    if(my < 1) break;
                    if(my > 6) 
                    {
                        Game newG = g.copy();
                        if(newG.playField.get(n).ID != 8)
                            newG.playField.get(n).canMove = false; 
                        newG.playField.get(n).canAttack = false;
                        attackPlayer(n, newG);
                        recur(newG);
                        break;
                    }
                    else if(attackSlot[mx - 1][my - 1])
                    {
                        Game newG = g.copy();
                        if(newG.playField.get(n).ID != 8)
                            newG.playField.get(n).canMove = false; 
                        newG.playField.get(n).canAttack = false;
                        attackCard(n, indexAt[mx - 1][my - 1], newG, true);
                        if(newG.playField.get(n).ID == 3)
                        {
                            int x = mx, y = my;
                            for(int l = 0; l < 9; l++)
                            {
                                if(l != 4)
                                {
                                    int tx = x + l % 3 - 1;
                                    int ty = y + l / 3 - 1;
                                    if(tx > 0 && tx < 2 && ty > 0 && ty < 6)
                                        if(attackSlot[tx - 1][ty - 1])
                                            attackCard(n, indexAt[tx - 1][ty - 1], newG, true);
                                }
                            }
                        }
                        ArrayList <Card> tempRemove = new ArrayList <Card>(); 
                        for(Card c: newG.playField) // First wave of card deaths
                        { 
                          if(c.HP <= 0)
                              tempRemove.add(c);
                        }
                        for(Card c: tempRemove)
                        {
                          if(c.ID == 2)
                          {
                            for(int j = 0; j < newG.playField.size(); j++)
                            {
                                if(newG.playField.get(j).player != c.player)
                                  attackCard(-4, j, newG, true);
                            }
                          }
                        }
                        for(Card c: newG.playField) // Second wave of card deaths (Guarunteed no more due to only one astrocat879
                        { 
                          if(c.HP <= 0)
                              tempRemove.add(c);
                        }
                        for(Card c: tempRemove)
                          newG.playField.remove(c);

                        recur(newG);
                        break;
                    }
                  }
                }
            }
            if(g.playField.get(n).canMove && g.playField.get(n).player == 1)
            {
                for(int q = 0; q < 2; q++)
                {
                  for(int j = 1; j <= g.playField.get(n).MVMT; j++) // Moving
                  {
                    int mx = g.playField.get(n).x, my = g.playField.get(n).y;
                    int moveMultiplier = 1; 
                    if(q % 2 == 0) moveMultiplier = -1; 
                    my += j * moveMultiplier;
                    boolean availibleMove = true;
                    if(my <= 0 || my > 6)
                        availibleMove = false;

                    if(availibleMove && availible[mx - 1][my - 1])
                    {
                        Game newG = g.copy();
                        if(newG.playField.get(n).ID != 8)
                            newG.playField.get(n).canAttack = false; 
                        newG.playField.get(n).canMove = false;
                        newG.playField.get(n).y = my;
                        recur(newG);
                    }
                    else break;
                    }
                }
            }
        }
    }
    private static int SToID(String s)
    {
        switch(s)
        {
            case "yeahbennou":
                return 0;
            case "A.L.I.C.E.":
                return 1;
            case "astrocat879":
                return 2;
            case "Waba359":
                return 3;
            case "Ben 2.0":
                return 4;
            case "saltyRavioli":
                return 5;
            case "hewmatt10":
                return 6;
            case "kenneth_ruan":
                return 7;
            case "AlanL":
                return 8;
            case "nicoella":
                return 9;
            case "dulldesk":
                return 10;
        }
        return -1;
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static String next () throws IOException {
            while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(br.readLine().trim());
            return st.nextToken();
    }
    static long readLong () throws IOException {
            return Long.parseLong(next());
    }
    static int readInt () throws IOException {
            return Integer.parseInt(next());
    }
    static double readDouble () throws IOException {
            return Double.parseDouble(next());
    }
    static char readCharacter () throws IOException {
            return next().charAt(0);
    }
    static String readLine () throws IOException {
            return br.readLine().trim();
    }
}
