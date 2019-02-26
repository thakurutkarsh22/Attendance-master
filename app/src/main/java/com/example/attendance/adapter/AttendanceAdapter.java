package com.example.attendance.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.models.AttendanceModel;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    static int count =0 ;
    Context context;
    public ArrayList<AttendanceModel> attendanceModels;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> attendanceModel) {
        this.context = context;
        this.attendanceModels = attendanceModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.attendence_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final AttendanceModel viewModel = attendanceModels.get(myViewHolder.getAdapterPosition());
        if (viewModel == null)
            return;
        myViewHolder.txt_name.setText(viewModel.getStu_name());
        myViewHolder.chk_attendance.setChecked(viewModel.isAttendanceMark());

        myViewHolder.chk_attendance.setTag(viewModel);
        myViewHolder.chk_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox chk = (CheckBox) view;
                AttendanceModel list = (AttendanceModel) chk.getTag();
                list.setAttendanceMark(chk.isChecked());
                count++;
                attendanceModels.get(i).setAttendanceMark(chk.isChecked());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceModels.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox chk_attendance;
        ImageView image_profile;
        TextView txt_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chk_attendance = (CheckBox) itemView.findViewById(R.id.checkbox);
            image_profile = (ImageView) itemView.findViewById(R.id.image);
            txt_name = (TextView) itemView.findViewById(R.id.studentName);
        }
    }
}
