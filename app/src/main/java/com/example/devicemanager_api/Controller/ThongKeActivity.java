package com.example.devicemanager_api.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.Adapter.listviewAdapter;
//import com.example.devicemanagement.DBHelper.DBChiTietSD;
//import com.example.devicemanagement.DBHelper.DBThietBi;
//import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.R;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeActivity extends AppCompatActivity {
    ImageButton imbBack;
    Button btnChart, btnOutExcel, btnLoc, btnReport;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;

    ArrayList<ChiTietSDEntity> historyList = new ArrayList<>();
    int ngayBD, ngayKT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        setEvent();
        try {
            fillter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        displayHistory();
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

    private void setEvent() {

        getDSChiTiet();
        historyList = chiTietSDs;
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, PieChartActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("ngay_bd", ngayBD);
                bundle.putInt("ngay_kt", ngayKT);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fillter();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                displayHistory();
            }
        });

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOutExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    reportExcel();
                    thongBaoThanhCong(Gravity.CENTER, "Xuất file Excel thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayHistory() {
        ListView lview = (ListView) findViewById(R.id.listviewhuyen);
        listviewAdapter adapter = new listviewAdapter(this, historyList);
        lview.setAdapter(adapter);
    }

    public int getDateSS(String date) {
        date = date.replace("-", "");
        date = date.replace("/", "");
        date = date.replace(":", "");
        date = date.replace(".", "");
        System.out.println(date);
        return Integer.parseInt(date);
    }

//    public String format(java.util.Date dateStr) throws ParseException {
////        dateStr = "Mon Jan 12 00:00:00 IST 2015";
////    dateStr = dateStr.replace("GMT+07:00", "");
////        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss  yyyy");
////        DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
////        return (formatter1.format(formatter.parse(dateStr)));
//        String d = "-2022-05-11";
//        d =dateStr.getYear()+"-"+dateStr.getMonth()+"-" +dateStr.getDay();
//        return d;
//    }

    public void fillter() throws ParseException {
        getDSChiTiet();
//        DBThietBi dbtbi = new DBThietBi(this);
        EditText tvNgayBD = findViewById(R.id.ngyBD);
        EditText tvNgayKT = findViewById(R.id.ngyKT);
        ngayBD = getDateSS(tvNgayBD.getText().toString());
        ngayKT = getDateSS(tvNgayKT.getText().toString());
        ArrayList<ChiTietSDEntity> newHistoryList = new ArrayList<>();
        historyList = chiTietSDs;
        if (historyList != null) {
            for (ChiTietSDEntity ct : historyList) {
                if (getDateSS(ct.getNgaySDForThongKe().trim()) >= ngayBD && getDateSS(ct.getNgaySDForThongKe().trim()) <= ngayKT) {
//                    ct.setMaTB(dbtbi.layTenThietBi(ct.getMaTB()));
//                    ct.setMaTB((ct.getMaTB()));
                    newHistoryList.add(ct);

                }

            }

        }
        historyList = newHistoryList;
    }

    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
        btnChart = findViewById(R.id.btnChart);
        btnOutExcel = findViewById(R.id.btnReport);
        btnLoc = findViewById(R.id.btnFilter);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        EditText tvNgayBD = findViewById(R.id.ngyBD);
        EditText tvNgayKT = findViewById(R.id.ngyKT);
        ngayBD = getDateSS(tvNgayBD.getText().toString());
        ngayKT = getDateSS(tvNgayKT.getText().toString());
        tvNgayBD.setText(date.toString());
        tvNgayKT.setText(date.toString());
    }

    public void reportExcel() throws IOException {
        // Creating the workbook

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Report");
        Row row1 = sheet.createRow(0);
        // Row row2 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Mã Phòng");
        row1.createCell(1).setCellValue("Mã Thiết Bị");
        row1.createCell(2).setCellValue("Ngày Sử Dụng");
        row1.createCell(3).setCellValue("Số Lượng Mượn");
        // row2.createCell(0).setCellValue("test");
        try {
            fillter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int col = historyList.size();

        for (int i = 0; i < col; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(historyList.get(i).getMaPhong());
            row.createCell(1).setCellValue(historyList.get(i).getMaTB());
            row.createCell(2).setCellValue(historyList.get(i).getNgaySDForThongKe());
            row.createCell(3).setCellValue(historyList.get(i).getSoLuongSD());
        }
        String namefile = "thongke-sudung-"+ngayBD+"-"+ngayKT;
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + namefile + ".xlsx");
            workbook.write(file);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void thongBaoThanhCong(int gravity, String text) {
        // xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tbthanhcong);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }
        TextView tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText(text);
        dialog.show();

    }
}