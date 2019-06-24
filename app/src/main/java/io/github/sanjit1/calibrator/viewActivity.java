package io.github.sanjit1.calibrator;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import Jama.Matrix;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;


public class viewActivity extends AppCompatActivity {

    public GraphView graph;
    public AlertDialog dialog;
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
        graph = findViewById(R.id.graph);

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
            name_et.setText(name);
            R1 = Double.parseDouble(arrOfStr[1]);
            Res1.setText(R1+"");
            R2 = Double.parseDouble(arrOfStr[2]);
            Res2.setText(R2+"");
            R3 = Double.parseDouble(arrOfStr[3]);
            Res3.setText(R3+"");
            T1 = Double.parseDouble(arrOfStr[4]);
            Tem1.setText(T1+"");
            T2 = Double.parseDouble(arrOfStr[5]);
            Tem2.setText(T2+"");
            T3 = Double.parseDouble(arrOfStr[6]);
            Tem3.setText(T3+"");
            A  = Double.parseDouble(arrOfStr[7]);
            Atv.setText(A+"");
            B  = Double.parseDouble(arrOfStr[8]);
            Btv.setText(B+"");
            C  = Double.parseDouble(arrOfStr[9]);
            Ctv.setText(C+"");

            enterPressed(graph);


        } catch (IOException e){}// catch (NumberFormatException e){ Log.e("",e.getMessage()+" "+e.getCause());}

    }

    public void testPressed(View v){
        TextView answer = findViewById(R.id.tempTest);
        EditText resText = findViewById(R.id.ResTest);
        if(A<8000) answer.setText(getTemp(Integer.parseInt(resText.getText().toString())));
    }

    public void enterPressed(View v){
        if(A>8000) {
            name = name_et.getText().toString();
            R1 = (Double.parseDouble(Res1.getText().toString()));
            R2 = (Double.parseDouble(Res2.getText().toString()));
            R3 = (Double.parseDouble(Res3.getText().toString()));
            T1 = (Double.parseDouble(Tem1.getText().toString()));
            T2 = (Double.parseDouble(Tem2.getText().toString()));
            T3 = (Double.parseDouble(Tem3.getText().toString()));
        }

        //Do Some cool Math to get ABC
        if(R1!=R2&&R2!=R3&&R1!=R3&&T1!=T2&&T2!=T3&&T1!=T3) {
            double[] ABC = coolMathGames(R1, R2, R3, T1, T2, T3);
            A = ABC[0];
            B = ABC[1];
            C = ABC[2];
            Atv.setText("A = " + A + "");
            Btv.setText("B = " + B + "");
            Ctv.setText("C = " + C + "");
            DataPoint[] data = new DataPoint[800-1];

            for(int i = 1; i<800; i++) {
                data[i - 1] = new DataPoint(100*i, Double.parseDouble(getTemp(100*i)));
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMaxY(100);
            graph.getViewport().setMinY(-20);
            graph.getViewport().setMaxX(80000);
            graph.getViewport().setMinX(0);
            graph.addSeries(series);

        } else {
            Toast.makeText(this,"Temp and/or Res Values are same",Toast.LENGTH_LONG);
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
        return (1/(A + (B*ln(R)) + (C*cb(ln(R))))-273.15)+"";
    }

    public void onSavePressed(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.setView(R.layout.save_dialog_box).show();

    }

    public void saveCalibration(View v){
        try{
            name = name_et.getText().toString();
            R1 = (Double.parseDouble(Res1.getText().toString()));
            R2 = (Double.parseDouble(Res2.getText().toString()));
            R3 = (Double.parseDouble(Res3.getText().toString()));
            T1 = (Double.parseDouble(Tem1.getText().toString()));
            T2 = (Double.parseDouble(Tem2.getText().toString()));
            T3 = (Double.parseDouble(Tem3.getText().toString()));
            File calibFile =  new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+name+".🧪"));
            FileWriter toWrite = new FileWriter(calibFile);
            toWrite.append(name);
            toWrite.append(System.lineSeparator());
            toWrite.append(R1+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(R2+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(R3+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(T1+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(T2+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(T3+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(A+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(B+"");
            toWrite.append(System.lineSeparator());
            toWrite.append(C+"");
            toWrite.append(System.lineSeparator());
            toWrite.flush();
            toWrite.close();

            File ActList = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + "/CalibratorAppData/activities.🧪🧪");
            FileReader activityList = new FileReader(ActList);
            BufferedReader br = new BufferedReader(activityList);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            String fileAsString = sb.toString();
            final String[] arrOfStr = fileAsString.split(System.lineSeparator(), 0);

            boolean itsAlreadyThere = false;
            FileWriter actList = new FileWriter(ActList);
            ArrayList<String> write = new ArrayList<>(Arrays.asList(arrOfStr));
            for (int adder = 0; adder<write.size(); adder++){
                    actList.append(write.get(adder));
                    actList.append(System.lineSeparator());
            }
            if(!Arrays.asList(arrOfStr).contains(name))actList.append(name);
            actList.flush();
            actList.close();
            dialog.dismiss();
        }catch(IOException e){}
    }

    public void dialogDismiss(View v){
        dialog.dismiss();
    }

    public void shareAsString(View v){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ("A = " + A +System.lineSeparator()+"B = " + B +System.lineSeparator()+"C = " + C +System.lineSeparator()));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void shareAsFile(View v){
        try{
            File calibFile =  new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+name+".txt"));
            FileWriter toWrite = new FileWriter(calibFile);
            toWrite.append("Res1 = "+R1);
            toWrite.append(System.lineSeparator());
            toWrite.append("Res2 = "+R2);
            toWrite.append(System.lineSeparator());
            toWrite.append("Res3 = "+R3);
            toWrite.append(System.lineSeparator());
            toWrite.append("Temp1 = "+T1);
            toWrite.append(System.lineSeparator());
            toWrite.append("Temp2 = "+T2);
            toWrite.append(System.lineSeparator());
            toWrite.append("Temp3 = "+T3);
            toWrite.append(System.lineSeparator());
            toWrite.append("A = "+A);
            toWrite.append(System.lineSeparator());
            toWrite.append("B = "+B);
            toWrite.append(System.lineSeparator());
            toWrite.append("C = "+C);
            toWrite.append(System.lineSeparator());
            toWrite.flush();
            toWrite.close();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(calibFile.getAbsolutePath()));
            startActivity(Intent.createChooser(sharingIntent, "Share Calibration File"));
        }catch(IOException e){}
    }

    public double ln(double numb){
        return Math.log(numb)/Math.log(Math.E);
    }
    public double cb(double numb){
        return numb*numb*numb;
    }

}
