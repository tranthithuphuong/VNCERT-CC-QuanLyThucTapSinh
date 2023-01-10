package com.edu.poly.quanlythuctapsinh_vncert.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edu.poly.quanlythuctapsinh_vncert.R;
import com.edu.poly.quanlythuctapsinh_vncert.adapter.ThanhVienAdapter;
import com.edu.poly.quanlythuctapsinh_vncert.custom_toast.CustomToast;
import com.edu.poly.quanlythuctapsinh_vncert.dao.ThanhVienDao;
import com.edu.poly.quanlythuctapsinh_vncert.doi_tuong_Interface.ObjectInterface;
import com.edu.poly.quanlythuctapsinh_vncert.objects.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanhVienFragment extends Fragment implements ObjectInterface {

    private RecyclerView rcvThanhVien;
    private FloatingActionButton fabThanhVien;
    private ArrayList<ThanhVien> list;
    private ThanhVienDao dao;
    private ThanhVien thanhVien;
    private ThanhVienAdapter adapter;

    public static ThanhVienFragment newInstance() {
        ThanhVienFragment fragment = new ThanhVienFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabThanhVien = view.findViewById(R.id.fab_thanh_vien);
        rcvThanhVien = view.findViewById(R.id.rcv_thanh_vien);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvThanhVien.setLayoutManager(layoutManager);

        dao = new ThanhVienDao(getActivity());
        list = dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this);
        adapter.setData(list);
        adapter.setTypeAnimation(0);
        rcvThanhVien.setAdapter(adapter);

        fabThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpenDialog(getActivity(), 0);
                adapter.setTypeAnimation(1);
            }
        });

        thanhVien = new ThanhVien();

    }

    @Override
    public void onClickDelete(Object object) {
        thanhVien = (ThanhVien) object;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn xóa hay không ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(thanhVien.getId() + "");
                restartAdapter();
                adapter.setTypeAnimation(1);
                CustomToast.showMessage(getActivity(), "Xóa thành công");
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onpenDialog(getActivity(), 1);
            }
        });

        builder.show();
    }


    @Override
    public void onClickUpdate(Object object) {
        thanhVien = (ThanhVien) object;
        onpenDialog(getActivity(), 1);
        adapter.setTypeAnimation(1);
    }


    private void onpenDialog(Context context, int type) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_item_thanh_vien_dialog);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvId = dialog.findViewById(R.id.tv_id);
        EditText edName = dialog.findViewById(R.id.ed_name);
        EditText edNumberPhone = dialog.findViewById(R.id.ed_numberphone);
        EditText edGmail = dialog.findViewById(R.id.ed_gmail);
        EditText edAddress = dialog.findViewById(R.id.ed_address);
        RadioButton rdoNam = dialog.findViewById(R.id.rdo_nam);
        RadioButton rdoNu = dialog.findViewById(R.id.rdo_nu);
        Button btnSave = dialog.findViewById(R.id.btn_send);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        tvTitle.setText("Thêm thành viên");
        tvId.setVisibility(View.GONE);

        if (type != 0) {
            tvTitle.setText("Sửa thành viên");
            tvId.setVisibility(View.VISIBLE);
            tvId.setText("Mã thành viên: " + thanhVien.getId());
            edName.setText(thanhVien.getName());
            edNumberPhone.setText(thanhVien.getNumberphone());
            edGmail.setText(thanhVien.getGmail());
            edAddress.setText(thanhVien.getAddress());
            // kiểm tra giới tính
            if (thanhVien.getGender().equalsIgnoreCase("Nam")) {
                rdoNam.setChecked(true);
            } else {
                rdoNu.setChecked(true);
            }
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set dữ liệu vào trong đối tượng khi nhấp save
                String strName = edName.getText().toString().trim();
                String strNumberPhone = edNumberPhone.getText().toString().trim();
                String strGmail = edGmail.getText().toString().trim();
                String strAddress = edAddress.getText().toString().trim();
                thanhVien.setName(strName);
                thanhVien.setNumberphone(strNumberPhone);
                thanhVien.setGmail(strGmail);
                thanhVien.setAddress(strAddress);

                if (rdoNam.isChecked()) {
                    thanhVien.setGender("Nam");
                } else {
                    thanhVien.setGender("Nữ");
                }

                if (validate(thanhVien)) {

                    if (type == 0) { // thêm
                        if (dao.insert(thanhVien) > 0) {
                            CustomToast.showMessage(getActivity(), "Thêm thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Thêm thất bại!");
                        }
                    } else { // sửa
                        if (dao.update(thanhVien) > 0) {
//                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            CustomToast.showMessage(getActivity(), "Sửa thành công");
                        } else {
                            CustomToast.showMessage(getActivity(), "Sửa thất bại!");
                        }
                    }

                    dialog.dismiss();
                    restartAdapter(); // cập nhật lại dữ liệu
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validate(ThanhVien thanhVien) {
        String strTenTv = thanhVien.getName();
        String strSdt = thanhVien.getNumberphone();
        String strGmail = thanhVien.getGmail();
        String strAddress = thanhVien.getAddress();
        if (strTenTv.isEmpty() || strSdt.isEmpty() || strGmail.isEmpty() || strAddress.isEmpty()) {
            CustomToast.showMessage(getActivity(), "Mời nhập đầu đủ dữ liệu!");
            return false;
        }
        return true;
    }

    public void restartAdapter() {
        list = dao.getAll();
        adapter.setData(list);
    }
}