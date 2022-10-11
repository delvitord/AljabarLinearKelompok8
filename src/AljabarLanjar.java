import java.util.Scanner ;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.io.FileNotFoundException;

public class AljabarLanjar {
    int i,j,imax,jmax,n;
    double inter;
    boolean banyaksolusi;
    double[][] data,data2;
    class titik {
        double x,y;
    }
    titik[] tabtitik;
    String namafile;


    AljabarLanjar() {
        imax = 0;
        jmax = 0;
        data = new double[20][20];
        data2 = new double[20][20];
        tabtitik = new titik[100];
        for (i=1;i<=99;i++) {
            tabtitik[i] = new titik();
        }
    }

    void tulismatriks() {
        for(i=1;i<=imax;i++) {
            for(j=1;j<=jmax;j++) {
                System.out.print(String.format("%6.2f",data[i][j])+" ");
            }
            System.out.println("");
        }
    }

    void keyboard_spl() {
        Scanner S;
        S = new Scanner (System.in);
        System.out.println("----------------------------");
        System.out.println("--------- Matriks ----------");
        System.out.println("----------------------------");
        System.out.print("Jumlah Kolom : ");
        jmax = S.nextInt();
        System.out.print("Jumlah Baris : ");
        imax = S.nextInt();
        System.out.println("\nMasukkan Matriks : \n*spasi untuk batas kolom, enter untuk batas baris*");
        for (i=1;i<=imax;i++) {
            for (j=1;j<=jmax;j++) {
                data[i][j] = S.nextDouble();
            }
        }

    }

    void OBE_geser(int a,int b) {
        double temp;
        for (j=1;j<=jmax;j++) {
            temp = data[a][j];
            data[a][j] = data[b][j];
            data[b][j] = temp;
        }
    }

    void OBE_bagikons(int baris,double k) {
        for(j=1;j<=jmax;j++) {
            data[baris][j] = data[baris][j] / k;
        }
    }

    void OBE_perslain(int baristarget, int barislain, double k) {
        for(j=1;j<=jmax;j++){
            data[baristarget][j] = data[baristarget][j] + (k * data[barislain][j]);
        }
    }

    boolean IsKolomZero(int kolom, int bawal , int bakhir) {
        boolean nolsemua = true;
        for (i=bawal;i<=bakhir;i++) {
            if (data[i][kolom] != 0) {
                nolsemua = false;
            }
        }
        return nolsemua;
    }

    boolean IsBarisZero(int baris) {
        boolean nolsemua = true;
        for(i=1;i<=jmax;i++){
            if ( data[baris][i] != 0) {
                nolsemua = false;
            }
        }
        return nolsemua;
    }

    void pemorosan() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Pemrosesan Matriks");
        System.out.println("----------------------------------------------------------------");

        int poros,kolom,last_kolom,last_baris,n,nproses;
        n = 1;
        kolom = 1;
        nproses = imax;
        last_kolom = jmax;
        last_baris = imax;
        while (n <= nproses) {
            do {
                if (IsKolomZero(kolom,n,imax) == true)
                    kolom++;
            }
            while ( IsKolomZero(kolom,n,imax) == true);

            if (IsBarisZero(n) == true) {
                nproses--;
            }

            poros=n;
            for (i=n+1;i<=imax;i++) {
                if ( Math.abs(data[i][kolom]) > Math.abs(data[poros][kolom]) ) {
                    poros = i;
                }
            }

            if (n != poros) {
                OBE_geser(n,poros);

                System.out.println("OBE : Geser "+n+" <-> "+poros);
                System.out.println("");
                tulismatriks();
                System.out.println("");
            }


            System.out.println("OBE : Baris ke-"+n+" dibagi "+data[n][kolom]);
            System.out.println("");
            if (data[n][kolom] != 0) {
                OBE_bagikons(n,data[n][kolom]);
            }

            tulismatriks();
            System.out.println("");

            last_kolom = kolom;
            last_baris = n;

            if (n != imax) {
                for(i=n+1;i<=imax;i++) {
                    OBE_perslain(i,n,(-1)*data[i][kolom]);
                }

                System.out.println("OBE : Jadikan nol..");
                System.out.println("");
                tulismatriks();
                System.out.println("");
            }


            kolom++;
            n++;
            nproses = Rank();

        }



        while (last_baris != 1) {

            do {
                if (data[last_baris][last_kolom] == 0) {
                    last_kolom--;
                }
            }
            while ((data[last_baris][last_kolom] == 0) );

            System.out.println("");
            System.out.println("OBE : Backward phase, "
                    + "baris ke-"+last_baris);
            System.out.println("");
            for (i=last_baris-1;i>=1;i--) {
                OBE_perslain(i,last_baris, (-1) * data[i][last_kolom]);
            }
            tulismatriks();


            last_baris--;
            last_kolom--;
            if (last_baris == 1) {
                break;
            }
            if( (data[last_baris][last_kolom] != 1) ) {
                do {
                    last_kolom--;
                }
                while (data[last_baris][last_kolom] != 1);
            }
        }

