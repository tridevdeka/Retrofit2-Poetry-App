package com.example.mypoetryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypoetryapp.Adapter.PoetryAdapter;
import com.example.mypoetryapp.Api.ApiClient;
import com.example.mypoetryapp.Api.ApiInterface;
import com.example.mypoetryapp.Model.PoetryModel;
import com.example.mypoetryapp.Response.GetPoetryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView poetry_recyclerView;
    PoetryAdapter poetryAdapter;

    Toolbar customToolbar;
    // ArrayList <PoetryModel> list=new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        getData();
        setUpCustomToolbar();


//        list.add(new PoetryModel(1,"John Doe","Hello","07-jun-2021"));
//        list.add(new PoetryModel(1,"Jack Black","Hi ","07-jun-2021"));

    }

    public void initialization() {
        poetry_recyclerView = findViewById(R.id.poetry_recyclerView);

        customToolbar = findViewById(R.id.toolbar_main);

        Retrofit RETROFIT = ApiClient.getClient();
        apiInterface = RETROFIT.create(ApiInterface.class);
    }

    private void setAdapter(List<PoetryModel> poetryModels) {

        poetryAdapter = new PoetryAdapter(this, poetryModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        poetry_recyclerView.setHasFixedSize(true);
        poetry_recyclerView.setLayoutManager(gridLayoutManager);
        poetry_recyclerView.setAdapter(poetryAdapter);


        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        poetry_recyclerView.addItemDecoration(divider);

    }

    private void getData() {
        apiInterface.getMyPoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {
                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {
                            setAdapter(response.body().getData());
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });

    }

    private void setUpCustomToolbar() {
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poetry, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add_poetry:
                startActivity(new Intent(MainActivity.this, AddPoetry.class));
                break;
            case R.id.item_night_mode:
                Toast.makeText(this, "Add feature", Toast.LENGTH_SHORT).show();
                break;

            default:
                return true;

        }
        return true;
    }
}