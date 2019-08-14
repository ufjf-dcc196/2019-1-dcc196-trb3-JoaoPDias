package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.ufjf.br.trabalho03.adapter.AtividadeAdapter;
import com.ufjf.br.trabalho03.dao.AtividadeDAO;
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.model.Producao;

import java.text.ParseException;

public class DetalhesProducaoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_PRODUCAO_EDITAR = 1;
    private static final int REQUEST_MODIFICAR_ATIVIDADE = 2;
    private static final int REQUEST_ADICIONAR_ATIVIDADE = 3;
    private Toolbar toolbar;
    private Producao producao;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView rv;
    private AtividadeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_producao_list);
        this.toolbar = findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.producao = (Producao) bundle.get("producao");
        }
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        this.rv = findViewById(R.id.recyclerAtividade);
        this.adapter = new AtividadeAdapter(AtividadeDAO.getInstance().getAll(this, producao.getIdProducao()), producao);
        adapter.setOnAtividadeClickListener(new AtividadeAdapter.OnAtividadeClickListener() {
            @Override
            public void onAtividadeClick(View atividadeView, int position) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, AtividadeEditActivity.class);
                try {
                    intent.putExtra("atividade", adapter.getAtividade(position));
                    startActivityForResult(intent, DetalhesProducaoActivity.REQUEST_MODIFICAR_ATIVIDADE);
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
        Button botaoProducaoEditar = findViewById(R.id.buttonEditarProducao);
        botaoProducaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, ProducaoEditActivity.class);
                intent.putExtra("producao", producao);
                startActivityForResult(intent, DetalhesProducaoActivity.REQUEST_PRODUCAO_EDITAR);
            }
        });
        Button botaoAdicionarAtividade = findViewById(R.id.buttonAdicionarAtividade);
        botaoAdicionarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesProducaoActivity.this, AtividadeInsertActivity.class);
                intent.putExtra("producao", producao);
                startActivityForResult(intent, DetalhesProducaoActivity.REQUEST_MODIFICAR_ATIVIDADE);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_candidato: {
                Intent intent = new Intent(DetalhesProducaoActivity.this, DetalhesProducaoActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            case R.id.nav_item_categoria: {
                Intent intent = new Intent(DetalhesProducaoActivity.this, CategoriaListActivity.class);
                finish();
                startActivity(intent);
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DetalhesProducaoActivity.REQUEST_ADICIONAR_ATIVIDADE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                this.adapter.setCursor(AtividadeDAO.getInstance().getAll(this, producao.getIdProducao()), producao);
            }
        } else if (requestCode == DetalhesProducaoActivity.REQUEST_MODIFICAR_ATIVIDADE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Edição realizada com sucesso", Toast.LENGTH_SHORT).show();
                this.adapter.setCursor(AtividadeDAO.getInstance().getAll(this, producao.getIdProducao()), producao);
            }
        } else if (requestCode == DetalhesProducaoActivity.REQUEST_PRODUCAO_EDITAR) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Edição realizado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }

    }
}
