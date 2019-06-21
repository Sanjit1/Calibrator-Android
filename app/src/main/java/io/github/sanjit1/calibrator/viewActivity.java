package io.github.sanjit1.calibrator;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import Jama.Matrix;
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

        A = 90000;
        B = 80000;
        C = 60000;

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

    public void testPressed(View v){
        TextView answer = findViewById(R.id.tempTest);
        EditText resText = findViewById(R.id.ResTest);
        if(A<8000) answer.setText(getTemp(Integer.parseInt(resText.getText().toString())));
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
        if(R1!=R2&&R2!=R3&&R1!=R3&&T1!=T2&&T2!=T3&&T1!=T3) {
            double[] ABC = coolMathGames(R1, R2, R3, T1, T2, T3);
            A = ABC[0];
            B = ABC[1];
            C = ABC[2];
            Atv.setText("A = " + A + "");
            Btv.setText("B = " + B + "");
            Ctv.setText("C = " + C + "");
        } else {
            Toast.makeText(this,"Temp or Res Values are same",Toast.LENGTH_LONG);
        }
    }

    public double[] coolMathGames(double R1,double R2,double R3,double T1,double T2,double T3){
        T1 = T1 + 273.15;
        T2 = T2 + 273.15;
        T3 = T3 + 273.15;
        double [][] M1 = {
                {1,ln(R1),cb(ln(R1))},
                {1,ln(R2),cb(ln(R2))},
                {1,ln(R3),cb(ln(R3))}
        };
        double [] M3 = {1/T1,1/T2,1/T3};
        Matrix lhs = new Matrix(M1);
        Matrix rhs = new Matrix(M3,3);
        Matrix toReturn = lhs.solve(rhs);
        double [] tr = {toReturn.get(0,0),toReturn.get(1,0),toReturn.get(2,0)};
        return  tr;
    }

    public String getTemp(double R){
        return (1/(A + (B*ln(R)) + (C*cb(ln(R)))))+"";
    }


    public double ln(double numb){
        return Math.log(numb)/Math.log(Math.E);
    }
    public double cb(double numb){
        return numb*numb*numb;
    }

}
