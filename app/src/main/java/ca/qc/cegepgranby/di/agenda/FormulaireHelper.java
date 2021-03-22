package ca.qc.cegepgranby.di.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

public class FormulaireHelper {

    private Etudiant etudiant;
    private EditText boiteNom;
    private EditText boiteAdresse;
    private EditText boiteTelephone;
    private EditText boiteSite;
    private RatingBar barreNote;

    public FormulaireHelper(FormulaireActivity activity){
        this.etudiant = new Etudiant();
        this.boiteNom = (EditText) activity.findViewById(R.id.formulaire_nom);
        this.boiteAdresse = (EditText) activity.findViewById(R.id.formulaire_adresse);
        this.boiteTelephone = (EditText) activity.findViewById(R.id.formulaire_telephone);
        this.boiteSite = (EditText) activity.findViewById(R.id.formulaire_site);
        this.barreNote = (RatingBar) activity.findViewById(R.id.formulaire_note);
    }

    public Etudiant recupererEtudiant(){
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(this.boiteNom.getText().toString());
        etudiant.setAdresse(this.boiteAdresse.getText().toString());
        etudiant.setTelephone(this.boiteTelephone.getText().toString());
        etudiant.setSite(this.boiteSite.getText().toString());
        etudiant.setNote(Double.valueOf(this.barreNote.getProgress()));
        return etudiant;
    }

    public void remplirFormulaire (Etudiant etudiant){
        this.etudiant = etudiant;
        this.boiteNom.setText(etudiant.getNom());
        this.boiteAdresse.setText(etudiant.getAdresse());
        this.boiteTelephone.setText(etudiant.getTelephone());
        this.boiteSite.setText(etudiant.getSite());
        this.barreNote.setProgress(etudiant.getNote().intValue());
    }

}
