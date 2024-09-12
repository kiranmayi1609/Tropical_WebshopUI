package dk.tec.tropical_webshop.Product;

import java.util.List;

import dk.tec.tropical_webshop.DataModel.Product;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApi  {
    @GET("getAllProducts")
    Call<List<Product>> getProducts();

    @GET("/getProductById/{id}")
    Call<Product> getProductById(@Path("id") int id);


    @POST("/addProduct")
    Call<ResponseBody> uploadProductWithImageUrl(@Body Product product);

    @PUT("/updateProductById/{id}")
    Call<Void> updateProduct(@Body Product product);

    @DELETE("/deleteBookById/{id}")
    Call<Void> deleteProduct(@Path("id") int id);


}
