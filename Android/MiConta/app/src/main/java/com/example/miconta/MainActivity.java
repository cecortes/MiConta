package com.example.miconta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //Globales

    //Reference activity elements
    TextInputEditText txtUsr, txtPass;
    MaterialButton btnRegistrar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instance activity elements
        txtUsr = findViewById(R.id.TxtUsr);
        txtPass = findViewById(R.id.TxtPass);
        btnRegistrar = findViewById(R.id.BtnRegistrar);
        btnLogin = findViewById(R.id.BtnLogin);

        //Listening
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Función Registrar
                Registrar();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Función Login
                Login();

            }
        });

    }

    /*
    Show a new activity to register a new user
     */
    private void Registrar() {

        //Clear text inputs if any
        LimpiarTextos();

        //Start activity Registrar
        startActivity(new Intent(this, Registrar.class));

        //Close MainActivity
        finish();

    }

    /*
    Validate texts inputs with patterns
    Capture the values and store in global values to use later with volley
     */
    private void Login() {

        //Validate user
        if(Patterns.EMAIL_ADDRESS.matcher(txtUsr.getText().toString()).matches()){

            //Clean error if any
            txtUsr.setError(null);

            //-> TODO Capture value and proceed to method complete

        }else {

            //Usr
            txtUsr.setError("Usuario no válido");

        }

        //Validate pass
        Pattern patronNum = Pattern.compile("[0-9]");
        if(patronNum.matcher(txtPass.getText().toString()).matches()){

            //Clean error if any
            txtPass.setError(null);

            //-> TODO Capture value and proceed to method complete

        }else {

            //Usr
            txtPass.setError("Contraseña no válida");

        }

    }

    /*
    Clear for errors and texts if any
     */
    private void LimpiarTextos() {
        txtUsr.setError(null);
        txtPass.setError(null);
        txtUsr.setText("");
        txtPass.setText("");
    }
}