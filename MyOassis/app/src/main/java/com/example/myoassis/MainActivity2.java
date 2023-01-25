package com.example.myoassis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;

public class MainActivity2 extends AppCompatActivity {


    EditText et1,et2,et3;
    TextView tv1;
    RadioButton rb1,rb2,rb3,rb4,rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Date date = new Date();
        tv1 = findViewById(R.id.textViewFecha);
        SimpleDateFormat fecha = new SimpleDateFormat("dd / MM / yyyy");
        String fechaString = fecha.format(date);
        tv1.setText(fechaString);
        et1 =( EditText) findViewById(R.id.etCuentame);
        et2 =(EditText) findViewById(R.id.etLoMejor);
        et3 =(EditText) findViewById(R.id.etLoPeor);
        rb1 =(RadioButton) findViewById(R.id.rb1);
        rb2 =(RadioButton) findViewById(R.id.rb2);
        rb3 =(RadioButton) findViewById(R.id.rb3);
        rb4 =(RadioButton) findViewById(R.id.rb4);
        rb5 =(RadioButton) findViewById(R.id.rb5);

    }
    //A continuacion el metodo de guardado
    public void Guardar(View view) {
        Admin admin = new Admin(this,"pruebaregistro", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fechaMostrar = tv1.getText().toString();
        String cuentame = et1.getText().toString();
        String mejor = et2.getText().toString();
        String peor = et3.getText().toString();
        String calificacion = "";

        if(rb1.isChecked() == true){
            calificacion = "Mejor de lo esperado";
        }else if(rb2.isChecked() == true){
            calificacion = "Bien";
        }else if(rb3.isChecked() == true){
            calificacion = "Sin más";
        }else if(rb4.isChecked() == true){
            calificacion = "Mal";
        }else if(rb5.isChecked() == true){
            calificacion = "Fatal";
        }else{
            calificacion = "No hay datos de este dia";
        }


        if(!cuentame.isEmpty() && !mejor.isEmpty() && !peor.isEmpty()){
            ContentValues registrar = new ContentValues();
            registrar.put("fecha", fechaMostrar);
            registrar.put("diaResumen", cuentame);
            registrar.put("loMejor", mejor);
            registrar.put("loPeor", peor);
            registrar.put("Calificacion", calificacion);
            BaseDeDatos.insert("registro",null,registrar);
            BaseDeDatos.close();
            Toast.makeText(this,"REGISTRADO!! =)",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this,"Ups!! parece que falta algún campo por rellenar",Toast.LENGTH_SHORT).show();
        }


    }
}