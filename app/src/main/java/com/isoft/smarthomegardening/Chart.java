package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Chart extends AppCompatActivity {
    private static final int TIME_OUT_DRAWING = 1700;
    private static final int TIME_OUT_LOADING = 2000;
    private static final int DATABASE_DELAY_ID = 1500;
    private static final int DATABASE_DELAY_DATA = 1600;

    DatabaseReference reference, referenceid;

    Window window;
    CombinedChart lineChart;

    RadioButton MOI, BAR, UVL;
    Boolean Moisture=true, Barrier=false, UV=false;
    Boolean FormLoad;
    String user, id;

    String currentTime, hourNow;
    String[] hourSp;

    TextView tview;

    Integer mr1=0, mr2=0, mr3=0, mr4=0, mr5=0, mr6=0, mr7=0, mr8=0, mr9=0, mr10=0, mr11=0, mr12=0, mr13=0, mr14=0, mr15=0, mr16=0, mr17=0, mr18=0, mr19=0, mr20=0, mr21=0, mr22=0, mr23=0, mr24=0;
    Integer ar1=0, ar2=0, ar3=0, ar4=0, ar5=0, ar6=0, ar7=0, ar8=0, ar9=0, ar10=0, ar11=0, ar12=0, ar13=0, ar14=0, ar15=0, ar16=0, ar17=0, ar18=0, ar19=0, ar20=0, ar21=0, ar22=0, ar23=0, ar24=0;
    Integer ur1=0, ur2=0, ur3=0, ur4=0, ur5=0, ur6=0, ur7=0, ur8=0, ur9=0, ur10=0, ur11=0, ur12=0, ur13=0, ur14=0, ur15=0, ur16=0, ur17=0, ur18=0, ur19=0, ur20=0, ur21=0, ur22=0, ur23=0, ur24=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        window = Chart.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(3, 20, 20));
        }

        Intent intent = getIntent();
        if(intent.getStringExtra("userc") != null){
            user = intent.getStringExtra("userc");
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.now();

            currentTime = dtf.format(localTime);
            hourSp = currentTime.split(":", 3);
            hourNow = hourSp[0];
        }

        FormLoad=true;

        lineChart = findViewById(R.id.lineChart);
        MOI = findViewById(R.id.MOI);
        BAR = findViewById(R.id.BAR);
        UVL = findViewById(R.id.UVL);
        tview = findViewById(R.id.tview);

        if (FormLoad){
            drawLinesChart();
        }

        new Handler().postDelayed(() -> {
            try {
                referenceid = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
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
            if (FormLoad){
                drawLinesChart();
                FormLoad=false;
            }
        },TIME_OUT_DRAWING);

        new Handler().postDelayed(() -> {
            if(hourNow.equals("00")){
                Record1();
            } else if(hourNow.equals("01")){
                Record1();
                Record2();
            } else if(hourNow.equals("02")){
                Record1();
                Record2();
                Record3();
            } else if(hourNow.equals("03")){
                Record1();
                Record2();
                Record3();
                Record4();
            } else if(hourNow.equals("04")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
            } else if(hourNow.equals("05")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
            } else if(hourNow.equals("06")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
            } else if(hourNow.equals("07")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
            } else if(hourNow.equals("08")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
            } else if(hourNow.equals("9")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
            } else if(hourNow.equals("10")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
            } else if(hourNow.equals("11")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
            } else if(hourNow.equals("12")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
            } else if(hourNow.equals("13")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
            } else if(hourNow.equals("14")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
            } else if(hourNow.equals("15")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
            } else if(hourNow.equals("16")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
            } else if(hourNow.equals("17")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
            } else if(hourNow.equals("18")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
            } else if(hourNow.equals("19")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
                Record19();
                Record20();
            } else if(hourNow.equals("20")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
                Record19();
                Record20();
                Record21();
            } else if(hourNow.equals("21")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
                Record19();
                Record20();
                Record21();
                Record22();
            } else if(hourNow.equals("22")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
                Record19();
                Record20();
                Record21();
                Record22();
                Record23();
            } else if(hourNow.equals("23")){
                Record1();
                Record2();
                Record3();
                Record4();
                Record5();
                Record6();
                Record7();
                Record8();
                Record9();
                Record10();
                Record11();
                Record12();
                Record13();
                Record14();
                Record15();
                Record16();
                Record17();
                Record18();
                Record19();
                Record20();
                Record21();
                Record22();
                Record23();
                Record24();
            }
        },DATABASE_DELAY_DATA);

        new Handler().postDelayed(() -> {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    RealTimeUpdate();
                    drawLinesChart();
                }
            },0,3000);
        },TIME_OUT_LOADING);
    }

    public void tview(View view) {
        drawLinesChart();
        Toast.makeText(getBaseContext(), String.valueOf(hourNow), Toast.LENGTH_SHORT).show();
    }

    private void RealTimeUpdate(){
        if(hourNow.equals("00")){
            Record1();
        } else if(hourNow.equals("01")){
            Record1();
            Record2();
        } else if(hourNow.equals("02")){
            Record1();
            Record2();
            Record3();
        } else if(hourNow.equals("03")){
            Record1();
            Record2();
            Record3();
            Record4();
        } else if(hourNow.equals("04")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
        } else if(hourNow.equals("05")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
        } else if(hourNow.equals("06")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
        } else if(hourNow.equals("07")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
        } else if(hourNow.equals("08")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
        } else if(hourNow.equals("9")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
        } else if(hourNow.equals("10")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
        } else if(hourNow.equals("11")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
        } else if(hourNow.equals("12")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
        } else if(hourNow.equals("13")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
        } else if(hourNow.equals("14")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
        } else if(hourNow.equals("15")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
        } else if(hourNow.equals("16")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
        } else if(hourNow.equals("17")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
        } else if(hourNow.equals("18")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
        } else if(hourNow.equals("19")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
            Record19();
            Record20();
        } else if(hourNow.equals("20")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
            Record19();
            Record20();
            Record21();
        } else if(hourNow.equals("21")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
            Record19();
            Record20();
            Record21();
            Record22();
        } else if(hourNow.equals("22")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
            Record19();
            Record20();
            Record21();
            Record22();
            Record23();
        } else if(hourNow.equals("23")){
            Record1();
            Record2();
            Record3();
            Record4();
            Record5();
            Record6();
            Record7();
            Record8();
            Record9();
            Record10();
            Record11();
            Record12();
            Record13();
            Record14();
            Record15();
            Record16();
            Record17();
            Record18();
            Record19();
            Record20();
            Record21();
            Record22();
            Record23();
            Record24();
        }
    }

    private void drawLinesChart() {
        Legend l = lineChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextColor(Color.WHITE);
        l.setTextSize(14);
        l.setDrawInside(false);

        if(FormLoad) {
            lineChart.animateY(1000);
        }
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getDescription().setText("Data Status Lines");
        lineChart.getDescription().setTextSize(14);
        lineChart.getDescription().setTextColor(Color.WHITE);
        lineChart.setDrawMarkers(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(14);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(true);
        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(35);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);

        CombinedData data = new CombinedData();
        data.setData(generateLineData());

        xAxis.setAxisMaximum(data.getXMax());

        lineChart.setData(data);
        lineChart.invalidate();
    }

    private LineData generateLineData() {
        LineData lineData = new LineData();

        List<Entry> lineEntries1 = DataSet_Moisture();
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1, "Soil Moisture");
        lineDataSet1.setColor(Color.rgb(255,145,0));
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setCircleHoleRadius(2f);
        lineDataSet1.setLineWidth(2f);
        lineDataSet1.setCircleColor(Color.rgb(255,145,0));
        lineDataSet1.setCircleColorHole(Color.WHITE);
        lineDataSet1.setCircleRadius(5f);
        lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet1.setValueTextSize(14f);
        lineDataSet1.setValueTextColor(Color.WHITE);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.GREEN);
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setDrawValues(Moisture);
        lineDataSet1.setValueFormatter(new LargeValueFormatter());
        lineData.addDataSet(lineDataSet1);

        List<Entry> lineEntries2 = DataSet_Attack();
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, "Snail Attack");
        lineDataSet2.setColor(Color.rgb(249,60,26));
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet2.setCircleHoleRadius(2f);
        lineDataSet2.setLineWidth(2f);
        lineDataSet2.setCircleColor(Color.rgb(249,60,26));
        lineDataSet2.setCircleColorHole(Color.WHITE);
        lineDataSet2.setCircleRadius(5f);
        lineDataSet2.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet2.setValueTextSize(14f);
        lineDataSet2.setValueTextColor(Color.WHITE);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.BLUE);
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setDrawValues(Barrier);
        lineDataSet2.setValueFormatter(new LargeValueFormatter());
        lineData.addDataSet(lineDataSet2);

        List<Entry> lineEntries3 = DataSet_UV();
        LineDataSet lineDataSet3 = new LineDataSet(lineEntries3, "UV Light");
        lineDataSet3.setColor(Color.rgb(10,195,18));
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet3.setCircleHoleRadius(2f);
        lineDataSet3.setLineWidth(2f);
        lineDataSet3.setCircleColor(Color.rgb(10,195,18));
        lineDataSet3.setCircleColorHole(Color.WHITE);
        lineDataSet3.setCircleRadius(5f);
        lineDataSet3.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet3.setValueTextSize(14f);
        lineDataSet3.setValueTextColor(Color.WHITE);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.BLUE);
        lineDataSet3.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet3.setDrawValues(UV);
        lineDataSet3.setValueFormatter(new LargeValueFormatter());
        lineData.addDataSet(lineDataSet3);

        return lineData;
    }

    private List<Entry> DataSet_Moisture() {
        List<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, mr1));
        lineEntries.add(new Entry(2, mr2));
        lineEntries.add(new Entry(3, mr3));
        lineEntries.add(new Entry(4, mr4));
        lineEntries.add(new Entry(5, mr5));
        lineEntries.add(new Entry(6, mr6));
        lineEntries.add(new Entry(7, mr7));
        lineEntries.add(new Entry(8, mr8));
        lineEntries.add(new Entry(9, mr9));
        lineEntries.add(new Entry(10, mr10));
        lineEntries.add(new Entry(11, mr11));
        lineEntries.add(new Entry(12, mr12));
        lineEntries.add(new Entry(13, mr13));
        lineEntries.add(new Entry(14, mr14));
        lineEntries.add(new Entry(15, mr15));
        lineEntries.add(new Entry(16, mr16));
        lineEntries.add(new Entry(17, mr17));
        lineEntries.add(new Entry(18, mr18));
        lineEntries.add(new Entry(19, mr19));
        lineEntries.add(new Entry(20, mr20));
        lineEntries.add(new Entry(21, mr21));
        lineEntries.add(new Entry(22, mr22));
        lineEntries.add(new Entry(23, mr23));
        lineEntries.add(new Entry(24, mr24));
        return lineEntries;

    }

    private List<Entry> DataSet_Attack() {
        List<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, ar1));
        lineEntries.add(new Entry(2, ar2));
        lineEntries.add(new Entry(3, ar3));
        lineEntries.add(new Entry(4, ar4));
        lineEntries.add(new Entry(5, ar5));
        lineEntries.add(new Entry(6, ar6));
        lineEntries.add(new Entry(7, ar7));
        lineEntries.add(new Entry(8, ar8));
        lineEntries.add(new Entry(9, ar9));
        lineEntries.add(new Entry(10, ar10));
        lineEntries.add(new Entry(11, ar11));
        lineEntries.add(new Entry(12, ar12));
        lineEntries.add(new Entry(13, ar13));
        lineEntries.add(new Entry(14, ar14));
        lineEntries.add(new Entry(15, ar15));
        lineEntries.add(new Entry(16, ar16));
        lineEntries.add(new Entry(17, ar17));
        lineEntries.add(new Entry(18, ar18));
        lineEntries.add(new Entry(19, ar19));
        lineEntries.add(new Entry(20, ar20));
        lineEntries.add(new Entry(21, ar21));
        lineEntries.add(new Entry(22, ar22));
        lineEntries.add(new Entry(23, ar23));
        lineEntries.add(new Entry(24, ar24));
        return lineEntries;

    }

    private List<Entry> DataSet_UV() {
        List<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, ur1));
        lineEntries.add(new Entry(2, ur2));
        lineEntries.add(new Entry(3, ur3));
        lineEntries.add(new Entry(4, ur4));
        lineEntries.add(new Entry(5, ur5));
        lineEntries.add(new Entry(6, ur6));
        lineEntries.add(new Entry(7, ur7));
        lineEntries.add(new Entry(8, ur8));
        lineEntries.add(new Entry(9, ur9));
        lineEntries.add(new Entry(10, ur10));
        lineEntries.add(new Entry(11, ur11));
        lineEntries.add(new Entry(12, ur12));
        lineEntries.add(new Entry(13, ur13));
        lineEntries.add(new Entry(14, ur14));
        lineEntries.add(new Entry(15, ur15));
        lineEntries.add(new Entry(16, ur16));
        lineEntries.add(new Entry(17, ur17));
        lineEntries.add(new Entry(18, ur18));
        lineEntries.add(new Entry(19, ur19));
        lineEntries.add(new Entry(20, ur20));
        lineEntries.add(new Entry(21, ur21));
        lineEntries.add(new Entry(22, ur22));
        lineEntries.add(new Entry(23, ur23));
        lineEntries.add(new Entry(24, ur24));
        return lineEntries;

    }

    @SuppressLint("NonConstantResourceId")
    public void RadioButtonChecked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.MOI:
                if(checked){
                    Moisture=true;
                    Barrier=false;
                    UV=false;
                    BAR.setChecked(false);
                    UVL.setChecked(false);
                    drawLinesChart();
                }
                break;
            case R.id.BAR:
                if(checked){
                    Moisture=false;
                    Barrier=true;
                    UV=false;
                    MOI.setChecked(false);
                    UVL.setChecked(false);
                    drawLinesChart();
                }
                break;
            case R.id.UVL:
                if(checked){
                    Moisture=false;
                    Barrier=false;
                    UV=true;
                    BAR.setChecked(false);
                    MOI.setChecked(false);
                    drawLinesChart();
                }
                break;
        }
    }

    private void Record1(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record0");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr1 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr1 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar1 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar1 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur1 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur1 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr1 = 0;
            ar1 = 0;
            ur1 = 0;
        }
    }

    private void Record2(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record1");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr2 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr2 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar2 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar2 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur2 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur2 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr2 = 0;
            ar2 = 0;
            ur2 = 0;
        }
    }

    private void Record3(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record2");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr3 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr3 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar3 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar3 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur3 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur3 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr3 = 0;
            ar3 = 0;
            ur3 = 0;
        }
    }

    private void Record4(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record3");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr4 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr4 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar4 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar4 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur4 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur4 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr4 = 0;
            ar4 = 0;
            ur4 = 0;
        }
    }

    private void Record5(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record4");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr5 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr5 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar5 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar5 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur5 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur5 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr5 = 0;
            ar5 = 0;
            ur5 = 0;
        }
    }

    private void Record6(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record5");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr6 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr6 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar6 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar6 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur6 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur6 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr6 = 0;
            ar6 = 0;
            ur6 = 0;
        }
    }

    private void Record7(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record6");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr7 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr7 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar7 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar7 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur7 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur7 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr7 = 0;
            ar7 = 0;
            ur7 = 0;
        }
    }

    private void Record8(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record7");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr8 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr8 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar8 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar8 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur8 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur8 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr8 = 0;
            ar8 = 0;
            ur8 = 0;
        }
    }

    private void Record9(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record8");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr9 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr9 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar9 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar9 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur9 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur9 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr9 = 0;
            ar9 = 0;
            ur9 = 0;
        }
    }

    private void Record10(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record9");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr10 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr10 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar10 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar10 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur10 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur10 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr10 = 0;
            ar10 = 0;
            ur10 = 0;
        }
    }

    private void Record11(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record10");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr11 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr11 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar11 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar11 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur11 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur11 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr11 = 0;
            ar11 = 0;
            ur11 = 0;
        }
    }

    private void Record12(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record11");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr12 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr12 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar12 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar12 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur12 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur12 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr12 = 0;
            ar12 = 0;
            ur12 = 0;
        }
    }

    private void Record13(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record12");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr13 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr13 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar13 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar13 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur13 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur13 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr13 = 0;
            ar13 = 0;
            ur13 = 0;
        }
    }

    private void Record14(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record13");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr14 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr14 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar14 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar14 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur14 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur14 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr14 = 0;
            ar14 = 0;
            ur14 = 0;
        }
    }

    private void Record15(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record14");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr15 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr15 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar15 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar15 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur15 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur15 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr15 = 0;
            ar15 = 0;
            ur15 = 0;
        }
    }

    private void Record16(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record15");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr16 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr16 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar16 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar16 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur16 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur16 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr16 = 0;
            ar16 = 0;
            ur16 = 0;
        }
    }

    private void Record17(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record16");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr17 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr17 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar17 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar17 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur17 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur17 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr17 = 0;
            ar17 = 0;
            ur17 = 0;
        }
    }

    private void Record18(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record17");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr18 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr18 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar18 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar18 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur18 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur18 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr18 = 0;
            ar18 = 0;
            ur18 = 0;
        }
    }

    private void Record19(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record18");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr19 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr19 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar19 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar19 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur19 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur19 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr19 = 0;
            ar19 = 0;
            ur19 = 0;
        }
    }

    private void Record20(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record19");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr20 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr20 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar20 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar20 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur20 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur20 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr20 = 0;
            ar20 = 0;
            ur20 = 0;
        }
    }

    private void Record21(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record20");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr21 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr21 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar21 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar21 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur21 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur21 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr21 = 0;
            ar21 = 0;
            ur21 = 0;
        }
    }

    private void Record22(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record21");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr22 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr22 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar22 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar22 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur22 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur22 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr22 = 0;
            ar22 = 0;
            ur22 = 0;
        }
    }

    private void Record23(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record22");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr23 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr23 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar23 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar23 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur23 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur23 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr23 = 0;
            ar23 = 0;
            ur23 = 0;
        }
    }

    private void Record24(){
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("PotDataFromID").child(id).child("Plant_Pot_01").child("Report").child("Record23");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.child("SoilLevel_Average").getValue() != null ){
                        mr24 = Integer.parseInt(snapshot.child("SoilLevel_Average").getValue().toString());
                    } else {
                        mr24 = 0;
                    }

                    if(snapshot.child("Snail_Count").getValue() != null ){
                        ar24 = Integer.parseInt(snapshot.child("Snail_Count").getValue().toString());
                    } else {
                        ar24 = 0;
                    }

                    if(snapshot.child("UVlevel_Average").getValue() != null ){
                        ur24 = Integer.parseInt(snapshot.child("UVlevel_Average").getValue().toString());
                    } else {
                        ur24 = 0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            mr24 = 0;
            ar24 = 0;
            ur24 = 0;
        }
    }
}