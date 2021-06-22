package com.example.mypoetryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.mypoetryapp.Api.ApiClient;
import com.example.mypoetryapp.Api.ApiInterface;
import com.example.mypoetryapp.Response.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    EditText poetName, poetryData;
    AppCompatButton resetBtn, submitBtn;
    Toolbar toolbar;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);

        initialization();
        setUpToolbar();
        setUpAddPoetry();
        setResetBtn();

    }

    private void initialization() {
        poetName = findViewById(R.id.edittext_poet_name);
        poetryData = findViewById(R.id.edittext_poetry_data);
        resetBtn = findViewById(R.id.button_reset);
        submitBtn = findViewById(R.id.button_submit);
        toolbar = findViewById(R.id.toolbar_add_poetry);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpAddPoetry() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetNameString = poetName.getText().toString();
                String poetryDataString = poetryData.getText().toString();

                if (poetNameString.equals("") && poetryDataString.equals("")) {
                    poetName.setError("Field is empty");
                    poetryData.setError("Field is empty");

                } else if (poetryDataString.equals("")) {
                    poetryData.setError("Field is empty");

                } else if (poetNameString.equals("")) {
                    poetName.setError("Field is empty");

                } else {
                    callApi(poetNameString, poetryDataString);
                    Toast.makeText(AddPoetry.this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPoetry.this, MainActivity.class));
                }
            }
        });
    }

    private void callApi(String pName, String pData) {
        apiInterface.addMyPoetry(pName, pData).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(AddPoetry.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPoetry.this, "Add unsuccessful", Toast.LENGTH_SHORT).show();
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
                poetName.getText().clear();
                poetryData.getText().clear();
            }
        });
    }


}