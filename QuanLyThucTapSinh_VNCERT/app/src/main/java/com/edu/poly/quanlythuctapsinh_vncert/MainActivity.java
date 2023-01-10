package com.edu.poly.quanlythuctapsinh_vncert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.edu.poly.quanlythuctapsinh_vncert.dao.AdminDao;
import com.edu.poly.quanlythuctapsinh_vncert.fragment.DoiMatKhauFragment;
import com.edu.poly.quanlythuctapsinh_vncert.fragment.TaoTaiKhoanFragment;
import com.edu.poly.quanlythuctapsinh_vncert.fragment.ThanhVienFragment;
import com.edu.poly.quanlythuctapsinh_vncert.objects.Admin;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private View mHeaderView; // Đại diện cho một view con trong thẻ
    private TextView tvNameHeader;
    private AdminDao adaminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.id_drawerLayout);
        toolbar = findViewById(R.id.id_toolbar);
        navView = findViewById(R.id.id_navView);


        // liên kết toolbar với drawer layout
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();

        // Thiết lập màu cho các icon
        navView.setItemIconTintList(null);

        // sự kiện ấn vào item trong nav
        navView.setNavigationItemSelectedListener(this);


        // Mới đầy chạy ứng dụng thì nhảy vào tạo tài khoản
        setTitle("Quản lý thông tin thực tập sinh");
        replaceFragment(ThanhVienFragment.newInstance());


        // ánh xạ và thao tác với navigationView
        mHeaderView = navView.getHeaderView(0); // lấy header
        // Lấy được headerView rồi thì mới lấy tv trong header
        tvNameHeader = mHeaderView.findViewById(R.id.tv_name_header);

        // có username để lấy ra đối tượng, rồi để lấy ra Name
        String username = getIntent().getStringExtra("user");
        adaminDao = new AdminDao(this);
        Admin admin = adaminDao.getOne(username);
        String nameTT = admin.getHoTenTT();

        tvNameHeader.setText("Welcome " + nameTT + "!");


        navView.getMenu().findItem(R.id.ic_tao_tai_khoan).setVisible(false);
        // Nếu là admin thì mới hiển thị tạo tài khoản
        if(username.equalsIgnoreCase("admin")) {
            navView.getMenu().findItem(R.id.ic_tao_tai_khoan).setVisible(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawer(navView);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ic_thanhvien:
                setTitle("Quản lý thành viên");
                replaceFragment(ThanhVienFragment.newInstance());
                break;
            case R.id.ic_tao_tai_khoan:
                setTitle("Tạo tài khoản");
                replaceFragment(TaoTaiKhoanFragment.newInstance());
                break;
            case R.id.ic_doi_mat_khau:
                setTitle("Đổi mật khẩu");
                replaceFragment(DoiMatKhauFragment.newInstance());
                break;
            case R.id.ic_thoat:
                thoatTaiKhoan();
                break;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(navView);
            }
        },200);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout, fragment);
        transaction.commit();
    }

    public void thoatTaiKhoan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát tài khoản không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(), DangNhapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
