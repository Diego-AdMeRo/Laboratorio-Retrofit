package co.edu.unipiloto.laboratorio_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvLista;
    private EditText txtId, txtIdAdd, txtUserIdAdd, txtTitleAdd, txtBodyAdd;
    private Button btnGetPosts, btnGetPost, btnHabilitarDatos, btnAgregarPost;
    private LinearLayout lnlEnvioDatos;
    private List<String> datos;
    private ArrayAdapter<String> adaptadorDatos;
    private InterfaceAPI interfaceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de Atributos
        this.btnGetPosts = (Button) findViewById(R.id.btn_get_posts);
        this.txtId = (EditText) findViewById(R.id.txt_id);
        this.btnGetPost = (Button) findViewById(R.id.btn_get_post);
        this.btnHabilitarDatos = (Button) findViewById(R.id.btn_habilitar_datos);
        this.lvLista = (ListView) findViewById(R.id.lista);
        this.lnlEnvioDatos = (LinearLayout) findViewById(R.id.lnl_datos);

        this.txtIdAdd = (EditText) findViewById(R.id.txt_id_add);
        this.txtUserIdAdd = (EditText) findViewById(R.id.txt_userid_add);
        this.txtTitleAdd = (EditText) findViewById(R.id.txt_title_add);
        this.txtBodyAdd = (EditText) findViewById(R.id.txt_body_add);
        this.btnAgregarPost = (Button) findViewById(R.id.btn_agregar_post);

        //Acción Botones
        this.btnGetPosts.setOnClickListener(this);
        this.btnGetPost.setOnClickListener(this);
        this.btnHabilitarDatos.setOnClickListener(this);
        this.btnAgregarPost.setOnClickListener(this);

        //Configuración Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.interfaceAPI = retrofit.create(InterfaceAPI.class);

        //Configuración ListView
        this.datos = new ArrayList<>();
        this.adaptadorDatos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        this.lvLista.setAdapter(adaptadorDatos);
    }

    private void visibilidad(boolean visibilidadLista) {
        if (visibilidadLista) {
            this.lvLista.setVisibility(View.VISIBLE);
            this.lnlEnvioDatos.setVisibility(View.GONE);
        } else {
            this.lvLista.setVisibility(View.GONE);
            this.lnlEnvioDatos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnGetPosts.getId()) {
            visibilidad(true);
            getPosts();
        } else if (v.getId() == btnGetPost.getId()) {
            if (!txtId.getText().toString().equals("")) {
                getPost(Integer.parseInt(txtId.getText().toString()));
                visibilidad(true);
            } else
                txtId.setError("Dato Requerido");
        } else if (v.getId() == btnHabilitarDatos.getId()) {
            visibilidad(false);
        } else if (v.getId() == btnAgregarPost.getId()) {
            if (!txtIdAdd.getText().toString().equals("") && !txtUserIdAdd.getText().toString().equals("")
                    && !txtTitleAdd.getText().toString().equals("") && !txtBodyAdd.getText().toString().equals("")) {
                setPost(new Entity(Integer.parseInt(txtIdAdd.getText().toString()),
                        Integer.parseInt(txtUserIdAdd.getText().toString()),
                        txtTitleAdd.getText().toString(),
                        txtBodyAdd.getText().toString()));
            } else {
                if (txtIdAdd.getText().toString().equals(""))
                    txtIdAdd.setError("Dato Requerido");
                if (txtUserIdAdd.getText().toString().equals(""))
                    txtUserIdAdd.setError("Dato Requerido");
                if (txtTitleAdd.getText().toString().equals(""))
                    txtTitleAdd.setError("Dato Requerido");
                if (txtBodyAdd.getText().toString().equals(""))
                    txtBodyAdd.setError("Dato Requerido");
            }
        }
    }

    private void getPosts() {
        Call<List<Entity>> call = interfaceAPI.getPosts();
        call.enqueue(new Callback<List<Entity>>() {
            @Override
            public void onResponse(Call<List<Entity>> call, Response<List<Entity>> response) {
                datos.clear();
                for (Entity entity : response.body()) {
                    datos.add(entity.toString());
                }
                adaptadorDatos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Entity>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de Conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPost(int id) {
        Call<List<Entity>> call = interfaceAPI.getPost(id);
        System.out.println(call.request().url().toString());
        call.enqueue(new Callback<List<Entity>>() {
            @Override
            public void onResponse(Call<List<Entity>> call, Response<List<Entity>> response) {
                datos.clear();
                for (Entity entity : response.body()) {
                    datos.add(entity.toString());
                }
                adaptadorDatos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Entity>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de Conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPost(Entity entity) {
        Call<Entity> call = interfaceAPI.setPost(entity);
        System.out.println(call.request().url().toString());
        call.enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                Toast.makeText(MainActivity.this, "Elemento Agregado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en el Agregado", Toast.LENGTH_SHORT).show();
            }
        });
    }

}