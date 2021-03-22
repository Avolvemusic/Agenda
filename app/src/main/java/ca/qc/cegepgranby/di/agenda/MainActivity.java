package ca.qc.cegepgranby.di.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentList = (ListView) findViewById(R.id.student_list);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Etudiant etudiant = (Etudiant) studentList.getItemAtPosition(position);
                Intent intentAllerAuFormulaire = new Intent(MainActivity.this, FormulaireActivity.class);
                intentAllerAuFormulaire.putExtra("etudiant", etudiant);
                startActivity(intentAllerAuFormulaire);
            }
        });

        Button newEtudiant = (Button) findViewById(R.id.button_nouveau_etudiant);
        newEtudiant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentGoToForm = new Intent(MainActivity.this, FormulaireActivity.class);
                startActivity(intentGoToForm);
            }
        });

        registerForContextMenu(studentList);
    }

    private void chargerListeEtudiants() {
        EtudiantDAO dao = new EtudiantDAO(this);
        List<Etudiant> etudiants = dao.chercherEtudiants();
        dao.close();

        ArrayAdapter<Etudiant> adapter = new ArrayAdapter<Etudiant>(this, android.R.layout.simple_list_item_1, etudiants);
        studentList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerListeEtudiants();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        MenuItem supprimer = menu.add("Supprimer");
        supprimer.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Etudiant etudiant = (Etudiant) studentList.getItemAtPosition(info.position);

                EtudiantDAO dao = new EtudiantDAO(MainActivity.this);
                dao.supprimer(etudiant);
                dao.close();
                chargerListeEtudiants();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
}