package com.example.devicemanager_api.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
     ArrayList<ChiTietSDEntity> historyList = null;
    public static ArrayList<ThietBiEntity> thietBis;
    ImageButton imbBack;
    Date date;
    private PieChart mChart;
    long millis = System.currentTimeMillis();
    Switch next;
    private static ArrayList<ChiTietSDEntity> listCTSD;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;
    private static int ngayBD=0, ngayKT=99999999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        dbtbi = new DBThietBi(this);
        getDSChiTiet();
        layDSThietBi();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        // get historyList to listCTSD
        Intent intent =  getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            ngayBD = bundle.getInt("ngay_bd",0);
            ngayKT = bundle.getInt("ngay_kt",0);
        }
        fillter();


        setControl();
        setEvent();
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("PieChart");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        addDataSet(mChart);

        mChart.setOnChartValueSelectedListener(this);
    }

    public String format(java.util.Date dateStr) throws ParseException {
        String d = "-2022-05-11";
        d =dateStr.getYear()+"-"+dateStr.getMonth()+"-" +dateStr.getDay();
        return d;
    }

    public void layDSThietBi() {

        ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                thietBis = (ArrayList<ThietBiEntity>) response.body();
            }

            @Override
            public void onFailure(Call<List<ThietBiEntity>> call, Throwable t) {
           }
        });
    }
    private void getDSChiTiet() {
        ChiTietSDAPI.apiChiTietSDService.layDSChiTietSD().enqueue(new Callback<List<ChiTietSDEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
            }
            @Override
            public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {
            }
        });
    }
    private static void addDataSet(PieChart pieChart) {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        HashMap<String,Integer> hm = new HashMap<String,Integer>();


        for (ChiTietSDEntity x:listCTSD) {
            if(!hm.containsKey(x.getMaTB())){
                hm.put(x.getMaTB(), x.getSoLuongSD());
            }
            else
            {
                hm.put(x.getMaTB(), hm.get(x.getMaTB())+x.getSoLuongSD()) ;
            }
        }
        int id = 0;
        for(Map.Entry<String, Integer> entry : hm.entrySet()) {
//            String key = entry.getKey();
            xEntrys.add(entry.getKey());
            yEntrys.add(new PieEntry(entry.getValue(),id++)); ;
        }



        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Thiết Bị Được Mượn");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Tổng mượn: "
                + e.getY()
                + ", Thiết Bị: "
//                +  dbtbi.layTenThietBi(listCTSD.get((int)h.getX()).getMaThietBi())
                +  listCTSD.get((int)h.getX()).getMaTB()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
    private void setEvent() {

//        next.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Intent intent = new Intent(PieChartActivity.this, CombinedChartActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
//        imbBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(PieChartActivity.this, CombinedChartActivity.class);
////                startActivity(intent);
//            }
//        });
    }
    private void setControl() {

        imbBack = findViewById(R.id.imbBack);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public int getDateSS(String date) {
        date = date.replace("-", "");
        date = date.replace("/", "");
        date = date.replace(":", "");
        date = date.replace(".", "");
        return Integer.parseInt(date);
    }

    public void fillter() {
        ArrayList<ChiTietSDEntity> newHistoryList = new ArrayList<>();
        listCTSD = chiTietSDs;
        if (listCTSD != null) {
            for (ChiTietSDEntity ct : listCTSD) {
                 if (getDateSS(ct.getNgaySDForThongKe()) >= ngayBD && getDateSS(ct.getNgaySDForThongKe().trim()) <= ngayKT) {
                     newHistoryList.add(ct);
                 }
            }

        }
        listCTSD = newHistoryList;
    }

}
