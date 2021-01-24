package co.edu.unipiloto.laboratorio_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceAPI {

    String ruta = "/posts";

    @GET(ruta)
    Call<List<Entity>> getPosts();

    @GET(ruta + "/")
    Call<List<Entity>> getPost(@Query("id") int id);

    @POST(ruta)
    Call<Entity> setPost(@Body Entity entity);


}
