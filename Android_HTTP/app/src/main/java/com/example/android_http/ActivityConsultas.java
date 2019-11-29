package com.example.android_http;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.controlador.AnalizadorJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityConsultas extends Activity {
    EditText cajaNumControl, cajaNombre, cajaPrimerAp;
    ListView lsl_consulta;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList=new ArrayList<>();
    volatile String dato;
    Button btn_Consultar;
    int v = 0;
    String campo;
    Spinner spCarrera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        cajaNumControl = findViewById(R.id.txtNC);
        cajaNombre = findViewById(R.id.txtN);
        cajaPrimerAp = findViewById(R.id.txtPA);

        lsl_consulta=findViewById(R.id.lsl_consulta);

        new MostrarAlumnos().execute();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lsl_consulta.setAdapter(adapter);


        cajaNumControl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String valor = cajaNumControl.getText().toString();
                    if ( valor !="" ){
                        arrayList = new MostrarAlumnosBus().execute("nc",valor).get();
                        if (arrayList != null){
                            adapter = new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                            lsl_consulta.setAdapter(adapter);

                        }else{
                            Toast.makeText(ActivityConsultas.this,"No hay registros",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        new MostrarAlumnos().execute();
                        adapter= new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                        lsl_consulta.setAdapter(adapter);
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        cajaNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String valor = cajaNombre.getText().toString();
                    if ( valor !="" ){
                        arrayList = new MostrarAlumnosBus().execute("n",valor).get();
                        if (arrayList != null){
                            adapter = new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                            lsl_consulta.setAdapter(adapter);

                        }else{
                            Toast.makeText(ActivityConsultas.this,"No hay registros",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        new MostrarAlumnos().execute();
                        adapter= new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                        lsl_consulta.setAdapter(adapter);
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        cajaPrimerAp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String valor = cajaPrimerAp.getText().toString();
                    if ( valor !="" ){
                        arrayList = new MostrarAlumnosBus().execute("pa",valor).get();
                        if (arrayList != null){
                            adapter = new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                            lsl_consulta.setAdapter(adapter);

                        }else{
                            Toast.makeText(ActivityConsultas.this,"No hay registros",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        new MostrarAlumnos().execute();
                        adapter= new ArrayAdapter<>(ActivityConsultas.this,android.R.layout.simple_list_item_1,arrayList);
                        lsl_consulta.setAdapter(adapter);
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void consultarAlumno(View v){
        String nc = cajaNumControl.getText().toString();

        //Verificar conexion WIFI

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected()){
            new MostrarAlumnos().execute(dato);
        }else{
            Toast.makeText(this, "Error Wi-Fi", Toast.LENGTH_LONG).show();
            Log.i("MSJ =", "Error WiFi");
        }
    }//Metodo registrar alumno


    class MostrarAlumnos extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            AnalizadorJSON analizador_json = new AnalizadorJSON();

            //cambiar el nombre del archivo php, debe de ser el de consulta
            String url="http://192.168.1.71/Pruebas_PHP/SistemaABCC/API_PHP_Android/consultas_alumnos.php";
            JSONObject jsonObject= analizador_json.consultaHTTP(url);

            try {
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                String cadena = null;

                for (int i=0; i<jsonArray.length();i++){
                    cadena=jsonArray.getJSONObject(i).getString("nc")+"|"+
                            jsonArray.getJSONObject(i).getString("n")+"|"+
                            jsonArray.getJSONObject(i).getString("pa")+"|"+
                            jsonArray.getJSONObject(i).getString("sa")+"|"+
                            jsonArray.getJSONObject(i).getString("e")+"|"+
                            jsonArray.getJSONObject(i).getString("s")+"|"+
                            jsonArray.getJSONObject(i).getString("c");                          ;

                    //se agrega String ´por String al array list, esto llena el array lista para despues meterlo al adaptador
                    arrayList.add(cadena);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }//mostrar alumnos
    }

    class MostrarAlumnosBus extends AsyncTask<String, String, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> arrayList = null;
            AnalizadorJSON analizador_json = new AnalizadorJSON();

            //cambiar el nombre del archivo php, debe de ser el de consulta
            String url="http://192.168.1.71/Pruebas_PHP/SistemaABCC/API_PHP_Android/consulta_caja.php";
            JSONObject jsonObject= analizador_json.consultaHTTPCaja(url, strings[0], strings[1]);

            try {

                    arrayList = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("alumnos");

                    String cadena = "";
                    for (int i = 0; i < jsonArray.length(); i++){
                        cadena = jsonArray.getJSONObject(i).getString("nc") + " | " +
                                jsonArray.getJSONObject(i).getString("n") + " | " +
                                jsonArray.getJSONObject(i).getString("pa") + " | " +
                                jsonArray.getJSONObject(i).getString("sa") + " | " +
                                jsonArray.getJSONObject(i).getString("c") + " | " +
                                jsonArray.getJSONObject(i).getString("s") + " | " +
                                jsonArray.getJSONObject(i).getString("e");

                        arrayList.add(cadena);
                    }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }//mostrar alumnos
    }
}
