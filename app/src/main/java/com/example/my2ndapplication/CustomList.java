package com.example.my2ndapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


import java.util.List;

public class CustomList extends RecyclerView.Adapter<CustomList.ViewHolder> {
    private List<Recipe> recipes;
    private Context context;
    private DBhelper db;

    public CustomList(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
        db = new DBhelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.txtName.setText(recipe.getName());

        if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
            Glide.with(context)
                    .load(recipe.getImage())
                    .placeholder(R.drawable.ic_food_foreground)
                    .error(R.drawable.baseline_fastfood_24)
                    .into(holder.imgFood);
        } else {
            Glide.with(context).load(recipe.getImage()).placeholder(R.drawable.baseline_fastfood_24).into(holder.imgFood);
        }




        holder.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeDetails.class);
            intent.putExtra("RECIPE_ID", recipe.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context).setTitle("Delete Recipe").setMessage("Are you sure you want to delete this recipe?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        db.deleteRecipe(recipe.getId());
                        recipes.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancel", null).show();
        });
    }

    @Override
    public int getItemCount() { return recipes.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName;
        Button btnView, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            btnView = itemView.findViewById(R.id.btnView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}