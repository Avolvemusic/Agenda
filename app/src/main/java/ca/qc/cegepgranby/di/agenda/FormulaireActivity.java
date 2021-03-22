package ca.qc.cegepgranby.di.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormulaireActivity extends AppCompatActivity {

    FormulaireHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        this.helper = new FormulaireHelper(this);

        Intent intent = getIntent();
        Etudiant etudiant = (Etudiant) intent.getSerializableExtra("etudiant");
        if (etudiant != null){
            helper.remplirFormulaire(etudiant);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.agenda_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_formulaire_ok:
                Etudiant etudiant = this.helper.recupererEtudiant();
                EtudiantDAO dao = new EtudiantDAO(this);
                if(etudiant.getId() != null){
                    dao.editer(etudiant);
                } else {
                    dao.inserer(etudiant);
                }
                dao.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}