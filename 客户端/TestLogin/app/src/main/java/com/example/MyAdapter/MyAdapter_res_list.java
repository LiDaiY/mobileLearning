package com.example.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.MaterialInfo;
import com.example.services.DownLoadService;
import com.example.testlogin.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/24.
 */

public class MyAdapter_res_list extends RecyclerView.Adapter<MyAdapter_res_list.ViewHolder>{
    List<MaterialInfo> datas;
    Context context;

    boolean isStop=true;//用来限制ui的变化

    public MyAdapter_res_list(List<MaterialInfo> datas, Context context) {
        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;
        this.context = context;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.res_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

         holder.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                MaterialInfo materialInfo=datas.get(position);
                materialInfo .setPosition(position);
                Log.e("downBtn","获得的position是："+position);
               if(materialInfo.state.equals("")||materialInfo.state.equals("暂停")){
                   isStop=false;
                   holder.progressBar.setVisibility(View.VISIBLE);
                   holder.btnStart.setEnabled(false);
                   new sleepAsync().execute(holder.btnStop);
                   Intent intent=new Intent(context, DownLoadService.class);
                   intent.setAction(DownLoadService.ACTION_START);
                   intent.putExtra("materialInfo", materialInfo);
                   context.startService(intent);

                   materialInfo.setState("下载中");
                   notifyItemChanged(position, "lzh");//局部更新，否则按钮将会不停闪烁
               }
            }
        });
         holder.btnStop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int position=holder.getAdapterPosition();
                 MaterialInfo materialInfo=datas.get(position);
                 materialInfo .setPosition(position);
                 Log.e("downBtn","获得的position是："+position);
                 if (materialInfo.getState().equals("下载中")) {
                     isStop=true;
                     holder.btnStop.setEnabled(false);
                     new sleepAsync().execute(holder.btnStart);
                     Intent intent = new Intent(context, DownLoadService.class);
                     intent.setAction(DownLoadService.ACTION_STOP);
                     intent.putExtra("materialInfo", materialInfo);
                     context.startService(intent);
                     materialInfo.setState("暂停");
                     notifyItemChanged(position, "lzh");//局部更新，否则按钮将会不停闪烁
                 }
             }
         });

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int position=holder.getAdapterPosition();
                 MaterialInfo materialInfo=datas.get(position);
                 materialInfo .setPosition(position);
                 playVideo(materialInfo.getSavePath(),v.getContext());
             }
         });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MaterialInfo materialInfo = datas.get(position);
        holder.textName.setText(materialInfo.getResTitle());
        holder.textState.setText(materialInfo.getState());
        holder.progressBar.setProgress(materialInfo.getProgress());
        holder.resSize.setText((materialInfo.getSize()/1024/1024)+"MB");
    }

    @Override
    public int getItemCount() {
        if (datas == null) return 0;
        return datas.size();
    }

    public void updateProgress(int position,int progress){
        datas.get(position).setProgress(progress);
        if (progress==100){
            datas.get(position).setState("下载完成");
        }else if (isStop==false){//就是没有暂停
            datas.get(position).setState("下载中");
        }
        notifyItemChanged(position,"lzh");//局部更新，否则按钮将会不停闪烁
//        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else{
            ViewHolder viewHolder=holder;
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setProgress(datas.get(position).progress);
            viewHolder.textState.setText(datas.get(position).state);
            if (viewHolder.btnStart.getTag().equals("1")){
                viewHolder.btnStart.setEnabled(false);
                viewHolder.btnStop.setEnabled(true);
                viewHolder.btnStart.setTag("0");
                datas.get(position).setState("下载中");
                viewHolder.textState.setText("下载中");
            }
            if (datas.get(position).state.equals("下载完成")){
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.btnStop.setEnabled(false);
                viewHolder.btnStart.setEnabled(true);
            }
        }
    }

    /**************************************viewholder********************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textState;
        ProgressBar progressBar;
        Button btnStart;
        Button btnStop;
        TextView resSize;
        TextView promptView;
        View itemView;

        public ViewHolder(View convertView) {
            super(convertView);

            itemView=convertView;
            textName = convertView.findViewById(R.id.res_resName);
            textState = convertView.findViewById(R.id.res_state);
            progressBar = convertView.findViewById(R.id.res_progressBar);
            btnStart =convertView.findViewById(R.id.res_start_btn);
            btnStop =convertView.findViewById(R.id.res_stop_btn);
            resSize = convertView.findViewById(R.id.res_size);
            promptView=convertView.findViewById(R.id.res_list_prompt_view);

            btnStart.setTag("1");//这是用于初始化的标志

        }
    }

    public class sleepAsync extends AsyncTask<Button,Void,Button>{

        @Override
        protected Button doInBackground(Button... buttons) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return buttons[0];
        }

        @Override
        protected void onPostExecute(Button button) {
            super.onPostExecute(button);
            button.setEnabled(true);
        }
    }

    public void playVideo( String param,Context context ) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        File file=new File(param);
        if (!file.exists()){
            Toast.makeText(context,"请先点击下载再播放",Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        context.startActivity(intent);
    }
}
