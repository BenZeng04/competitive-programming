/**
 * 
 * @author hewmatt10
 */

final static int INF = MAX_INT;
final static Node NULL = new Node(-1, 0); {
    NULL.min = INF;
    NULL.max = -INF;
    NULL.sz = 0;
    NULL.isNull = true;
}
final static class Node
{
    int i, v, sz, sum, min, max, lz, inc;
    boolean rev, isNull;
    Node p, l, r;
    public Node(int i, int v) 
    {
        this.i = i;
        p = l = r = NULL;
        this.v = sum = min = max = v;
        lz = INF;
        sz = 1;
    }
    public final void resolve()
    {
        if(isNull) return;
        if(rev)
        {
            Node temp = l; l = r; r = temp;
            if(!l.isNull) l.rev ^= true; if(!r.isNull) r.rev ^= true; 
            rev = false;
        }
        if(lz != INF)
        {
            v = min = max = lz;
            sum = lz * sz;
            if(!l.isNull) { l.lz = lz; l.inc = 0; }
            if(!r.isNull) { r.lz = lz; r.inc = 0; } lz = INF;
        }
        if(inc != 0)
        {
            v += inc; min += inc; max += inc; sum += inc * sz;
            if(!l.isNull) l.inc += inc; if(!r.isNull) r.inc += inc;
            inc = 0;
        }
    }
    public final void update()
    {
        if(isNull) return;
        l.resolve(); r.resolve();
        max = max(v, l.max, r.max);
        min = min(v, l.min, r.min);
        sum = l.sum + r.sum + v;
    }
    public final void resize() { if(isNull) return; sz = l.sz + r.sz + 1; }
    public final void rotate(boolean x)
    {
        Node par = p;
        if(x) { par.l = r; if(!r.isNull) r.p = par; }
        else { par.r = l; if(!l.isNull) l.p = par; }
        p = par.p;
        if(!p.isNull)
        {
            if(par.p.l == par) p.l = this;
            if(par.p.r == par) p.r = this;
        }
        par.p = this;
        if(x) r = par; else l = par;
        par.resize(); par.resolve(); par.update();
    }
    public final void splay()
    {
        while(isRoot())
        {
            boolean co = p.isRoot();
            if(co) p.p.resolve(); p.resolve(); resolve();
            if(co)
            {
                boolean left = p.p.l == p;
                if((left? p.r: p.l) == this) { rotate(!left); rotate(left); }
                else { p.rotate(left); rotate(left); }
            } 
            else rotate(p.l == this);
        }
        resize(); resolve(); update();
    }
    public final Node find()
    {
        Node le = NULL, n = this;
        while(!n.isNull) { n.splay(); n.r = le; le = n; n = n.p; }
        splay();
        return le;
    }
    public final void rev() { find(); rev ^= true; }
    public final boolean isRoot() { return !p.isNull && (p.l == this || p.r == this); }
}
final static Node LCA(Node u, Node v) { u.find(); return v.find(); }
final static Node path(Node u, Node v) { u.rev(); v.find(); return v; }
final static void link(Node u, Node v) { u.rev(); u.p = v; }
final static void cut(Node u) { u.find(); if(u.l.isNull) return; u.l.p = NULL; u.l = NULL; u.resize(); u.update(); }
final static void parc(Node u, Node v) { if(LCA(u, v) == u) return; cut(u); link(u, v); }
final static Node[] tree = new Node[100005];
void setup()
{
    int N = readInt(), Q = readInt();
    for(int i = 1; i <= N; i++) tree[i] = new Node(i, readInt());
    for(int i = 1; i < N; i++) link(tree[readInt()], tree[readInt()]);
    int root = readInt();
    tree[root].rev();
    while(Q --> 0)
    {
        int t = readInt();
        if(t == 0) root = readInt();
        else if(t == 1) path(tree[readInt()], tree[readInt()]).lz = readInt();
        else if(t == 2) path(tree[readInt()], tree[readInt()]).inc = readInt();
        else if(t == 3) println(path(tree[readInt()], tree[readInt()]).min);
        else if(t == 4) println(path(tree[readInt()], tree[readInt()]).max);
        else if(t == 5) println(path(tree[readInt()], tree[readInt()]).sum);
        else if(t == 6) parc(tree[readInt()], tree[readInt()]);
        else println(LCA(tree[readInt()], tree[readInt()]).i);
        if(t != 7) tree[root].rev();
    }
}
