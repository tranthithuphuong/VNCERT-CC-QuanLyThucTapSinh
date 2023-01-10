package com.edu.poly.quanlythuctapsinh_vncert.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.poly.quanlythuctapsinh_vncert.dbhelper.DBHelper;
import com.edu.poly.quanlythuctapsinh_vncert.objects.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<ThanhVien> getData(String sql, String ...selection) {
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThanhVien thanhVien = new ThanhVien();

                thanhVien.setId(cursor.getInt(0));
                thanhVien.setName(cursor.getString(1));
                thanhVien.setGender(cursor.getString(2));
                thanhVien.setNumberphone(cursor.getString(3));
                thanhVien.setGmail(cursor.getString(4));
                thanhVien.setAddress(cursor.getString(5));

                list.add(thanhVien);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<ThanhVien> getAll() {
        ArrayList<ThanhVien> list =  new ArrayList<>();
        list = getData("SELECT * FROM ThanhVien");
        return list;
    }

    public ThanhVien getOne(String id) {
        ArrayList<ThanhVien> list = new ArrayList<>();
        list = getData("SELECT * FROM ThanhVien WHERE id = ?", id);

        return list.get(0);
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("name", thanhVien.getName());
        values.put("gender", thanhVien.getGender());
        values.put("numberphone", thanhVien.getNumberphone());
        values.put("gmail", thanhVien.getGmail());
        values.put("address", thanhVien.getAddress());
        return db.insert("ThanhVien", null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("name", thanhVien.getName());
        values.put("gender", thanhVien.getGender());
        values.put("numberphone", thanhVien.getNumberphone());
        values.put("gmail", thanhVien.getGmail());
        values.put("address", thanhVien.getAddress());
        return db.update("ThanhVien", values, "id = ?", new String[] {thanhVien.getId() + ""});
    }

    public int delete(String id) {
        return db.delete("ThanhVien", "id = ?", new String[] {id});
    }
}