        if ((IsBarisZero(imax) == true) || ((jmax-1) > imax) ) {
            banyaksolusi = true;
        }
        else {
            banyaksolusi = false;
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("Proses Selesai...");
        System.out.println("----------------------------------------------------------------");
        System.out.println("");

    }


    boolean IsTanpaSolusi() {
        boolean temp;
        temp = false;
        int x = imax;
        if (IsBarisZero(x) == true) {
            do {
                x--;
            }
            while (IsBarisZero(x) == true);
        }
        if ((data[x][jmax] == 1) && (data[x][jmax-1] == 0) ) {
            return true;
        }
        else {
            return false;
        }
    }





    int Rank() {
        int temp = 0;
        int x;
        for(x=1;x<=imax;x++) {
            if (IsBarisBukanZero(x) == true) {
                temp++;
            }
        }
        return temp;
    }

    boolean IsBanyakSolusi() {
        int temp = 0;
        int x;
        for(x=1;x<=imax;x++) {
            if (IsBarisBukanZero(x) == true) {
                temp++;
            }
        }
        if ((jmax-1) > temp ) {
            return true;
        }
        else {
            return false;
        }

    }

    boolean IsBarisBukanZero(int baris) {
        boolean temp;
        temp = false;
        for (i=1;i<=jmax;i++) {
            if (data[baris][i] != 0) {
                temp = true;
            }
        }
        return temp;

    }


