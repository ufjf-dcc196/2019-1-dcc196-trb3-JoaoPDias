package com.ufjf.br.trabalho03.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.dao.AtividadeDAO;
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.dao.ProducaoDAO;
import com.ufjf.br.trabalho03.model.Atividade;
import com.ufjf.br.trabalho03.model.Candidato;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Producao;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        try {
            Categoria categoria = new Categoria().setDescricao("Categoria "+ RandomStringUtils.randomAlphabetic(3).toUpperCase());
            Candidato candidato = new Candidato().setNomeCandidato("Candidato "+RandomStringUtils.randomAlphabetic(3).toUpperCase())
                    .setDataNascimento("07/04/2019")
                    .setEmail("diasjp1997@gmail.com")
                    .setTelefone("24981552606")
                    .setPerfil("Desenvolvedor C# há 7 anos");
            candidato = CandidatoDAO.getInstance().Save(candidato, this);
            categoria=(CategoriaDAO.getInstance().Save(categoria,this));
            Producao producao = new Producao().
                    setCandidato(candidato)
                    .setDescricao("Desenvolvimento C#")
                    .setInicio("24/07/2019")
                    .setFim("25/09/2019")
                    .setHoras(80+candidato.getIdCandidato())
                    .setTitulo("Desenvolvimento C#").setCategoria(
                            categoria);
            producao = ProducaoDAO.getInstance().Save(producao, this);
            Atividade atividade = new Atividade().
                    setDataAtividade("24/07/2019")
                    .setDescricao("GraphQL")
                    .setHoras(20)
                    .setProducao(producao);
            AtividadeDAO.getInstance().Save(atividade, this);
            Cursor cursorAtividade,cursorProducao,cursorCategoria,cursorCandidato, cursorCandidatoHoras;
            cursorAtividade = AtividadeDAO.getInstance().getAll(this,producao.getIdProducao());
            cursorCandidato = CandidatoDAO.getInstance().getCandidatosSomaHoras(this,null);
            cursorCandidatoHoras = CandidatoDAO.getInstance().getCandidatosSomaHoras(this,categoria);
            cursorCategoria = CategoriaDAO.getInstance().getAll(this);
            cursorProducao = ProducaoDAO.getInstance().getAll(this,candidato.getIdCandidato());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_candidato: {
                Intent intent = new Intent(MainActivity.this, CandidatoListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_item_categoria: {
                Intent intent = new Intent(MainActivity.this, CategoriaListActivity.class);
                startActivity(intent);
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

