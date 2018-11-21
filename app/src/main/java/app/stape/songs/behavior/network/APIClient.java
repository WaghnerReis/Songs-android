package app.stape.songs.behavior.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit mRetrofit;
    private static final String BASE_URL = "https://api.deezer.com/";

    public static APIRequests getInstance() {
        if (mRetrofit == null) {
            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit.create(APIRequests.class);
    }
}
