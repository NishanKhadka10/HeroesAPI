package heroesapi;

import com.heroesapi.LoginSignupResponse;

import java.util.Map;

import model.Heroes;
import model.ImageResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HeroesAPI {
    @POST("heroes")
    Call<Void> addHero(@Header("Cookie") String cookie, @Body Heroes heroes);


    //direct binding to the table field
    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Header("Cookie") String cookie, @Field("name") String name, @Field("desc") String desc);

    //using key and value
    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Header("Cookie") String cookie, @FieldMap Map<String, String> map);

    //For uploading Image
    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Header("Cookie") String cookie, @Part MultipartBody.Part img);

//    @GET("heroes")
//    Call<List<Heroes>> getAllHeroes((@Header ("Cookie") String cookie);

    //For Login
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginSignupResponse> checkUser(@Field("username") String username, @Field("password") String password);

    //For Register
    @FormUrlEncoded
    @POST("users/signup")
    Call<LoginSignupResponse> registerUser(@Field("username") String username, @Field("password") String password);
}
