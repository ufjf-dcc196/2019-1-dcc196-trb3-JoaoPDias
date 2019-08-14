package com.ufjf.br.trabalho03.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.model.Categoria;

public class CandidatoListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public CandidatoAdapter adapter;
    private RecyclerView rv;
    private static final int REQUEST_CANDIDATO_CADASTRAR = 1;
    private static final int REQUEST_CANDIDATO_EDITAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_list);
        this.toolbar = findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        this.rv = findViewById(R.id.recyclerCandidato);
        this.adapter = new CandidatoAdapter(CandidatoDAO.getInstance().getCandidatosSomaHoras(this, null), null);
        adapter.setOnCandidatoClickListener(new CandidatoAdapter.OnCandidatoClickListener() {
            @Override
            public void onCandidatoClick(View candidatoView, int position) {
                Intent intent = new Intent(CandidatoListActivity.this, DetalhesCandidatoActivity.class);
                intent.putExtra("candidato", adapter.getCandidato(position));
                startActivityForResult(intent, CandidatoListActivity.REQUEST_CANDIDATO_EDITAR);
            }
        });
        rv.setAdapter(adapter);
        rv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        rv.setLayoutManager(new LinearLayoutManager(this));
        Button botaoCandidato = findViewById(R.id.buttonCadastrarCandidato);
        botaoCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CandidatoListActivity.this, CandidatoInsertActivity.class);
                startActivityForResult(intent, CandidatoListActivity.REQUEST_CANDIDATO_CADASTRAR);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_candidato: {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            }
            case R.id.nav_item_categoria: {
                Intent intent = new Intent(CandidatoListActivity.this, CategoriaListActivity.class);
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
        if (requestCode == CandidatoListActivity.REQUEST_CANDIDATO_CADASTRAR) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                this.adapter.setCursor(CandidatoDAO.getInstance().getCandidatosSomaHoras(this, null));
            }
        } else if (requestCode == CandidatoListActivity.REQUEST_CANDIDATO_EDITAR) {
            this.adapter.setCursor(CandidatoDAO.getInstance().getCandidatosSomaHoras(this, null));
        }

    }


}

