package abi.parveen.donate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FoodMyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataClassList;

    public FoodMyAdapter(Context context, List<DataClass> dataClassList) {
        this.context = context;
        this.dataClassList = dataClassList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataClassList.get(position).getEdataImage()).into(holder.recImage);
        holder.recName.setText(dataClassList.get(position).getAdataName());
        holder.recLocation.setText(dataClassList.get(position).getBdataLocation());
        holder.recSpinner.setText(dataClassList.get(position).getCdataSpinner());
        holder.recGmail.setText(dataClassList.get(position).getDdataGmail());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,DetailActivity.class);
                intent.putExtra("name",dataClassList.get(holder.getAdapterPosition()).getAdataName());
                intent.putExtra("image",dataClassList.get(holder.getAdapterPosition()).getEdataImage());
                intent.putExtra("location" ,dataClassList.get(holder.getAdapterPosition()).getBdataLocation());
                intent.putExtra("gmail",dataClassList.get(holder.getAdapterPosition()).getDdataGmail());
                intent.putExtra("blood group",dataClassList.get(holder.getAdapterPosition()).getCdataSpinner());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataClassList.size();
    }
    public void searchDataList(ArrayList<DataClass> searchList){
        dataClassList=searchList;
        notifyDataSetChanged();
    }
}
class FoodMyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recName, recLocation, recSpinner, recGmail;
    CardView recCard;

    public FoodMyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage =itemView.findViewById(R.id.recImage);
        recCard =itemView.findViewById(R.id.recCard);
        recName =itemView.findViewById(R.id.recname);
        recLocation=itemView.findViewById(R.id.reclocation);
        recGmail=itemView.findViewById(R.id.recgmail);
        recSpinner =itemView.findViewById(R.id.recblood);

    }
}
