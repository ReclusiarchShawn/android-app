package com.example.my2ndapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<bookvar> {
    private Context context;
    private int resource;
    private List<bookvar> list;
    private bookdata datas;
    public CustomAdapter(@NonNull Context context, int resource, List<bookvar> objects) {
        super(context, resource,objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
        this.datas = new bookdata(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(resource,parent,false);
            holder=new ViewHolder();
            holder.image=convertView.findViewById(R.id.image);
            holder.name=convertView.findViewById(R.id.fname);
            holder.deleteBtn = convertView.findViewById(R.id.delbtn);
            holder.viewBtn = convertView.findViewById(R.id.viewbtn);
            convertView.setTag(holder);


        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        bookvar item = getItem(position);
        if (item != null) {
            String imgpath=item.getImg_path();
            if(imgpath!=null && !imgpath.isEmpty()){
                Uri imguri= Uri.parse(imgpath);
                holder.image.setImageURI(imguri);
            }else{
                holder.image.setImageResource(R.drawable.baseline_fastfood_24);
            }
            holder.name.setText(item.getFoodname());
            holder.deleteBtn.setOnClickListener(v -> {
                boolean deleted = datas.delete(item);
                if (deleted) {
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    list.remove(position); 
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Deletion Failed", Toast.LENGTH_SHORT).show();
                }
            });
            holder.viewBtn.setOnClickListener(v -> {
                Intent intent = new Intent(context, Marycookbook3.class);
                intent.putExtra("food_id", item.getId());  // Pass Recipe ID
                intent.putExtra("food_name", item.getFoodname()); // Pass Recipe Name
                context.startActivity(intent);
            });
        }


        return convertView;
    }

    static class ViewHolder {
    ImageView image;
    TextView name;
    Button deleteBtn,viewBtn;
}
}
