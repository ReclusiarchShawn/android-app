package com.example.my2ndapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class customlv extends RecyclerView.Adapter<customlv.ViewHolder> {
    private List<noteClass> lv;
    private notesDB db;
    private Context context;
    private ArrayList<noteClass> lvFiltered;
    public customlv(ArrayList<noteClass> lv,Context context){
        this.lv = new ArrayList<>(lv);
        this.lvFiltered = new ArrayList<>(lv);
        this.context=context;
        db=new notesDB(context);
    }
    @NonNull
    @Override
    public customlv.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cuslv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customlv.ViewHolder holder, int position) {
        noteClass var=lvFiltered.get(position);
            holder.tv.setText(var.getTexts());
            holder.cv.setOnClickListener(v -> {
                Intent i=new Intent(context, Marynotes2.class);
                i.putExtra("id",var.getId1());
                context.startActivity(i);
            });
    }

    @Override
    public int getItemCount() {
        return lvFiltered!=null? lvFiltered.size():0;
    }

    public void updateLists(List<noteClass> newList) {
        lv.clear();
        lv.addAll(newList);

        lvFiltered.clear();
        lvFiltered.addAll(newList);

        notifyDataSetChanged();
//        lv.clear();
//        lv.addAll(newList);
//        filternote("");
    }
    public void filternote(String text){
        lvFiltered.clear();
        if(text.isEmpty()){
            lvFiltered.addAll(lv);
        }else{
            text=text.toLowerCase();
            for (noteClass var:lv){
                if (var.getTexts() != null && var.getTexts().toLowerCase().contains(text)) {
                    lvFiltered.add(var);
                }
            }
        }notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cardview);
            tv=itemView.findViewById(R.id.notetv);
        }
    }


}
