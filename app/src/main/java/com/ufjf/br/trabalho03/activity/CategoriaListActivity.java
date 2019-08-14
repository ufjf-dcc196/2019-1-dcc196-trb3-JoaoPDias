package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.adapter.CategoriaAdapter;
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.model.Categoria;

public class CategoriaListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CategoriaAdapter adapter;
    private static final int REQUEST_CATEGORIA_CADASTRAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_list);
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        final RecyclerView rv = findViewById(R.id.recyclerCategoria);
        this.adapter = new CategoriaAdapter(CategoriaDAO.getInstance().getAll(this));

        adapter.setOnCategoriaClickListener(new CategoriaAdapter.OnCategoriaClickListener() {
            @Override
            public void onCategoriaClick(View categoriaView, int position) {
                Categoria categoria = adapter.getCategoria(position);
                Cursor cursor = CandidatoDAO.getInstance().getCandidatosSomaHoras(CategoriaListActivity.this, categoria);
                if (cursor.getCount() > 0) {
                    Intent intent = new Intent(CategoriaListActivity.this, CandidatoListViewActivity.class);
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                } else {
                    Toast.makeText(CategoriaListActivity.this,
                            "Não há Candidatos vinculados a essa Categoria",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        rv.setAdapter(adapter);
        rv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        rv.setLayoutManager(new LinearLayoutManager(this));
        Button botaoCategoria = findViewById(R.id.buttonCadastrarCategoria);
        botaoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriaListActivity.this, CategoriaInsertActivity.class);
                startActivityForResult(intent, CategoriaListActivity.REQUEST_CATEGORIA_CADASTRAR);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_candidato: {
                Intent intent = new Intent(CategoriaListActivity.this, CandidatoListActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            case R.id.nav_item_categoria: {
                Intent intent = getIntent();
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
        if (requestCode == CategoriaListActivity.REQUEST_CATEGORIA_CADASTRAR) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                this.adapter.setCursor(CategoriaDAO.getInstance().getAll(CategoriaListActivity.this));
            }
        }
    }
}



