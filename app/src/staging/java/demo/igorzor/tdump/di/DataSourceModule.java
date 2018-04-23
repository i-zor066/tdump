package demo.igorzor.tdump.di;

import dagger.Module;
import dagger.Provides;
import demo.igorzor.tdump.BuildConfig;
import demo.igorzor.tdump.base.BaseSchedulerProvider;
import demo.igorzor.tdump.data.API.QuoteService;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.Repository;
import demo.igorzor.tdump.data.helpers.SchedulerProvider;
import demo.igorzor.tdump.data.helpers.StringFormatHelper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataSourceModule {

    @Provides
    @PerActivity
    OkHttpClient provideOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY:
                HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

    }

    @Provides
    @PerActivity
    Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    @Provides
    @PerActivity
    QuoteService provideQuoteService() {
        return provideRetrofit(QuoteService.BASE_URL).create(QuoteService.class);
    }

    @Provides
    @PerActivity
    StringFormatHelper provideStringFormatHelper() {
        return new StringFormatHelper();
    }

    @Provides
    @PerActivity
    BaseSchedulerProvider provideScheduler() {
        return new SchedulerProvider();
    }

    @Provides
    @PerActivity
    DataSource provideDataSource() {
        return getRepository();
    }

    protected DataSource getRepository() {
        return new Repository(provideQuoteService(), provideStringFormatHelper(), provideScheduler());

    }

}
