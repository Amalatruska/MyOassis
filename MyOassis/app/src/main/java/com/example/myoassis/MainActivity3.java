package com.example.myoassis;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    EditText et;
    Button boton;
    TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tabla=(TableLayout) findViewById(R.id.tblayout);
        et=(EditText) findViewById(R.id.etborrar);
        boton =(Button)findViewById(R.id.buttonborrar);
        ConsultarDatos();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et.getText().toString();
                Admin admin = new Admin(MainActivity3.this, "pruebaregistro", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                try {
                    int totalRows = tabla.getChildCount();
                    boolean found = false;
                    for (int i = 1; i < totalRows; i++) {
                        View view = tabla.getChildAt(i);
                        TextView idView = (TextView) view.findViewById(R.id.tvid);
                        if (idView.getText().toString().equals(id)) {
                            tabla.removeViewAt(i);
                            BaseDeDatos.execSQL("DELETE FROM registro WHERE id = " + id);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new Exception("El id no existe");
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity3.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    BaseDeDatos.close();
                    et.setText("");
                }
            }
        });
    }
    public void ConsultarDatos() {
        Admin admin = new Admin(this, "pruebaregistro", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("select id,fecha,diaResumen,loMejor,loPeor,Calificacion from registro", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            View layout = LayoutInflater.from(this).inflate(R.layout.items,null,false);
            TextView id=(TextView)layout.findViewById(R.id.tvid) ;
            TextView fecha=(TextView)layout.findViewById(R.id.tvFecha);
            TextView diaResumen=(TextView)layout.findViewById(R.id.tvDiaResumen);
            TextView loMejor=(TextView)layout.findViewById(R.id.tvLoMejor);
            TextView loPeor=(TextView)layout.findViewById(R.id.tvLoPeor);
            TextView Calificacion=(TextView)layout.findViewById(R.id.tvCalificacion);


            id.setText(cursor.getString(0));
            fecha.setText(cursor.getString(1));
            diaResumen.setText(cursor.getString(2));
            loMejor.setText(cursor.getString(3));
            loPeor.setText(cursor.getString(4));
            Calificacion.setText(cursor.getString(5));


            tabla.addView(layout);
            cursor.moveToNext();
        }

    }
}