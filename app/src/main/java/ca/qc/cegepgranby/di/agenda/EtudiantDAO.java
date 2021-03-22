package ca.qc.cegepgranby.di.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO extends SQLiteOpenHelper {

    public EtudiantDAO(Context context){
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Etudiants (id INTEGER PRIMARY KEY, nom TEXT NOT NULL, " +
                "adresse TEXT, telephone TEXT, site TEXT, note REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "DROP TABLE IF EXISTS Etudiants";
        db.execSQL(sql);
        this.onCreate(db);
    }

    public void inserer(Etudiant etudiant){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues donnees = new ContentValues();
        donnees.put("nom", etudiant.getNom());
        donnees.put("adresse", etudiant.getAdresse());
        donnees.put("telephone", etudiant.getTelephone());
        donnees.put("site", etudiant.getSite());
        donnees.put("note", etudiant.getNote());

        db.insert("Etudiants", null, donnees);
    }

    @NonNull
    private ContentValues prendreDonneesEtudiant(Etudiant etudiant){
        ContentValues donnees = new ContentValues();
        donnees.put("nom", etudiant.getNom());
        donnees.put("adresse", etudiant.getAdresse());
        donnees.put("telephone", etudiant.getTelephone());
        donnees.put("site", etudiant.getSite());
        donnees.put("note", etudiant.getNote());
        return donnees;
    }

    public List<Etudiant> chercherEtudiants() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Etudiants;";
        Cursor c = db.rawQuery(sql, null);
        Etudiant etudiant;
        List<Etudiant> etudiants = new ArrayList<>();
        while(c.moveToNext()){
            etudiant = new Etudiant();
            etudiant.setId(c.getLong(c.getColumnIndex("id")));
            etudiant.setNom(c.getString(c.getColumnIndex("nom")));
            etudiant.setAdresse(c.getString(c.getColumnIndex("adresse")));
            etudiant.setTelephone(c.getString(c.getColumnIndex("telephone")));
            etudiant.setSite(c.getString(c.getColumnIndex("site")));
            etudiant.setNote(c.getDouble(c.getColumnIndex("note")));
            etudiants.add(etudiant);
        }
        c.close();
        return etudiants;
    }

    public void supprimer(Etudiant etudiant){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {etudiant.getId().toString()};
        db.delete("Etudiants", "id = ?", params);
    }

    public void editer(Etudiant etudiant){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues donnees = prendreDonneesEtudiant(etudiant);
        String[] params = {etudiant.getId().toString()};
        db.update("Etudiants", donnees, "id = ?", params);
    }

}
