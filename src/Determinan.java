import java.util.Scanner;

public class Determinan {

    private int mat[][];
    private int n,m;

    Determinan (int a[][])
    {
        this.mat = (int[][])a.clone();
        this.n = a.length;
        this.m = a[0].length;
    }

    public void print_mat ()
    {
        print_mat(mat);
    }

    private void print_mat (int a[][])
    {
        for (int i = 0; i < a.length; ++i)
        {
            for (int j = 0; j  < a[0].length; ++j)
                System.out.print(a[i][j] + " ");
            System.out.print("\n");
        }
    }

    public int determinant ()
    {
        if (n == m)
            return determinant(mat,n);
        else return 0;
    }

    private int[][] cofactor (int a[][], int n, int p, int q)
    {
        int cofac[][] = new int [n-1][n-1];
        int x = 0, y = 0;
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
                if (i != p && j != q)
                {
                    cofac[x][y] = a[i][j];
                    y++;
                }
            if (y > n-2)  { ++x; y = 0; }
        }
        return cofac;
    }

    private int determinant (int a[][], int n)
    {
        if (n == 1)  return a[0][0];
        else
        {
            int res = 0;
            for (int i = 0; i < n; ++i)
                    res += ((int)Math.pow(-1, i+0)) * 
                    a[i][0] * determinant(cofactor(a, n, i, 0), n-1);
            return res;
        }
    }


    public static void main (String arg[])
    {
        Scanner readIn = new Scanner(System.in);
        int n = readIn.nextInt();
        int a[][] = new int[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                a[i][j] = readIn.nextInt();

        Determinan x = new Determinan(a);
        //x.print_mat();
        System.out.println(x.determinant());


    }
	
}
