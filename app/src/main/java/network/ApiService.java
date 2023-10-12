package network;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("search.json") // Note the added .json here
    Call<ApiResponse> searchGoogle(@QueryMap Map<String, String> parameters);
}
