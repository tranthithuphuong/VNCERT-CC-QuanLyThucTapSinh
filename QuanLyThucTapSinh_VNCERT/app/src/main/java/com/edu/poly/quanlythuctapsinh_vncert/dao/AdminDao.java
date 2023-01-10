package com.edu.poly.quanlythuctapsinh_vncert.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.poly.quanlythuctapsinh_vncert.dbhelper.DBHelper;
import com.edu.poly.quanlythuctapsinh_vncert.objects.Admin;

import java.util.ArrayList;

public class AdminDao {
    private SQLiteDatabase db;

    public AdminDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Admin> getData(String sql, String ...selection) {
        ArrayList<Admin> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Admin admin = new Admin();
                admin.setMaTT(cursor.getString(0));
                admin.setHoTenTT(cursor.getString(1));
                admin.setMatKhau(cursor.getString(2));
                list.add(admin);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<Admin> getAll() {
        ArrayList<Admin> list =  new ArrayList<>();
        list = getData("SELECT * FROM Admin");
        return list;
    }

    public Admin getOne(String id) {
        ArrayList<Admin> list = new ArrayList<>();
        list = getData("SELECT * FROM Admin WHERE maTT = ?", id);

        return list.get(0);
    }

    public long insert(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("maTT", admin.getMaTT());
        values.put("hoTen", admin.getHoTenTT());
        values.put("matKhau", admin.getMatKhau());
        return db.insert("Admin", null, values);
    }

    public int update(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("hoTen", admin.getHoTenTT());
        values.put("matKhau", admin.getMatKhau());
        return db.update("Admin", values, "maTT = ?", new String[] {admin.getMaTT()});
    }

    public int delete(String id) {
        return db.delete("Admin", "maTT = ?", new String[] {id});
    }


    // kiểm tra xem có tài khoản mật khẩu không
    public int checkLogin(String username, String password) {
        String sql = "SELECT * FROM Admin WHERE maTT = ? AND matKhau = ?";
        ArrayList<Admin> list = getData(sql, username, password);

        if(list.size() == 0) {
            return -1; // không có tài khoản mật khẩu
        }

        return 1;
    }


    // nếu đúng username và sai password
    public int checkPassword(String username, String password) {
        String sql = "SELECT * FROM Admin WHERE maTT = ? AND matKhau != ?";
        ArrayList<Admin> list = getData(sql, username, password);

        if(list.size() == 0) {
            return 1; // Mật khẩu đúng
        }

        return -1;
    }

    // kiểm tra xem đã có username chưa
    public int checkUserName(String username) {
        String sql = "SELECT * FROM Admin WHERE maTT = ?";
        ArrayList<Admin> list = getData(sql, username);

        if(list.size() == 0) {
            return -1; // maTT chứ tồn tại
        }

        return 1;
    }
}
