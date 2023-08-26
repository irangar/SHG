package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class ControlView extends AppCompatActivity {
    private static final int TIME_OUT_LOADING = 2000;
    private static final int DATABASE_DELAY_ID = 600;
    private static final int DATABASE_DELAY_DATA = 1000;

    DatabaseReference reference, referenceid;

    Switch sw1, sw2, sw3;
    TextView ml, uvL, uvS, bl, at, bh, ad, online, date, potName;
    GifImageView pot;
    ImageView pot_off;
    LinearLayout moisture, uvLight;
    ProgressBar progressBarCon;

    Integer Ms, Bs, Us, timeCount=0;
    String userN, id, today, oldOnline = "0";
    Drawable imgOnline;

    Boolean clicksw, autoOff=false, backOnline=false, backOffline=true, formload=true, chartView=false;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_view);

        window = com.isoft.smarthomegardening.ControlView.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(225, 244, 224));
        }

        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);

        ml = findViewById(R.id.ml);
        uvL = findViewById(R.id.uv_level);
        uvS = findViewById(R.id.uv_status);
        bl = findViewById(R.id.battery_level);
        at = findViewById(R.id.attack);
        bh = findViewById(R.id.batteryhours);
        ad = findViewById(R.id.attackdays);
        potName = findViewById(R.id.potName);
        online = findViewById(R.id.online);
        date = findViewById(R.id.date);
        pot = findViewById(R.id.pot);
        pot_off = findViewById(R.id.pot_off);
        moisture = findViewById(R.id.moisture);
        uvLight = findViewById(R.id.uv_light);
        progressBarCon = findViewById(R.id.progressBarCon);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        //int year = c.get(Calendar.YEAR);
        if((month+1)==1){
            today = "(Jan " + day + ")";
        } else if((month+1)==2){
            today = "(Feb " + day + ")";
        } else if((month+1)==3){
            today = "(Mar " + day + ")";
        } else if((month+1)==4){
            today = "(Apr " + day + ")";
        } else if((month+1)==5){
            today = "(May " + day + ")";
        } else if((month+1)==6){
            today = "(Jun " + day + ")";
        } else if((month+1)==7){
            today = "(Jul " + day + ")";
        } else if((month+1)==8){
            today = "(Aug " + day + ")";
        } else if((month+1)==9){
            today = "(Sep " + day + ")";
        } else if((month+1)==10){
            today = "(Oct " + day + ")";
        } else if((month+1)==11){
            today = "(Nov " + day + ")";
        } else if((month+1)==12){
            today = "(Dec " + day + ")";
        }
        date.setText(today);

        Intent intent = getIntent();
        if(intent.getStringExtra("LUser") == null){
            userN = intent.getStringExtra("SUser");
        } else {
            userN = intent.getStringExtra("LUser");
        }
        progressBarCon.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            try {
                referenceid = FirebaseDatabase.getInstance().getReference().child("Users").child(userN);
                referenceid.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("id").getValue() != null ){
                            id = snapshot.child("id").getValue().toString();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Data Error", Toast.LENGTH_SHORT).show();
            }

        },DATABASE_DELAY_ID);

        new Handler().postDelayed(() -> {
            progressBarCon.setVisibility(View.INVISIBLE);
            try {
                reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(formload){
                            if(snapshot.child("isOnline").getValue() != null ){
                                if(!snapshot.child("isOnline").getValue().toString().equals(oldOnline)){
                                    online.setText("Online");
                                    online.setTextColor(Color.rgb(0,150,136));
                                    oldOnline = snapshot.child("isOnline").getValue().toString();
                                    imgOnline = getBaseContext().getResources().getDrawable(R.drawable.online);
                                    sw2.setEnabled(true);
                                    if(backOffline){
                                        Toast.makeText(getBaseContext(), "Back to Online", Toast.LENGTH_SHORT).show();
                                        backOnline=true;
                                        backOffline=false;
                                    }
                                    if(autoOff){
                                        if (snapshot.child("optacouplarPWR").getValue() !=null) {
                                            reference.child("optacouplarPWR").setValue("1");
                                            sw2.setChecked(true);
                                            pot_off.setVisibility(View.INVISIBLE);
                                            pot.setVisibility(View.VISIBLE);
                                            autoOff=false;
                                        } else {
                                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    online.setText("Offline");
                                    online.setTextColor(Color.RED);
                                    imgOnline = getBaseContext().getResources().getDrawable(R.drawable.offline);
                                    if(backOnline){
                                        Toast.makeText(getBaseContext(), "Pot Offline", Toast.LENGTH_SHORT).show();
                                        backOnline=false;
                                        backOffline=true;
                                    }
                                    if (snapshot.child("optacouplarPWR").getValue() !=null) {
                                        reference.child("optacouplarPWR").setValue("0");
                                        sw2.setChecked(false);
                                        sw2.setEnabled(false);
                                        pot_off.setVisibility(View.VISIBLE);
                                        pot.setVisibility(View.INVISIBLE);
                                        autoOff=true;
                                    } else {
                                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                imgOnline.setBounds(0, 0, 60, 60);
                                online.setCompoundDrawables(imgOnline, null, null, null);
                            } else {
                                Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                            }
                            formload=false;
                        }
                        if(snapshot.child("fc28PWR").getValue() != null ){
                            if(snapshot.child("fc28PWR").getValue().toString().equals("1")){
                                sw1.setChecked(true);
                                moisture.setBackgroundResource(R.drawable.b_g_gradient);
                            }else{
                                sw1.setChecked(false);
                                moisture.setBackgroundResource(R.drawable.b_g_disable);
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("optacouplarPWR").getValue() != null ){
                            if(snapshot.child("optacouplarPWR").getValue().toString().equals("1")){
                                sw2.setChecked(true);
                                pot_off.setVisibility(View.INVISIBLE);
                                pot.setVisibility(View.VISIBLE);
                            }else{
                                sw2.setChecked(false);
                                pot_off.setVisibility(View.VISIBLE);
                                pot.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("s12sdPWR").getValue() != null ){
                            if(snapshot.child("s12sdPWR").getValue().toString().equals("1")){
                                sw3.setChecked(true);
                                uvLight.setBackgroundResource(R.drawable.b_g_gradient);
                            }else{
                                sw3.setChecked(false);
                                uvLight.setBackgroundResource(R.drawable.b_g_disable);
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("Soil_Moisture_Level").getValue() != null ){
                            if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("1")){
                                ml.setText("0.1%");
                            }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("2")){
                                ml.setText("20%");
                            }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("3")){
                                ml.setText("40%");
                            }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("4")){
                                ml.setText("60%");
                            }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("5")){
                                ml.setText("80%");
                            }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("6")){
                                ml.setText("100%");
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("Sunlight_UV_Intensity").getValue() != null ){
                            if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("1")){
                                uvL.setText("0");
                                uvS.setText("(no UV)");
                            }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("2")){
                                uvL.setText("1");
                                uvS.setText("(Low)");
                            }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("3")){
                                uvL.setText("2");
                                uvS.setText("(Medium)");
                            }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("4")){
                                uvL.setText("3");
                                uvS.setText("(Bright)");
                            }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("5")){
                                uvL.setText("4");
                                uvS.setText("(High Bright)");
                            }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("6")){
                                uvL.setText("5");
                                uvS.setText("(Very High)");
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("Battery_Level").getValue() != null ){
                            if(snapshot.child("Battery_Level").getValue().toString().equals("1")){
                                bl.setText("5%");
                            }else if(snapshot.child("Battery_Level").getValue().toString().equals("2")){
                                bl.setText("20%");
                            }else if(snapshot.child("Battery_Level").getValue().toString().equals("3")){
                                bl.setText("40%");
                            }else if(snapshot.child("Battery_Level").getValue().toString().equals("4")){
                                bl.setText("60%");
                            }else if(snapshot.child("Battery_Level").getValue().toString().equals("5")){
                                bl.setText("80%");
                            }else if(snapshot.child("Battery_Level").getValue().toString().equals("6")){
                                bl.setText("100%");
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }

                        if(snapshot.child("Snail_Detector_Count").getValue() != null ){
                            at.setText(snapshot.child("Snail_Detector_Count").getValue().toString());
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Data Error", Toast.LENGTH_SHORT).show();
            }
        },DATABASE_DELAY_DATA);

        new Handler().postDelayed(() -> {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    timeCount ++;
                    chartView = true;
                    RealTimeUpdate();
                }
            },0,3000);
        },TIME_OUT_LOADING);
    }

    public void MoistureClick(View view) {
        clicksw = true;

        if(sw1.isChecked()){
            Ms = 1;
            moisture.setBackgroundResource(R.drawable.b_g_gradient);
        }else {
            Ms = 0;
            moisture.setBackgroundResource(R.drawable.b_g_disable);
        }

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("fc28PWR").getValue() != null) {
                        if(clicksw.equals(true)){
                            reference.child("fc28PWR").setValue(Ms);
                            Toast.makeText(getBaseContext(), "Sensor Toggle Successful", Toast.LENGTH_SHORT).show();
                            clicksw = false;
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Intern Server Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void BarrierClick(View view) {
        clicksw = true;

        if(sw2.isChecked()){
            Bs = 1;
            pot_off.setVisibility(View.INVISIBLE);
            pot.setVisibility(View.VISIBLE);
        }else {
            Bs = 0;
            pot_off.setVisibility(View.VISIBLE);
            pot.setVisibility(View.INVISIBLE);
        }

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("optacouplarPWR").getValue() !=null) {
                        if(clicksw.equals(true)){
                            reference.child("optacouplarPWR").setValue(String.valueOf(Bs));
                            Toast.makeText(getBaseContext(), "Sensor Toggle Successful", Toast.LENGTH_SHORT).show();
                            clicksw = false;
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Intern Server Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void UvClick(View view) {
        clicksw = true;

        if(sw3.isChecked()){
            Us = 1;
            uvLight.setBackgroundResource(R.drawable.b_g_gradient);
        }else {
            Us = 0;
            uvLight.setBackgroundResource(R.drawable.b_g_disable);
        }

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("s12sdPWR").getValue() !=null) {
                        if(clicksw.equals(true)){
                            reference.child("s12sdPWR").setValue(Us);
                            Toast.makeText(getBaseContext(), "Sensor Toggle Successful", Toast.LENGTH_SHORT).show();
                            clicksw = false;
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Intern Server Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void RealTimeUpdate(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(timeCount == 7){
                        if(snapshot.child("isOnline").getValue() != null ){
                            if(!snapshot.child("isOnline").getValue().toString().equals(oldOnline)){
                                online.setText("Online");
                                online.setTextColor(Color.rgb(0,150,136));
                                oldOnline = snapshot.child("isOnline").getValue().toString();
                                imgOnline = getBaseContext().getResources().getDrawable(R.drawable.online);
                                sw2.setEnabled(true);
                                if(backOffline){
                                    Toast.makeText(getBaseContext(), "Back to Online", Toast.LENGTH_SHORT).show();
                                    backOnline=true;
                                    backOffline=false;
                                }
                                if(autoOff){
                                    if (snapshot.child("optacouplarPWR").getValue() !=null) {
                                        reference.child("optacouplarPWR").setValue("1");
                                        sw2.setChecked(true);
                                        pot_off.setVisibility(View.INVISIBLE);
                                        pot.setVisibility(View.VISIBLE);
                                        autoOff=false;
                                    } else {
                                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                online.setText("Offline");
                                online.setTextColor(Color.RED);
                                imgOnline = getBaseContext().getResources().getDrawable(R.drawable.offline);
                                if(backOnline){
                                    Toast.makeText(getBaseContext(), "Pot Offline", Toast.LENGTH_SHORT).show();
                                    backOnline=false;
                                    backOffline=true;
                                }
                                if (snapshot.child("optacouplarPWR").getValue() !=null) {
                                    reference.child("optacouplarPWR").setValue("0");
                                    sw2.setChecked(false);
                                    sw2.setEnabled(false);
                                    pot_off.setVisibility(View.VISIBLE);
                                    pot.setVisibility(View.INVISIBLE);
                                    autoOff=true;
                                } else {
                                    Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                                }
                            }
                            imgOnline.setBounds(0, 0, 60, 60);
                            online.setCompoundDrawables(imgOnline, null, null, null);
                        } else {
                            Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                        }
                        timeCount = 0;
                    }
                    if(snapshot.child("fc28PWR").getValue() != null ){
                        if(snapshot.child("fc28PWR").getValue().toString().equals("1")){
                            sw1.setChecked(true);
                            moisture.setBackgroundResource(R.drawable.b_g_gradient);
                        }else{
                            sw1.setChecked(false);
                            moisture.setBackgroundResource(R.drawable.b_g_disable);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("optacouplarPWR").getValue() != null ){
                        if(snapshot.child("optacouplarPWR").getValue().toString().equals("1")){
                            sw2.setChecked(true);
                            pot_off.setVisibility(View.INVISIBLE);
                            pot.setVisibility(View.VISIBLE);
                        }else{
                            sw2.setChecked(false);
                            pot_off.setVisibility(View.VISIBLE);
                            pot.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("s12sdPWR").getValue() != null ){
                        if(snapshot.child("s12sdPWR").getValue().toString().equals("1")){
                            sw3.setChecked(true);
                            uvLight.setBackgroundResource(R.drawable.b_g_gradient);
                        }else{
                            sw3.setChecked(false);
                            uvLight.setBackgroundResource(R.drawable.b_g_disable);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("Soil_Moisture_Level").getValue() != null ){
                        if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("1")){
                            ml.setText("Very Dry");
                        }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("2")){
                            ml.setText("20%");
                        }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("3")){
                            ml.setText("40%");
                        }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("4")){
                            ml.setText("60%");
                        }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("5")){
                            ml.setText("80%");
                        }else if(snapshot.child("Soil_Moisture_Level").getValue().toString().equals("6")){
                            ml.setText("100%");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("Sunlight_UV_Intensity").getValue() != null ){
                        if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("1")){
                            uvL.setText("0");
                            uvS.setText("(no UV)");
                        }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("2")){
                            uvL.setText("1");
                            uvS.setText("(Low)");
                        }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("3")){
                            uvL.setText("2");
                            uvS.setText("(Medium)");
                        }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("4")){
                            uvL.setText("3");
                            uvS.setText("(Bright)");
                        }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("5")){
                            uvL.setText("4");
                            uvS.setText("(High Bright)");
                        }else if(snapshot.child("Sunlight_UV_Intensity").getValue().toString().equals("6")){
                            uvL.setText("5");
                            uvS.setText("(Very High)");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("Battery_Level").getValue() != null ){
                        if(snapshot.child("Battery_Level").getValue().toString().equals("1")){
                            bl.setText("10%");
                            bh.setText("(08h)");
                        }else if(snapshot.child("Battery_Level").getValue().toString().equals("2")){
                            bl.setText("20%");
                            bh.setText("(22h)");
                        }else if(snapshot.child("Battery_Level").getValue().toString().equals("3")){
                            bl.setText("40%");
                            bh.setText("(48h)");
                        }else if(snapshot.child("Battery_Level").getValue().toString().equals("4")){
                            bl.setText("60%");
                            bh.setText("(72h)");
                        }else if(snapshot.child("Battery_Level").getValue().toString().equals("5")){
                            bl.setText("80%");
                            bh.setText("(120h)");
                        }else if(snapshot.child("Battery_Level").getValue().toString().equals("6")){
                            bl.setText("100%");
                            bh.setText("(168h)");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }

                    if(snapshot.child("Snail_Detector_Count").getValue() != null ){
                        at.setText(snapshot.child("Snail_Detector_Count").getValue().toString());
                        ad.setText("(in 3days)");
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            sw1.setEnabled(false);
            sw2.setEnabled(false);
            sw3.setEnabled(false);
            ml.setText("Null");
            uvL.setText("Null");
            uvS.setText("( )");
            bl.setText("Null");
            at.setText("Null");
        }
    }

    public void reset(View view) {
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Snail_Detector_Count").getValue() != null ){
                        if(Integer.parseInt(snapshot.child("Snail_Detector_Count").getValue().toString()) > 0){
                            reference.child("Snail_Detector_Count").setValue(0);
                            Toast.makeText(getBaseContext(), "Resetting Successful", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception ignored) {

        }

    }

    public void potChanger(View view) {
        if(potName.getText().toString().equals("POT 01")){
            potName.setText("POT 02");
        } else if(potName.getText().toString().equals("POT 02")){
            potName.setText("POT 03");
        } else if(potName.getText().toString().equals("POT 03")){
            potName.setText("POT 01");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ControlView.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void chart(View view) {
        if(chartView){
            Intent intent = new Intent(ControlView.this, Chart.class);
            intent.putExtra("userc",userN);
            startActivity(intent);
        }
    }

    public void tips(View view) {
        Intent intent = new Intent(ControlView.this, Tips.class);
        startActivity(intent);
    }
}