package com.example.mypoetryapp.Api;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.mypoetryapp.MainActivity;
import com.example.mypoetryapp.R;
import com.example.mypoetryapp.Response.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {

    EditText poetryDataEt;
    AppCompatButton resetBtn, updateBtn;
    Toolbar toolbar;
    ApiInterface apiInterface;

    int poetryId;
    String poetryDataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        initialization();
        setToolbar();
        setResetBtn();
        setUpPoetryData();


    }


    private void initialization() {
        poetryDataEt = findViewById(R.id.edittext_poetryData);
        resetBtn = findViewById(R.id.button_reset);
        updateBtn = findViewById(R.id.button_update);
        toolbar = findViewById(R.id.toolbar_update_poetry);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        poetryId = getIntent().getIntExtra("p_id", 0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetryDataEt.setText(poetryDataString);

    }


    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void CallApi(String pData, String pId) {
        apiInterface.updateMyPoetry(pData, pId).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(UpdatePoetry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdatePoetry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }

    private void setResetBtn() {
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poetryDataEt.getText().clear();
            }
        });
    }

    private void setUpPoetryData() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_data = poetryDataEt.getText().toString();

                if (p_data.equals("")) {
                    poetryDataEt.setError("Field is empty");
                } else {
                    CallApi(p_data, poetryId + "");
                    startActivity(new Intent(UpdatePoetry.this, MainActivity.class));
                }
            }
        });
    }
}