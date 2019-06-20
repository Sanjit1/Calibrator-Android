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

    }

}
