package io.github.sanjit1.calibrator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get Permissions for Files
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        try {
            File cal = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/CalibratorAppData");
            createDir(cal);
            cal = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/CalibratorAppData/activities.🧪🧪");
            // If the files exist then it will not overwrite, in the createDir function. It is more like a Check and Create Function
            if(!cal.exists()) {
                FileWriter calList = new FileWriter(cal);

                calList.append("Sample");
                calList.append(System.lineSeparator());
                calList.append("Goat");
                calList.flush();
                calList.close();
            }
        }catch (IOException e){}

        LinearLayout parent = findViewById(R.id.parent);
        try {
            FileReader activityList = new FileReader((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + "/CalibratorAppData/activities.🧪🧪");
            BufferedReader br = new BufferedReader(activityList);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            String fileAsString = sb.toString();
            final String[] arrOfStr = fileAsString.split(System.lineSeparator(), 0);


            for (int numb = 1; numb < arrOfStr.length; numb++) {
                final int number = numb;
                CardView layoutHolder = new CardView(this); //CardView to hold Thermistor Calibrator Values
                CardView ref = findViewById(R.id.refCard); //Use a reference card to set some of its properties
                LinearLayout trashAndText = new LinearLayout(this); //Layout to hold Child of the Card
                Button trash = new Button(this); //Button to delete Calibration Data
                trash.setLayoutParams(findViewById(R.id.reference).getLayoutParams()); //Use another reference button to Calibrate the trash button
                trash.setBackground(getDrawable(R.drawable.ic_delete_black_24dp)); //Set Icon
                trash.setGravity(Gravity.END | Gravity.CENTER); //Set Gravity
                trashAndText.setGravity(Gravity.LEFT | Gravity.CENTER); //Set Gravity
                layoutHolder.setLayoutParams(ref.getLayoutParams()); //Use reference button to set Layout Parameter
                TextView name = new TextView(this); //Name of Calibration
                name.setTextSize(24); //Set Text Size
                name.setText(arrOfStr[numb]);
                trashAndText.addView(name); //Add name
                trashAndText.addView(trash); //Add Delete
                layoutHolder.addView(trashAndText); // Add this to the Layout holder
                parent.addView(layoutHolder); // Add The Card to the Parent Layout
                trash.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){
                        try {
                            File activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/activities.🧪🧪"));
                            FileWriter writer = new FileWriter(activityWrite);
                            ArrayList<String> write = new ArrayList<>(Arrays.asList(arrOfStr));
                            for (int adder = 0; adder<write.size(); adder++){
                                if (write.get(adder) != arrOfStr[number]) {
                                    writer.append(write.get(adder));
                                    writer.append(System.lineSeparator());
                                }
                            }
                            writer.flush();
                            writer.close();
                            activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+arrOfStr[number]+".🧪"));
                            activityWrite.delete();
                            activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+arrOfStr[number]+".data🧪"));
                            activityWrite.delete();
                            onRestart();
                        }
                        catch(IOException e){}
                    }

                });
                layoutHolder.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){
                        File cache = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/cache"));

                        try{
                            FileWriter writer = new FileWriter(cache);
                            writer.append(arrOfStr[number]);
                            writer.flush();
                            writer.close();
                            Intent myIntent = new Intent(getApplicationContext(),
                                    viewActivity.class);
                            startActivity(myIntent);
                        } catch (IOException e){}

                    }

        });

    }


        }catch(IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    void goCalibrate(View view){
        File cache = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/cache"));

        try{
            FileWriter writer = new FileWriter(cache);
            writer.append("New✔✔✔MakeNew");
            writer.flush();
            writer.close();
            Intent myIntent = new Intent(getApplicationContext(),
                    viewActivity.class);
            startActivity(myIntent);
        } catch (IOException e){}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);

        LinearLayout parent = findViewById(R.id.parent);
        try {
            File cal = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/CalibratorAppData");
            createDir(cal);
            cal = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/CalibratorAppData/activities.🧪🧪");
            // If the files exist then it will not overwrite, in the createDir function. It is more like a Check and Create Function
            if(!cal.exists()) {
                FileWriter calList = new FileWriter(cal);

                calList.append("Sample");
                calList.append(System.lineSeparator());
                calList.append("Goat");
                calList.flush();
                calList.close();
            }
        }catch (IOException e){}

        //LinearLayout parent1 = findViewById(R.id.parent);
        try {
            FileReader activityList = new FileReader((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + "/CalibratorAppData/activities.🧪🧪");
            BufferedReader br = new BufferedReader(activityList);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            String fileAsString = sb.toString();
            final String[] arrOfStr = fileAsString.split(System.lineSeparator(), 0);


            for (int numb = 1; numb < arrOfStr.length; numb++) {
                final int number = numb;
                CardView layoutHolder = new CardView(this); //CardView to hold Thermistor Calibrator Values
                CardView ref = findViewById(R.id.refCard); //Use a reference card to set some of its properties
                LinearLayout trashAndText = new LinearLayout(this); //Layout to hold Child of the Card
                Button trash = new Button(this); //Button to delete Calibration Data
                trash.setLayoutParams(findViewById(R.id.reference).getLayoutParams()); //Use another reference button to Calibrate the trash button
                trash.setBackground(getDrawable(R.drawable.ic_delete_black_24dp)); //Set Icon
                trash.setGravity(Gravity.END | Gravity.CENTER); //Set Gravity
                trashAndText.setGravity(Gravity.LEFT | Gravity.CENTER); //Set Gravity
                layoutHolder.setLayoutParams(ref.getLayoutParams()); //Use reference button to set Layout Parameter
                TextView name = new TextView(this); //Name of Calibration
                name.setTextSize(24); //Set Text Size
                name.setText(arrOfStr[numb]);
                trashAndText.addView(name); //Add name
                trashAndText.addView(trash); //Add Delete
                layoutHolder.addView(trashAndText); // Add this to the Layout holder
                parent.addView(layoutHolder); // Add The Card to the Parent Layout
                trash.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){
                        try {
                            File activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/activities.🧪🧪"));
                            FileWriter writer = new FileWriter(activityWrite);
                            ArrayList<String> write = new ArrayList<>(Arrays.asList(arrOfStr));
                            for (int adder = 0; adder<write.size(); adder++){
                                if (write.get(adder) != arrOfStr[number]) {
                                    writer.append(write.get(adder));
                                    writer.append(System.lineSeparator());
                                }
                            }
                            writer.flush();
                            writer.close();
                            activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+arrOfStr[number]+".🧪"));
                            activityWrite.delete();
                            activityWrite = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/"+arrOfStr[number]+".data🧪"));
                            activityWrite.delete();
                            onRestart();
                        }
                        catch(IOException e){}
                    }

                });
                layoutHolder.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){
                        File cache = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + ("/CalibratorAppData/cache"));

                        try{
                            FileWriter writer = new FileWriter(cache);
                            writer.append(arrOfStr[number]);
                            writer.flush();
                            writer.close();
                            Intent myIntent = new Intent(getApplicationContext(),
                                    viewActivity.class);
                            startActivity(myIntent);
                        } catch (IOException e){}

                    }

                });

            }


        }catch(IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void createDir(File scout){
        if (!scout.exists() && !scout.isDirectory()) {
            // create empty directory
            if (scout.mkdirs()) {
                Log.i("CreateDir", "App dir created");
            }
        } else {
            Log.i("CreateDir", "App dir already exists");
        }
    }

    public void Save(File root, String childName){
        try
        {
            if (!root.exists()) {
                Log.d("",root.mkdirs()?"Made":"Failed");
            }
            File file2 = new File(root, childName);
            FileWriter writer = new FileWriter(file2);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            Log.e("",e.getMessage());

        }
    }

}
