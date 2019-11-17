package com.example.mystore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.Products;
import com.example.mystore.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    Context context;
    List<Products>products;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int positon);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;

    }


    public ProductsAdapter(Context context, List<Products> products) {
        this.context = context;
        this.products = products;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
//        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.items_does,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Products UploadInfo = products.get(position);

        holder.brand.setText(products.get(position).getBrand());

        holder.description.setText(products.get(position).getDescription());

        holder.price.setText("Rs."+products.get(position).getPrice());
        holder.ratings.setText(products.get(position).getRating());

//Picasso.get().load(products.get(position).getThumbnail()).fit().centerCrop().into(holder.image);
//        Glide.with(context).load(products.get(position).getThumbnail()).into(holder.image);
Picasso.get().load(products.get(position).getThumbnail()).fit().centerCrop().into(holder.image);

//        Picasso.get().load(products.get(position).getThumbnail()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.image);

//       holder.image.setImageResource(products.get(position).getThumbnail());

//
//holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent i= new Intent (mContext,NewTask.class);
//            mContext.startActivity(i);
//    }
//});

    }

    @Override
    public int getItemCount() {
        return products.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description,price,brand,ratings;
        ImageView image;
        RecyclerView parentLayout;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

brand=itemView.findViewById(R.id.view_brand);
            description=(TextView)itemView.findViewById(R.id.view_desc);
            price=(TextView)itemView.findViewById(R.id.view_price);
            ratings=itemView.findViewById(R.id.view_Rating);


            image=(ImageView) itemView.findViewById(R.id.view_img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int Position=getAdapterPosition();
                        if(Position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(Position);
                        }
                    }
                }
            });

        }


    }
}

