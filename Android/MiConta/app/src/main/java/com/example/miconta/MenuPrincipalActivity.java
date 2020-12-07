package com.example.miconta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MenuPrincipalActivity extends AppCompatActivity {

    //Global que recibe datos de otros activities
    public static final String nomSesion = "nomsesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }
}