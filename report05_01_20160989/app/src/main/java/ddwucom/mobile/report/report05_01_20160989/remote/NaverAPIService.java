package ddwucom.mobile.report.report05_01_20160989.remote;

import ddwucom.mobile.report.report05_01_20160989.model.NaverSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NaverAPIService {

    @GET ("/v1/search/local.{type}")
    Call<NaverSearchResult> getNaverSearchItemsResult(@Path("type") String type,
                                                      @Header("X-Naver-Client-Id") String id, @Header("X-Naver-Client-Secret") String secret,
                                                      @Query("query") String query);
}
