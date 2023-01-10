package com.edu.poly.quanlythuctapsinh_vncert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.edu.poly.quanlythuctapsinh_vncert.adapter.PhotoAdapter;
import com.edu.poly.quanlythuctapsinh_vncert.custom_toast.CustomToast;
import com.edu.poly.quanlythuctapsinh_vncert.dao.AdminDao;
import com.edu.poly.quanlythuctapsinh_vncert.objects.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    // Bên giao diện slide show
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> list;
    private Timer timer;

    // Thuộc tính chức năng
    private Button btnDangNhap;
    private EditText edUserName, edPassWord;
    private TextView tvLoiUserName, tvLoiPassWord;
    private CheckBox chkGhiNho;
    private AdminDao dao;
    private SharedPreferences prefer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        viewPager = findViewById(R.id.viewpager_photo);
        circleIndicator = findViewById(R.id.circle_indicatior);
        btnDangNhap = findViewById(R.id.btn_dang_nhap);
        edUserName = findViewById(R.id.edUserName);
        edPassWord = findViewById(R.id.edPassWord);
        chkGhiNho = findViewById(R.id.chkGhiNho);
        tvLoiUserName = findViewById(R.id.tv_loi_username);
        tvLoiPassWord = findViewById(R.id.tv_loi_password);

        tvLoiUserName.setText("");
        tvLoiPassWord.setText("");

        dao = new AdminDao(this);
        prefer = getSharedPreferences("USER_FILE", MODE_PRIVATE);


        // Chỉ để chạy auto slide show image
        list = getListPhoto();
        photoAdapter = new PhotoAdapter(this, list);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        // Code chức năng
        btnDangNhap.setOnClickListener(this);


        // Đọc dữ liệu ở trong prefer để hiển thị lên màn hình login
        readPrefer();
    }
    //-----------------------------Code chức năng-------------------------------

    @Override
    public void onClick(View v) { // hàm click của btnDangNhap
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        String userName = edUserName.getText().toString();
        String passUser = edPassWord.getText().toString();
        Boolean status = chkGhiNho.isChecked();

        tvLoiUserName.setText("");
        tvLoiPassWord.setText("");

        if(userName.isEmpty()) {
            tvLoiUserName.setText("Mời nhập tài khoản");
        } else if(passUser.isEmpty()) {
            tvLoiPassWord.setText("Mời nhập mật khẩu");
        } else if(userName.length() < 5) {
            tvLoiUserName.setText("Tối thiểu 5 ký tự");
        } else if(dao.checkUserName(userName) < 0) {
            tvLoiUserName.setText("Tài khoản không tồn tại");
        } else if(dao.checkPassword(userName, passUser) < 0) {
            tvLoiPassWord.setText("Mật khẩu không đúng");
        } else if(dao.checkLogin(userName, passUser) > 0 || userName.equals("admin") && passUser.equals("admin")) {
            writePrefer(userName, passUser, status);
            CustomToast.showMessage(this, "Đăng nhập thành công!");
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            // chuyển userName sang bên mainActivity để thao tác
            intent.putExtra("user", userName);
            startActivity(intent);
            finish();

        }
    }

    public void writePrefer(String userName, String passWord, Boolean status) {
        SharedPreferences.Editor editor = prefer.edit();

        editor.putString("USERNAME", userName);
        editor.putString("PASSWORD", passWord);
        editor.putBoolean("STATUS", status);

        editor.commit();
    }

    public void readPrefer() {
        String userName = prefer.getString("USERNAME", null);
        String passWord = prefer.getString("PASSWORD", null);
        Boolean check = prefer.getBoolean("STATUS", false);

        chkGhiNho.setChecked(check);

        if(userName == null || passWord == null) {
            return;
        }

        if(chkGhiNho.isChecked()) {
            edUserName.setText(userName);
            edPassWord.setText(passWord);
        } else {
            edUserName.setText(userName);
        }
    }

    //----------------------------------------------------------------------
    // Dữ liệu ảnh
    public List<Photo> getListPhoto() {
        List<Photo> listPhoto = new ArrayList<>();

        listPhoto.add(new Photo(R.drawable.logo));
        listPhoto.add(new Photo(R.drawable.logo));
        listPhoto.add(new Photo(R.drawable.logo));
        listPhoto.add(new Photo(R.drawable.logo));

        return listPhoto;
    }

    // Chạy auto image sau khoảng thời gian
    private void autoSlideImage() {
        if(list == null || list.isEmpty() || viewPager == null) {
            return;
        }

        if(timer == null) {
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int index = list.size() - 1;
                        if(currentItem < index) {
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 4000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null) {
            timer = null;
        }
    }
}
