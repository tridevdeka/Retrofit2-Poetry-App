package com.example.mypoetryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypoetryapp.Api.ApiClient;
import com.example.mypoetryapp.Api.ApiInterface;
import com.example.mypoetryapp.Api.UpdatePoetry;
import com.example.mypoetryapp.Model.PoetryModel;
import com.example.mypoetryapp.R;
import com.example.mypoetryapp.Response.DeletePoetryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryViewHolder> {

    Context context;
    List<PoetryModel> poetryModelList;

    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModelList) {
        this.context = context;
        this.poetryModelList = poetryModelList;

        Retrofit RETROFIT = ApiClient.getClient();
        apiInterface = RETROFIT.create(ApiInterface.class);

    }

    @NonNull
    @Override
    public PoetryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list, parent, false);
        return new PoetryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoetryAdapter.PoetryViewHolder holder, int position) {

        PoetryModel model = poetryModelList.get(position);
        holder.tv_poet_name.setText(model.getPoet_name());
        holder.tv_poetry_data.setText(model.getPoetry_data());
        holder.tv_date_time.setText(model.getDate_time());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMyPoetry(poetryModelList.get(position).getId() + "", position);
            }
        });

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id", poetryModelList.get(position).getId());
                intent.putExtra("p_data", poetryModelList.get(position).getPoetry_data());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poetryModelList.size();
    }

    public void deleteMyPoetry(String id, int pos) {
        apiInterface.deleteMyPoetry(id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if (response.body().getStatus().equals("1")) {
                            poetryModelList.remove(pos);
                            notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("exception", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }

    public static class PoetryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_poet_name, tv_poetry_data, tv_date_time;
        AppCompatButton btn_update, btn_delete;

        public PoetryViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_poet_name = itemView.findViewById(R.id.tv_poet_name);
            tv_poetry_data = itemView.findViewById(R.id.tv_poetry_data);
            tv_date_time = itemView.findViewById(R.id.tv_date_time);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

}
