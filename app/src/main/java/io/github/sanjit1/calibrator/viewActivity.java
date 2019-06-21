package io.github.sanjit1.calibrator;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;


public class viewActivity extends AppCompatActivity {


    public TextView Atv;
    public TextView Btv;
    public TextView Ctv;
    public EditText name_et;
    public EditText Res1;
    public EditText Res2;
    public EditText Res3;
    public EditText Tem1;
    public EditText Tem2;
    public EditText Tem3;
    public String name;
    public double R1;
    public double R2;
    public double R3;
    public double T1;
    public double T2;
    public double T3;
    public double A;
    public double B;
    public double C;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_et = findViewById(R.id.Name);
        Res1 = findViewById(R.id.R1);
        Res2 = findViewById(R.id.R2);
        Res3 = findViewById(R.id.R3);
        Tem1 = findViewById(R.id.T1);
        Tem2 = findViewById(R.id.T2);
        Tem3 = findViewById(R.id.T3);
        Atv = findViewById(R.id.A);
        Btv = findViewById(R.id.B);
        Ctv = findViewById(R.id.C);

        File cache = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/cache"));
        try {
            FileReader cacheRead = new FileReader(cache);
            BufferedReader br = new BufferedReader(cacheRead);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            String fileAsString = sb.toString();
            final String[] arrOfStr = fileAsString.split(System.lineSeparator(), 0);


            if(arrOfStr[0]=="New✔✔✔MakeNew"){
                Toast.makeText(this, "Enter name, and Res-Temp values and press Enter", Toast.LENGTH_LONG).show();
            } else {
                File ftl = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+arrOfStr[0]+".🧪"));
                load(ftl);
            }
        }catch(IOException e){}
    }

    void load(File ftl){
        try {
            FileReader cacheRead = new FileReader(ftl);
            BufferedReader br = new BufferedReader(cacheRead);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            String fileAsString = sb.toString();
            final String[] arrOfStr = fileAsString.split(System.lineSeparator(), 0);

            name = arrOfStr[0];
            R1 = Integer.parseInt(arrOfStr[1]);
            R2 = Integer.parseInt(arrOfStr[2]);
            R3 = Integer.parseInt(arrOfStr[3]);
            T1 = Integer.parseInt(arrOfStr[4]);
            T2 = Integer.parseInt(arrOfStr[5]);
            T3 = Integer.parseInt(arrOfStr[6]);
            A = Integer.parseInt(arrOfStr[7]);
            B = Integer.parseInt(arrOfStr[8]);
            C = Integer.parseInt(arrOfStr[9]);
        } catch (IOException e){}

    }

    public void enterPressed(View v){
        name = name_et.getText().toString();
        R1 = (Double.parseDouble(Res1.getText().toString()));
        R2 = (Double.parseDouble(Res2.getText().toString()));
        R3 = (Double.parseDouble(Res3.getText().toString()));
        T1 = (Double.parseDouble(Tem1.getText().toString()));
        T2 = (Double.parseDouble(Tem2.getText().toString()));
        T3 = (Double.parseDouble(Tem3.getText().toString()));

        //Do Some cool Math to get ABC
        double []ABC = coolMathGames(R1,R2,R3,T1,T2,T3);
        A=ABC[0];
        B=ABC[1];
        C=ABC[2];
        Atv.setText("A = "+ A+"");
        Btv.setText("B = "+ B+"");
        Ctv.setText("C = "+ C+"");
    }

    public double[] coolMathGames(double R1,double R2,double R3,double T1,double T2,double T3){
        T1 = T1 + 273.15;
        T2 = T2 + 273.15;
        T3 = T3 + 273.15;
        //M1*M2 = M3 And M2 = M3*(M1/d)
        // /** ************My sad attempt at doing Matrices
        double [][] M1 = {
                {1,ln(R1),cb(ln(R1))},
                {1,ln(R2),cb(ln(R2))},
                {1,ln(R3),cb(ln(R3))}
        };
        double [] M3 = {1/T1,1/T2,1/T3};
        //                       A1  *(    B2   *    C3   -   C2    *   B3   )  +   B1    *(    C2   *   A3    -    C3   *    A2  )  +    C1   *(    A2   *   B3    -    B2   *    A3   )
        //double det = M1[0][0]*((M1[1][1]*M1[2][2])-(M1[1][2]*M1[2][1])) + M1[0][1]*((M1[1][2]*M1[2][0])-(M1[2][2]*M1[1][0])) + M1[0][2]*((M1[1][0]*M1[2][1])-(M1[1][1]*M1[0][2]));
        double det = (M1[0][0]*M1[0][2]*M1[2][2])+(M1[0][1]*M1[1][2]*M1[2][0])+(M1[0][2]*M1[1][0]*M1[1][2])-(M1[0][2]*M1[1][1]*M1[0][2])-(M1[0][1]*M1[1][0]*M1[2][2])-(M1[0][0]*M1[1][2]*M1[2][1]);
        double [][]M1Inv = {
                {(M1[0][0]/det),(M1[0][1]/det),(M1[0][2]/det)},
                {(M1[1][0]/det),(M1[1][1]/det),(M1[1][2]/det)},
                {(M1[2][0]/det),(M1[2][1]/det),(M1[2][2]/det)}
        };

        double [] tr = {
                M1Inv[0][0]*M3[0]+M1Inv[0][1]*M3[1]+M1Inv[0][2]*M3[2],
                M1Inv[1][0]*M3[0]+M1Inv[1][1]*M3[1]+M1Inv[1][2]*M3[2],
                M1Inv[2][0]*M3[0]+M1Inv[2][1]*M3[1]+M1Inv[2][2]*M3[2]};

                // Ends here ************ **
        // Using Wikipedia γ(Gamma) replaced with G

            /**double L1 = ln(R1),L2 = ln(R2), L3 = ln(R3);
            double Y1 = 1 / T1,Y2 = 1 / T2, Y3 = 1 / T3;
            double G2 = ((Y2-Y1)/L2-L1), G3 = ((Y3-Y1)/(L3-L1));
            double C  = (((G3-G2)/(L3-L2)*(1/(L1+L2+L3))));
            double B  = G2 - C * (sq(L1) + (L1*L2) + sq(L2));
            double A  = Y1 - (L1*(B+ sq(L1)*C));
            double[] tr = {A,B,C};**/
        return  tr;
    }


    public double ln(double numb){
        return Math.log(numb)/Math.log(Math.E);
    }

    public double sq(double numb){
            return numb*numb;
    }

    public double cb(double numb){
        return numb*numb*numb;
    }

}
