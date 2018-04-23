package demo.igorzor.tdump.data.API;

import demo.igorzor.tdump.data.model.remote.quotelistresponse.QuoteListResponse;
import demo.igorzor.tdump.data.model.remote.taglistresponse.TagListResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuoteService {

    String BASE_URL = "https://api.tronalddump.io/";

    @GET("tag")
    Single<TagListResponse> getTagList();

    @GET("tag/{tagname}")
    Single<QuoteListResponse> getQuotesForTag(@Path("tagname") String tagName);

}
