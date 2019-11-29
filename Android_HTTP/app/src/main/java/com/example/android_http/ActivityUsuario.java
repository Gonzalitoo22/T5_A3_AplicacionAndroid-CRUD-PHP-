package com.example.android_http;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlador.AnalizadorJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityUsuario extends Activity {
    Button btnEntrar;
    EditText txtU, txtC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_usuario);

        btnEntrar = findViewById(R.id.btnEntrar);
        txtU = findViewById(R.id.txtU);
        txtC = findViewById(R.id.txtC);
    }

    public void abrirActivities(View v){
        String usuario = txtU.getText().toString();
        String contra = txtC.getText().toString();
        if (usuario.equals("")|| contra.equals("")){
            Toast.makeText(getApplicationContext(),"Faltan valores",Toast.LENGTH_LONG).show();
        }else {
            new IniciarSesion().execute(usuario, contra);
        }
        /*Intent i;
        switch (v.getId()){
            case R.id.btnIniciar:
                i = new Intent(MainActivity.this, ActivityMenu.class);
                startActivity(i);
            break;
        }*/
    }
    ///CLLASE INTERNA PARA REALIZAR EL ENVIO DE DATOS EN OTRO HILO DE EJECUCION

    class IniciarSesion extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... args) {
            Map<String,String> mapDatos=new HashMap<String, String>();
            mapDatos.put("usuario",args[0]);
            mapDatos.put("contra",args[1]);

            AnalizadorJSON analizador_json= new AnalizadorJSON();
            //url para forma local
            //si se quiere utilizar  el servidor proxmox se tiene que poner la direccion del servido y el puerto
            //10.0.2.2
            String url ="http://192.168.1.71/Pruebas_PHP/SistemaABCC/API_PHP_Android/inicio_sesion.php";
            String metodo="POST";

            JSONObject resultado = analizador_json.peticionHTTPSesion(url,metodo,mapDatos);
            int r=0;
            try {
                r =resultado.getInt("exito");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (r==1){
                Intent i;
                i = new Intent(ActivityUsuario.this, MainActivity.class);
                startActivity(i);
                Log.i("Msj resultado", "SESION CORRECTA");

            }else{
                Intent i;
                i = new Intent(ActivityUsuario.this, ActivityUsuario.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext() , "Usuario incorrecto", Toast.LENGTH_LONG).show();
                Log.i("Msj resultado", "SESION INCORRECTA");
            }



            return null;
        }
    }
}
