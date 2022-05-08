package com.example.devicemanager_api.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.devicemanager_api.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();
    TaiKhoanFragmentActivity taiKhoanFragmentActivity = new TaiKhoanFragmentActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();

        setEvent();
    }

    private void setEvent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, homeFragmentActivity).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btnTrangChu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, homeFragmentActivity).commit();
                        return true;
                    case R.id.btnTaiKhoan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, taiKhoanFragmentActivity).commit();
                        return true;
                }

                return false;
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.btnViewTrangChu);
    }
}