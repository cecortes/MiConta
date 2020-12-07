package com.example.miconta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    //Globales
    boolean flgUsr = false, flgPass = false;   //Banderas de estado

    //Reference activity elements
    TextInputEditText txtUsr, txtPass;
    MaterialButton btnRegistrar, btnLogin;

    //Implementación de objetos de Volley
    RequestQueue jsonRQ;

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

        // Instance the RequestQueue.
        jsonRQ = Volley.newRequestQueue(this);

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

            //Change flag state
            flgUsr = true;

        }else {

            //Usr
            txtUsr.setError("Usuario no válido");

        }

        //Validate pass
        Pattern patronNum = Pattern.compile("[0-9][0-9][0-9][0-9]");
        if(patronNum.matcher(txtPass.getText().toString()).matches()){

            //Clean error if any
            txtPass.setError(null);

            //Change flag state
            flgPass = true;

        }else {

            //Usr
            txtPass.setError("Contraseña no válida");

        }

        //Validación de banderas de estado
        if (flgUsr && flgPass) {

            //Función para validar al usuario
            ValidaUsr();

        }

    }

    /*
    Captura los input text en variables
    Construye la URL necesaria
    Construye el JSON Request
    Prepara los métodos OnResponse y Error del Json
    Valida la respuesta
    Continua al siguiente activity
    Pasa el usuario con putextra al siguiente activity
     */
    private void ValidaUsr() {

        //Capture data into local variables
        final String usuario = txtUsr.getText().toString();
        final String pwd = txtPass.getText().toString();

        //URL to php server
        String url = "http://sylkaventas.ddns.net/conta_sesion.php?usr="
                + usuario + "&pwd=" + pwd;

        //Json Request
        JsonObjectRequest selectRQ = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Locales
                        Usuarios usuarios = new Usuarios();
                        JSONArray jsonArray = response.optJSONArray("sesion");
                        JSONObject jsonObject = null;

                        try {

                            jsonObject = jsonArray.getJSONObject(0);

                            //Captura del arreglo dentro del objeto usuarios
                            usuarios.setUsr_correo(jsonObject.optString("usr_correo"));
                            usuarios.setUsr_pwd(jsonObject.optString("usr_pwd"));
                            usuarios.setUsr_created(jsonObject.optString("usr_created"));

                            //Validación de credenciales
                            if (usuario.equals(usuarios.getUsr_correo())){

                                //Clear text inputs
                                LimpiarTextos();

                                //Continue to next activity
                                Intent intent = new Intent(MainActivity.this,
                                        MenuPrincipalActivity.class);

                                //Pasamos el parámetro de la sesion por medio de PUTEXTRA
                                intent.putExtra(MenuPrincipalActivity.nomSesion, usuarios.getUsr_correo());

                                //Continue to next activity
                                startActivity(intent);

                                //Close MainActivity activity
                                finish();

                            }

                        }catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Usuario
                MaterialAlertDialogBuilder err = new MaterialAlertDialogBuilder(MainActivity.this);
                err.setMessage("Usuario o contraseña incorrectos");
                err.setTitle("Error Jarvis Contador");
                err.setIcon(R.drawable.ic_error);
                err.create().show();

            }
        });

        //Add Jason Request to Request Que
        jsonRQ.add(selectRQ);

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