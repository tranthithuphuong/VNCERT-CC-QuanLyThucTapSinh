package com.edu.poly.quanlythuctapsinh_vncert.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.poly.quanlythuctapsinh_vncert.R;
import com.edu.poly.quanlythuctapsinh_vncert.doi_tuong_Interface.ObjectInterface;
import com.edu.poly.quanlythuctapsinh_vncert.objects.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> list;
    private ObjectInterface objectInterface;
    private int type;

    public ThanhVienAdapter(Context context, ObjectInterface objectInterface) {
        this.context = context;
        this.objectInterface = objectInterface;
    }

    public void setData(ArrayList<ThanhVien> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTypeAnimation(int type) {
        this.type = type;
    }


    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_thanh_vien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if(thanhVien == null) {
            return;
        }

        holder.tvId.setText("Mã thành viên: " + thanhVien.getId());
        holder.tvName.setText("Thành viên: " + thanhVien.getName());
        holder.tvGender.setText("Giới tính: " + thanhVien.getGender());
        holder.tvNumberphone.setText("Số đt: " + thanhVien.getNumberphone());
        holder.tvGmail.setText("Gmail: " + thanhVien.getGmail());
        holder.tvAddress.setText("Địa chỉ: " + thanhVien.getAddress());

        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] {Manifest.permission.CALL_PHONE},
                            999);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + thanhVien.getNumberphone()));
                    context.startActivity(intent);
                }
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickDelete(thanhVien);
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectInterface.onClickUpdate(thanhVien);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvGender;
        private TextView tvNumberphone;
        private TextView tvGmail;
        private TextView tvAddress;
        private TextView tvCall;
        private ImageView imgUpdate;
        private ImageView imgDelete;



        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvNumberphone = itemView.findViewById(R.id.tv_numberphone);
            tvGmail = itemView.findViewById(R.id.tv_gmail);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvCall = itemView.findViewById(R.id.tv_call);
            imgUpdate = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }
}
