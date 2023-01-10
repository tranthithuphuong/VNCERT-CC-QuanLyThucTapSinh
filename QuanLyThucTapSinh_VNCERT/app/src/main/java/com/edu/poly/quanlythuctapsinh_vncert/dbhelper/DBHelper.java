package com.edu.poly.quanlythuctapsinh_vncert.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "duAnMau";
    private static final int DB_VERSION = 7;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Admin(" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE ThanhVien(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL," +
                "gender TEXT NOT NULL, " +
                "numberphone TEXT NOT NULL, " +
                "gmail TEXT NOT NULL, " +
                "address TEXT NOT NULL)";
        db.execSQL(sql);



        sql = "INSERT INTO Admin(maTT, hoTen, matKhau) VALUES ('admin', 'Admin', 'admin'), ('thuphuong', 'Thu Phương', 'thuphuong0908');";
        db.execSQL(sql);



        sql = "INSERT INTO ThanhVien " +
                "VALUES  (1, 'Nguyễn Văn An', 'Nam', '0981234776', 'an@gmail.com', 'Thanh Hóa')";
        db.execSQL(sql);
        sql = "INSERT INTO ThanhVien " +
                "VALUES  (2, 'Trần Thị Bình', 'Nữ', '0971243212', 'binh@gmail.com', 'Hà Nội')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Admin";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS ThanhVien";
        db.execSQL(sql);
        onCreate(db);
    }
}
