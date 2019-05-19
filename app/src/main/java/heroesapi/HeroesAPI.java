package heroesapi;

import java.util.Map;

import model.Heroes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HeroesAPI {
    @POST("heroes")
    Call<Void>addHero(@Body Heroes heroes);


    //direct binding to the table field
    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Field("name") String name,@Field("desc") String desc);

    //using key and value
    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@FieldMap Map<String,String> map);

}
