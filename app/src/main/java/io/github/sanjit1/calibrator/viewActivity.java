package io.github.sanjit1.calibrator;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class viewActivity extends AppCompatActivity {

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

}
