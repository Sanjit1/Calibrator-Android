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
    public int R1;
    public int R2;
    public int R3;
    public int T1;
    public int T2;
    public int T3;
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
        R1 = (Integer.parseInt(Res1.getText().toString()));
        R2 = (Integer.parseInt(Res2.getText().toString()));
        R3 = (Integer.parseInt(Res3.getText().toString()));
        T1 = (Integer.parseInt(Tem1.getText().toString()));
        T2 = (Integer.parseInt(Tem2.getText().toString()));
        T3 = (Integer.parseInt(Tem3.getText().toString()));

        //Do Some cool Math to get ABC
        double []ABC = coolMathGames(R1,R2,R3,T1,T2,T3);
        A=ABC[0];
        B=ABC[1];
        C=ABC[2];
        Atv.setText("A = "+ A+"");
        Btv.setText("B = "+ B+"");
        Ctv.setText("C = "+ C+"");
    }


        //M1*M2 = M3 And M2 = M3*(M1/d)
        /** ************My sad attempt at doing Matrices
        double [][] M1 = {
                {1,ln(R1),cb(ln(R1))},
                {1,ln(R2),cb(ln(R2))},
                {1,ln(R3),cb(ln(R3))}
        };
        double [] M3 = {1/T1,1/T2,1/T3};
        //                       A1  *(    B2   *    C3   -   C2    *   B3   )  +   B1    *(    C2   *   A3    -    C3   *    A2  )  +    C1   *(    A2   *   B3    -    B2   *    A3   )
        double det = M1[0][0]*((M1[1][1]*M1[2][2])-(M1[1][2]*M1[2][1])) + M1[0][1]*((M1[1][2]*M1[2][0])-(M1[2][2]*M1[1][0])) + M1[0][2]*((M1[1][0]*M1[2][1])-(M1[1][1]*M1[0][2]));
        double [][]M1Inv = {
                {(M1[0][0]/det),(M1[0][1]/det),(M1[0][2]/det)},
                {(M1[1][0]/det),(M1[1][1]/det),(M1[1][2]/det)},
                {(M1[2][0]/det),(M1[2][1]/det),(M1[2][2]/det)}
        };

        double [] tr = {
                M1Inv[0][0]*M3[0]+M1Inv[0][1]*M3[1]+M1Inv[0][2]*M3[2],
                M1Inv[1][0]*M3[0]+M1Inv[1][1]*M3[1]+M1Inv[1][2]*M3[2],
                M1Inv[2][0]*M3[0]+M1Inv[2][1]*M3[1]+M1Inv[2][2]*M3[2]};

                Ends here ************ **/
        // Using Wikipedia
        public double[] coolMathGames(int R1,int R2,int R3,int T1,int T2,int T3){




        return  tr;
    }


    public double ln(double numb){
        return Math.log(numb)/Math.log(Math.E);
    }

    public double cb(double numb){
        return numb*numb*numb;
    }

}
