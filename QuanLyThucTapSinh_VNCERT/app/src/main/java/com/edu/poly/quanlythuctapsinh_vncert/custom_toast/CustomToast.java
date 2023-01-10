package com.edu.poly.quanlythuctapsinh_vncert.custom_toast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.poly.quanlythuctapsinh_vncert.R;

public class CustomToast {
    public static void showMessage(Activity context, String chuoi) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, context.findViewById(R.id.layout_custom_toast));
        toast.setView(view);
        TextView tvMessage = view.findViewById(R.id.tv_custom_toast);
        tvMessage.setText(chuoi);
        toast.show();
    }
}