    void kesimpulan() {
        int nvar;
        char[] param;
        char[] mapping;
        param = new char[9];
        mapping = new char[100];
        String[] kefile;
        kefile = new String[100];
        int nline;
        boolean ftoken;
        nline=0;


        if ( IsTanpaSolusi() == true) {
            System.out.println("Tidak ada solusi");
            nline++;
            kefile[nline]="Tidak ada solusi";
        }


        else if ( IsBanyakSolusi() == true) {

            System.out.println("Banyak solusi");
            nline++;
            kefile[nline]="Banyak solusi";
            param[1]='s';
            param[2]='t';
            param[3]='u';
            param[4]='v';
            param[5]='w';
            param[6]='x';
            param[7]='y';
            param[8]='z';
            for (j=1; j<=(jmax-1) ;j++) {
                mapping[j]='0';
            }
            i=1;
            j=1;
            nvar=1;

            while ( (j < jmax) && (i<=imax) ) {
                if (data[i][j] == 1) {
                    System.out.print("x"+j+" = ");
                    kefile[nline]="x"+j+" = ";
                    j++;
                    ftoken=true;
                    if (data[i][jmax] != 0) {
                        System.out.print(data[i][jmax]);
                        kefile[nline]=kefile[nline]+data[i][jmax];
                        ftoken=false;
                    }

                    if (j != jmax) {

                        do {
                            if (data[i][j] <= 0.01) {
                                j++;
                            }
                            else {
                                if (i==1) {
                                    mapping[j]=param[nvar];
                                    nvar++;
                                }
                                if (ftoken == false) {
                                    System.out.print(" + ");
                                    System.out.print("("+ (-1)*data[i][j] +
                                            mapping[j]+")");
                                    kefile[nline]=kefile[nline]+" + ";
                                    kefile[nline]=kefile[nline]+"("+ (-1)*data[i][j] +
                                            mapping[j]+")";

                                }
                                else {

                                    System.out.print("("+ (-1)*data[i][j] +
                                            mapping[j] + ")");
                                    kefile[nline]=kefile[nline]+"("+ (-1)*data[i][j] +
                                            mapping[j]+")";
                                    ftoken = false;
                                }
                                j++;
                            }
                        } while ( j != jmax);
                        System.out.println("");
                        nline++;
                        j=1;
                        i++;
                    }
                }
                else {
                    j++;
                    if (j >= jmax) {
                        j = 1;
                        i++;
                    }
                }
            }

            System.out.println("");
            for (j=1;j<=(jmax-1);j++) {
                if (mapping[j] != '0') {
                    System.out.println("x"+j+" = "+mapping[j]);
                    nline++;
                    kefile[nline]="x"+j+" = "+mapping[j];
                }
            }
        }

        else {

            for(i=1;i<=imax;i++) {
                System.out.println("x"+i+" = "+data[i][jmax]);
                nline++;
                kefile[nline]="x"+i+" = "+data[i][jmax];
            }

        }

        try {
            PrintStream out = new PrintStream(new FileOutputStream("keluaran_"+namafile));
            for (i=1;i<=nline;i++) {
                out.println(kefile[i]);
            }
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void file_spl() {
        Scanner S1 ;
        String filename ;
        S1 = new Scanner (System.in);
        filename = new String();
        //filename = "src/test.txt";
        System.out.print("Masukkan nama file	: ");
        filename = S1.nextLine();
        namafile = filename;
        try {
            System.out.println("");
            System.out.println("----------------------------------------------------------------");
            System.out.println("File "+filename+" ditemukan");
            System.out.println("----------------------------------------------------------------");
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 0;
            int col = 0;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                col = vals.length;
                row++;
            }
            imax = row ;
            jmax = col ;
            data = new double[row+1][col+1];


        }
        catch (IOException e) {System.out.println("File tidak ada");}
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 1;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                for (int i = 1; i <= jmax; i++) {
                    data[row][i] = Double.parseDouble(vals[i-1]);
                }
                row++;
            }
        } catch (IOException e) {}
        tulismatriks();
        System.out.println("");
    }

        static void perkalianMatriks() {
            int row1, col1, row2, col2;
            Scanner s = new Scanner(System.in);

            System.out.print("Masukan jumlah baris matriks pertama: ");
            row1 = s.nextInt();

            System.out.print("Masukan kolom baris matriks pertama: ");
            col1 = s.nextInt();

            System.out.print("Masukan jumlah baris matriks kedua: ");
            row2 = s.nextInt();

            System.out.print("Masukan jumlah kolom matriks kedua: ");
            col2 = s.nextInt();

            if (col1 != row2) {
                System.out.println("perkalian matriks tidak dapat dilakukan");
                return;
            }

            int a[][] = new int[row1][col1];
            int b[][] = new int[row2][col2];
            int c[][] = new int[row1][col2];

            System.out.println("\nMasukan nilai matriks pertama: ");
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col1; j++) a[i][j] = s.nextInt();
            }
            System.out.println("\nMasukan nilai matriks kedua: ");
            for (int i = 0; i < row2; i++) {
                for (int j = 0; j < col2; j++) b[i][j] = s.nextInt();
            }

            System.out.println("\nHasil perkalian matriks adalah : ");
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col2; j++) {
                    c[i][j] = 0;
                    for (int k = 0; k < col1; k++) {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                    System.out.print(c[i][j] + " ");
                }
                System.out.println();
            }
    }

    static void transposeMatriks() {
        int i, j, m, n;
        int matriks[][] = new int[10][10];
        int transpose[][] = new int[10][10];
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        m = scan.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        n = scan.nextInt();
        System.out.println("Masukkan elemen matriks: ");
        for(i = 0; i < m; i++){
            for(j = 0; j< n; j++){
                matriks[i][j] = scan.nextInt();
            }
        }
        for(i = 0; i < m; i++){
            for(j = 0; j< n; j++){
                transpose[j][i] = matriks[i][j];
            }
        }
        System.out.println("Hasil transpose matriks: ");
        for(i = 0; i < n; i++){
            for(j = 0; j< m; j++){
                System.out.print(transpose[i][j] + "\t");
            }
            System.out.println();
        }

    }


    

    public static void InverseMatriks () 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan panjang matriks (n x n): ");
        int n = input.nextInt();
        double a[][]= new double[n][n];
        System.out.println("Masukkan elemen matriks: ");
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                a[i][j] = input.nextDouble();
 
        double d[][] = invert(a);
 
        System.out.println("The inverse is: ");
        for (int i=0; i<n; ++i) 
        {
            for (int j=0; j<n; ++j)
            {
                System.out.print(d[i][j]+"  ");
            }
            System.out.println();
        }
        //input.close();
    }	
 
    public static double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
        // Transform the matrix into an upper triangle
        gaussian(a, index);
 
        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
        // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
        // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

   
  //================DETERMINAN====================//

    //================DETERMINAN====================//

    public static void determinan_matriks() {
        Scanner readIn = new Scanner(System.in);
        System.out.print("\nDimensi Matriks (n x n, contoh = 4): ");
        int n = readIn.nextInt();
        int a[][] = new int[n][n];
        System.out.println("\nBuat Matriks : ");
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                a[i][j] = readIn.nextInt();

        Determinan x = new Determinan(a);
        //x.print_mat();
        System.out.println("Determinan matriks : " + x.determinant());
    }
