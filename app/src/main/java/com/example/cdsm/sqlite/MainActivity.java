package com.example.cdsm.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtNom, edtPrenom, edtVille;
    SQLiteDatabase db;
    ListView lvListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNom = findViewById(R.id.edt_Nom);
        edtPrenom = findViewById(R.id.edt_Prenom);
        edtVille = findViewById(R.id.edt_Ville);
        lvListe = findViewById(R.id.lvAffichage);


        db = this.openOrCreateDatabase("Clients", MODE_PRIVATE, null);

        db.beginTransaction();

        try {
            String CreateTable =
                    "CREATE TABLE IF NOT EXISTS Client("
                            + "  CltId INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "  CltNom TEXT,"
                            + "  CltPrenom TEXT,"
                            + "  CltVille TEXT);";
            db.execSQL(CreateTable);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            db.endTransaction();
        }

    }

    public void Save_click(View view) {
        String nom = edtNom.getText().toString();
        String prenom = edtPrenom.getText().toString();
        String ville = edtVille.getText().toString();

        db.beginTransaction();

        try {
            String Insert =
                    "INSERT INTO Client(CltNom,CltPrenom,CltVille)"
                            + "Values('" + nom + "','" + prenom + "','" + ville + "')";
            db.execSQL(Insert);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void Afficher_click(View view) {

        ArrayList<String> liste = new ArrayList<String>();

        Cursor cr = db.rawQuery("SELECT * FROM Client", null);
        if (cr.moveToFirst()) {
            do {
                int id = cr.getInt(cr.getColumnIndex("CltId"));
                String nom = cr.getString(cr.getColumnIndex("CltNom"));
                String prenom = cr.getString(cr.getColumnIndex("CltPrenom"));
                String ville = cr.getString(cr.getColumnIndex("CltVille"));

                String raw = nom + " " + prenom + " " + ville;
                liste.add(raw);

            }
            while (cr.moveToNext());
            cr.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liste);
            lvListe.setAdapter(adapter);

        }

    }

    public void Act2_click(View view)
    {
        Intent i = new Intent(this,Activity2.class);
        startActivity(i);
    }
}
