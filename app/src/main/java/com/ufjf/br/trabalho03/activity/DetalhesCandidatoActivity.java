package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.adapter.CandidatoAdapter;
import com.ufjf.br.trabalho03.adapter.ProducaoAdapter;
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.dao.ProducaoDAO;
import com.ufjf.br.trabalho03.model.Candidato;

import java.text.ParseException;

public class DetalhesCandidatoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_CANDIDATO_EDITAR = 1;
    private static final int REQUEST_ADICIONAR_PRODUCAO = 2;
    private static final int REQUEST_EDITAR_PRODUCAO = 3;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProducaoAdapter adapter;
    private Candidato candidato;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_candidato_list);
        this.toolbar = findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.candidato = (Candidato) bundle.get("candidato");
        }
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        this.rv = findViewById(R.id.recyclerProducao);
        this.adapter = new ProducaoAdapter(ProducaoDAO.getInstance().getAll(this, candidato.getIdCandidato()), candidato, CategoriaDAO.getInstance().getCategorias(this));
        adapter.setOnProducaoClickListener(new ProducaoAdapter.OnProducaoClickListener() {
            @Override
            public void onProducaoClick(View producaoView, int position) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, DetalhesProducaoActivity.class);
                try {
                    intent.putExtra("producao", adapter.getProducao(position));
                    startActivityForResult(intent, DetalhesCandidatoActivity.REQUEST_EDITAR_PRODUCAO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        rv.setAdapter(adapter);
        rv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        rv.setLayoutManager(new LinearLayoutManager(this));
        Button botaoCandidatoEditar = findViewById(R.id.buttonEditarCandidato);
        botaoCandidatoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, CandidatoEditActivity.class);
                intent.putExtra("candidato", candidato);
                startActivityForResult(intent, DetalhesCandidatoActivity.REQUEST_CANDIDATO_EDITAR);
            }
        });
        Button botaoAdicionarProducao = findViewById(R.id.buttonAdicionarProducao);
        botaoAdicionarProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, ProducaoInsertActivity.class);
                intent.putExtra("candidato", candidato);
                startActivityForResult(intent, DetalhesCandidatoActivity.REQUEST_ADICIONAR_PRODUCAO);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_candidato: {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, CandidatoListActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            case R.id.nav_item_categoria: {
                Intent intent = new Intent(DetalhesCandidatoActivity.this, CategoriaListActivity.class);
                finish();
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DetalhesCandidatoActivity.REQUEST_ADICIONAR_PRODUCAO) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Cadastro da produção realizado com sucesso", Toast.LENGTH_SHORT).show();
                this.adapter.setCursor(ProducaoDAO.getInstance().getAll(DetalhesCandidatoActivity.this, candidato.getIdCandidato()), candidato);
            }
        } else if (requestCode == DetalhesCandidatoActivity.REQUEST_CANDIDATO_EDITAR) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Edição realizado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else if (requestCode == DetalhesCandidatoActivity.REQUEST_EDITAR_PRODUCAO) {
            if(resultCode == Activity.RESULT_OK){
                this.adapter.setCursor(ProducaoDAO.getInstance().getAll(DetalhesCandidatoActivity.this, candidato.getIdCandidato()), candidato);
            }
        }
    }

}
