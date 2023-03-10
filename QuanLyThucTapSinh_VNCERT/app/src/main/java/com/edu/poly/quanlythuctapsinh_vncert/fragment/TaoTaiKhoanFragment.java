package com.edu.poly.quanlythuctapsinh_vncert.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.poly.quanlythuctapsinh_vncert.R;
import com.edu.poly.quanlythuctapsinh_vncert.custom_toast.CustomToast;
import com.edu.poly.quanlythuctapsinh_vncert.dao.AdminDao;
import com.edu.poly.quanlythuctapsinh_vncert.objects.Admin;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaoTaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaoTaiKhoanFragment extends Fragment {

    private EditText edAddUser, edAddName, edAddPass, edAddRePass;
    private TextView tvBugAddUser, tvBugAddName, tvBugAddPass, tvBugAddRePass;
    private Button btnSave, btnCancel;
    private AdminDao dao;
    private SharedPreferences pref;

    public static TaoTaiKhoanFragment newInstance() {
        TaoTaiKhoanFragment fragment = new TaoTaiKhoanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tao_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edAddUser = view.findViewById(R.id.ed_add_user);
        edAddName = view.findViewById(R.id.ed_add_name);
        edAddPass = view.findViewById(R.id.ed_add_pass);
        edAddRePass = view.findViewById(R.id.ed_add_re_pass);
        tvBugAddUser = view.findViewById(R.id.tv_bug_add_user);
        tvBugAddName = view.findViewById(R.id.tv_bug_add_name);
        tvBugAddPass = view.findViewById(R.id.tv_bug_add_pass);
        tvBugAddRePass = view.findViewById(R.id.tv_bug_add_re_pass);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        dao = new AdminDao(getActivity());
        pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
                clearBugForm();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        clearBugForm();
    }

    private void addUser() {
        if(validate()) {
            Admin admin = new Admin();
            admin.setMaTT(edAddUser.getText().toString().trim());
            admin.setHoTenTT(edAddName.getText().toString().trim());
            admin.setMatKhau(edAddPass.getText().toString().trim());
            if(dao.insert(admin) > 0) { // n???u nh???p tr??ng id th?? x???y ra l???i, v?? khi ???? kh??ng tr??? v??? index > 0
                CustomToast.showMessage(getActivity(), "T???o t??i kho???n th??nh c??ng");
                clearForm();
            } else {
                CustomToast.showMessage(getActivity(),"T???o t??i kho???n th???t b???i");
            }
        }

    }

    public boolean validate() {
        String strAddUser = edAddUser.getText().toString().trim();
        String strAddName = edAddName.getText().toString().trim();
        String strAddPass = edAddPass.getText().toString().trim();
        String strAddRePass = edAddRePass.getText().toString().trim();

        clearBugForm();

        if(strAddUser.isEmpty()) {
            tvBugAddUser.setText("M???i nh???p d??? li???u");
            return false;
        } else if(strAddName.isEmpty()) {
            tvBugAddName.setText("M???i nh???p d??? li???u");
            return false;
        } else if(strAddPass.isEmpty()) {
            tvBugAddPass.setText("M???i nh???p d??? li???u");
            return false;
        } else if(strAddRePass.isEmpty()) {
            tvBugAddRePass.setText("M???i nh???p d??? li???u");
            return false;
        } else if(strAddUser.length() < 5) {
            tvBugAddUser.setText("T???i thi???u 5 k?? t???");
            return false;
        } else if(!strAddPass.equals(strAddRePass)) {
            tvBugAddRePass.setText("M???t kh???u kh??ng tr??ng kh???p");
            return false;
        } else if(dao.checkUserName(strAddUser) > 0) { // m?? ???? t???n t???i
            tvBugAddUser.setText("T??i kho???n ???? t???n t???i");
            return false;
        }

        return true;
    }

    private void clearBugForm() {
        tvBugAddUser.setText("");
        tvBugAddName.setText("");
        tvBugAddPass.setText("");
        tvBugAddRePass.setText("");
    }

    private void clearForm() {
        edAddUser.setText("");
        edAddName.setText("");
        edAddPass.setText("");
        edAddRePass.setText("");
    }
}