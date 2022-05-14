package com.example.devicemanager_api.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class listviewAdapter extends BaseAdapter {

    public ArrayList<ChiTietSDEntity> CTSDList;
    Activity activity;

    public listviewAdapter(Activity activity, ArrayList<ChiTietSDEntity> CTSDList) {
        super();
        this.activity = activity;
        this.CTSDList = CTSDList;
    }

    @Override
    public int getCount() {
        return CTSDList.size();
    }

    @Override
    public Object getItem(int position) {
        return CTSDList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView sttu;
        TextView ngaySuDung;
        TextView tenThietBi;
        TextView soluong;
    }
//
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_row_huyen, null);
            holder = new ViewHolder();
            holder.sttu = (TextView) convertView.findViewById(R.id.sNo);
            holder.ngaySuDung = (TextView) convertView.findViewById(R.id.dateuse);
            holder.tenThietBi = (TextView) convertView
                    .findViewById(R.id.deviceid);
            holder.soluong = (TextView) convertView.findViewById(R.id.qnty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChiTietSDEntity item = CTSDList.get(position);
        holder.sttu.setText((item.getNgaySDForThongKe()));
        holder.ngaySuDung.setText(item.getMaTB().toString());
        holder.tenThietBi.setText(item.getMaPhong().toString());
        holder.soluong.setText(""+item.getSoLuongSD());

        return convertView;
    }
}