//======================================================================

    public static void penjumlahan_matriks()
      {
            int  i, j, brs, klm;
            Scanner scan = new Scanner(System.in);
            System.out.print("Masukkan jumlah baris matriks: ");
            brs = scan.nextInt();
            System.out.print("Masukkan jumlah kolom matriks: ");
            klm = scan.nextInt();
            int   A[][] = new int[brs][klm];
            int   B[][] = new int[brs][klm];
            int sum[][] = new int[brs][klm];
            System.out.println("Masukkan elemen matriks A: ");
             for(i = 0; i < brs; i++){
                for(j = 0; j< klm; j++){
                A[i][j] = scan.nextInt();
                }
            }

            System.out.println("Masukkan elemen matriks B: ");
            for(i = 0; i < brs; i++){
               for(j = 0; j< klm; j++){
               B[i][j] = scan.nextInt();
               }
           }
               
            /* Penjumlahan Matriks*/
            for ( i = 0 ; i < brs ; i++ )
            {
              for ( j = 0 ; j < klm ; j++ )
              {
               sum[i][j] = A[i][j] + B[i][j];  
                 }  
            }
            
            //  menampilkan output 
            System.out.println("\nHasil Penjumlahan Matriks");
            System.out.println("=======================");
            for ( i = 0 ; i < brs ; i++ )
            {
               for ( j = 0 ; j < klm ; j++ )
               {
                   System.out.print(sum[i][j]+"\t");
               }
                  
               System.out.println();
            }
      }

      public static void pengurangan_matriks()
      {
            int  i, j, brs, klm;
            Scanner scan = new Scanner(System.in);
            System.out.print("Masukkan jumlah baris matriks: ");
            brs = scan.nextInt();
            System.out.print("Masukkan jumlah kolom matriks: ");
            klm = scan.nextInt();
            int   A[][] = new int[brs][klm];
            int   B[][] = new int[brs][klm];
            int sum[][] = new int[brs][klm];
            System.out.println("Masukkan elemen matriks A: ");
             for(i = 0; i < brs; i++){
                for(j = 0; j< klm; j++){
                A[i][j] = scan.nextInt();
                }
            }

            System.out.println("Masukkan elemen matriks B: ");
            for(i = 0; i < brs; i++){
               for(j = 0; j< klm; j++){
               B[i][j] = scan.nextInt();
               }
           }
               
            /* Pengurangan Matriks*/
            for ( i = 0 ; i < brs ; i++ )
            {
              for ( j = 0 ; j < klm ; j++ )
              {
               sum[i][j] = A[i][j] - B[i][j];  
                 }  
            }
            
            //  menampilkan output 
            System.out.println("\nHasil Pengurangan Matriks");
            System.out.println("=======================");
            for ( i = 0 ; i < brs ; i++ )
            {
               for ( j = 0 ; j < klm ; j++ )
               {
                   System.out.print(sum[i][j]+"\t");
               }
                  
               System.out.println();
            }
      }
  
  //==============================================//    
    

    public static void main(String[] args) {
        AljabarLanjar M;
        M = new AljabarLanjar();
        Scanner S;
        S = new Scanner(System.in);
        boolean valid = true;
        boolean stop = false;
        int choice;
        do {
            System.out.println("==============================================================");
            System.out.println(" Aplikasi Kalkulator Matriks");
            System.out.println("==============================================================");
            System.out.println(" 1 : Penyelesaian SPL (input keyboard)");
            System.out.println(" 2 : Penyelesaian SPL (file) ");
            System.out.println(" 3 : Perkalian Matriks ");
            System.out.println(" 4 : Tranpose Matriks ");
            System.out.println(" 5 : Invers Matriks ");
            System.out.println(" 6 : Determinan Matriks ");
            System.out.println(" 7 : Penjumlahan Matriks ");
            System.out.println(" 8 : Pengurangan Matriks ");
            do {
                System.out.print("Masukkan pilihan	: ");
                choice = S.nextInt();
                if ( (choice > 9) || (choice <= 0) ) {
                    System.out.println("Masukan salah , ulangi...");
                    valid = false;
                }
                else {
                    valid = true;
                }
            }

            while (valid == false);

            switch (choice) {
                case 1 :
                    M.keyboard_spl();
                    M.pemorosan();
                    M.tulismatriks();
                    M.kesimpulan();
                    System.out.println("Output program tersimpan pada file keluaran.txt...");
                    break;
                case 2 :
                    M.file_spl();
                    M.pemorosan();
                    M.kesimpulan();
                    System.out.println("Output program tersimpan pada file keluaran.txt...");
                    break;
                case 3 :
                    perkalianMatriks();
                    break;
                case 4 :
                    transposeMatriks();
                    break;
                case 5 :
                	InverseMatriks();
                    break;
                case 6 :
                	determinan_matriks();
                	break;
                case 7 :
                    penjumlahan_matriks();
                    break;
                case 8 :
                    pengurangan_matriks();
                    break;
                	
            }
            System.out.println("");
            System.out.println("Masukkan 0 untuk keluar dari program");
            System.out.println("Masukkan 1 untuk melanjutkan program");
            choice = S.nextInt();
            //choice = (int) S.nextFloat();
            if (choice == 0) {
                stop=true;
            }
        }
        while (stop == false);
        S.close();
        System.out.println("\n  Terimakasih !");
    }
}
