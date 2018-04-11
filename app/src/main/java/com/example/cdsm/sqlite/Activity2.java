package com.example.cdsm.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    EditText edt_SelectVille;
    ListView lvListe2;
    SQLiteDatabase db;
    Spinner listeVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edt_SelectVille = findViewById(R.id.edtSelectVille);
        lvListe2 = findViewById(R.id.lvAffichage2);
        listeVille = findViewById(R.id.spinner);

        db = this.openOrCreateDatabase("Clients", MODE_PRIVATE, null);

        ArrayList<String> liste = new ArrayList<String>();

        liste.add(" ");

        Cursor cr = db.rawQuery("SELECT DISTINCT CltVille FROM Client ", null);
        if (cr.moveToFirst()) {
            do {
                String ville = cr.getString(cr.getColumnIndex("CltVille"));

                String raw = ville;
                liste.add(raw);

            }
            while (cr.moveToNext());
            cr.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liste);
            listeVille.setAdapter(adapter);


        }
    }

    public void AfficherAc2_click(View view)
    {
        lvListe2.setAdapter(null);

        //String Select = edt_SelectVille.getText().toString();
        String Select = listeVille.getSelectedItem().toString();

        ArrayList<String> liste = new ArrayList<String>();

        Cursor cr = db.rawQuery("SELECT * FROM Client WHERE CltVille Like '"+Select+"'", null);
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
            lvListe2.setAdapter(adapter);

            //Log.e("Liste size",Integer.toString(liste.size()));

        }
        if(liste.size() < 1)
        {
            Toast.makeText(this,"Pas de client dans la ville!",Toast.LENGTH_SHORT).show();
        }
}

    public void TMP_click(View view)
    {
        lvListe2.setAdapter(null);

        ArrayList<String> liste = new ArrayList<String>();

        Cursor cr = db.rawQuery("SELECT * FROM Client WHERE CltVille LIKE 'Paris' AND CltNom LIKE 'Dupont' ", null);
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
            lvListe2.setAdapter(adapter);
    }
}
}
