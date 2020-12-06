package com.example.miconta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {

    //Globales
    boolean flgNewUr = false, flgNewPass = false;   //Banderas de estado

    //Activity references
    TextInputEditText txtNewUsr, txtNewPass;
    MaterialButton btnNewUsr;

    //Implementación de objetos de Volley
    RequestQueue jsonRQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Instance activity elements
        txtNewUsr = findViewById(R.id.TxtNewUsr);
        txtNewPass = findViewById(R.id.TxtNewPass);
        btnNewUsr = findViewById(R.id.BtnNewUsr);

        //Listening
        btnNewUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO method to add new user with volley
                AddNewUsr();

            }
        });

        // Instance the RequestQueue.
        jsonRQ = Volley.newRequestQueue(this);
    }

    /*
    Validate input text views with patterns
    Add new usr with volley GET method
     */
    private void AddNewUsr() {

        //Validate user
        if(Patterns.EMAIL_ADDRESS.matcher(txtNewUsr.getText().toString()).matches()){

            //Clean error if any
            txtNewUsr.setError(null);

            //Change flag state
            flgNewUr = true;

        }else {

            //Usr
            txtNewUsr.setError("Usuario no válido");

            //Re init flag state
            flgNewUr = false;

        }

        //Validate pass
        Pattern patronNum = Pattern.compile("[0-9][0-9][0-9][0-9]");
        if(patronNum.matcher(txtNewPass.getText().toString()).matches()){

            //Clean error if any
            txtNewPass.setError(null);

            //Change flag state
            flgNewPass = true;

        }else {

            //Usr
            txtNewPass.setError("Contraseña no válida");

            //Re init flag state
            flgNewPass = false;

        }

        //Validate flags
        if (flgNewUr && flgNewPass) {

            //Capture data into local variables
            String usuario = txtNewUsr.getText().toString();
            String pwd = txtNewPass.getText().toString();

            //URL to php server
            String url = "http://sylkaventas.ddns.net/conta_addusr.php?u="
                    + usuario + "&p=" + pwd;

            //Jason Request
            JsonObjectRequest insertRQ = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Usuario
                    MaterialAlertDialogBuilder msg = new MaterialAlertDialogBuilder(Registrar.this);
                    msg.setMessage("Nuevo usuario agregado");
                    msg.setTitle("Jarvis Contador");
                    msg.setIcon(R.drawable.ic_account);
                    msg.create().show();

                    //Clear text inputs
                    LimpiarTextos();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Usuario
                    MaterialAlertDialogBuilder err = new MaterialAlertDialogBuilder(Registrar.this);
                    err.setMessage("No se puede dar de alta al usuario");
                    err.setTitle("Error Jarvis Contador");
                    err.setIcon(R.drawable.ic_account);
                    err.create().show();

                }
            });

            //Add Jason Request to Request Que
            jsonRQ.add(insertRQ);
        }
    }

    /*
    Clear for errors and texts if any
     */
    private void LimpiarTextos() {
        txtNewUsr.setError(null);
        txtNewPass.setError(null);
        txtNewUsr.setText("");
        txtNewPass.setText("");
    }
}