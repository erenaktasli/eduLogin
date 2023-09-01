package com.kentuniversity.kent;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editTextNo;
    EditText editTextAdı;
    EditText editTextSoyadı;
    Button buttonKayıtNesnesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =(TextView) findViewById(R.id.tW1);
        editTextNo=(EditText)findViewById(R.id.eTNo);
        editTextAdı=(EditText) findViewById(R.id.eTAdı);
        editTextSoyadı=(EditText) findViewById(R.id.eTSoyadı);
        buttonKayıtNesnesi=(Button) findViewById(R.id.buttonKayıt);


        try {

            SQLiteDatabase sqliteDb = this.openOrCreateDatabase("OGRENCI", MODE_PRIVATE, null);
            sqliteDb.execSQL("CREATE TABLE IF NOT EXISTS KAYIT(OgrenciNo INTEGER, Adı VARCHAR, Soyadı VARCHAR)");
            //sqliteDb.execSQL("INSERT INTO KAYIT(OgrenciNo,Adı, Soyadı) VALUES (1,'AHMET','MEHMET')");


            Cursor cursor = sqliteDb.rawQuery("SELECT * FROM KAYIT", null);
            int IdX = cursor.getColumnIndex("OgrenciNo");
            int AdıX = cursor.getColumnIndex("Adı");
            int SoyadıX=cursor.getColumnIndex("Soyadı");

            while (cursor.moveToNext()) {
                textView.append(cursor.getInt(IdX) + "\t" + cursor.getString(AdıX) +"\t"+cursor.getString(SoyadıX) +"\n");
            }
            cursor.close();

            buttonKayıtNesnesi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqliteDb.execSQL("INSERT INTO KAYIT(OgrenciNo,Adı, Soyadı) VALUES ('"+
                            Integer.parseInt(editTextNo.getText().toString()) +"','"+
                            editTextAdı.getText().toString()+"','"+
                           editTextSoyadı.getText().toString() +"')");
                    editTextNo.setText("");
                    editTextAdı.setText("");
                    editTextSoyadı.setText("");

                    Cursor cursor = sqliteDb.rawQuery("SELECT * FROM KAYIT ORDER BY OgrenciNo DESC", null);
                    int IdX = cursor.getColumnIndex("OgrenciNo");
                    int AdıX = cursor.getColumnIndex("Adı");
                    int SoyadıX=cursor.getColumnIndex("Soyadı");

                    while (cursor.moveToNext()) {
                        textView.append(cursor.getInt(IdX) + "\t" + cursor.getString(AdıX) +"\t"+cursor.getString(SoyadıX) +"\n");
                    }
                    cursor.close();


                }
            });        }
        catch(Exception eX1)
        {
            eX1.printStackTrace();
        }

    }

}