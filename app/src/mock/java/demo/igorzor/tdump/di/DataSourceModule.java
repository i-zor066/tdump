package demo.igorzor.tdump.di;

import dagger.Module;
import dagger.Provides;
import demo.igorzor.tdump.BuildConfig;
import demo.igorzor.tdump.base.BaseSchedulerProvider;
import demo.igorzor.tdump.data.API.QuoteService;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.StubRepository;
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
    DataSource provideDataSource() {
        return new StubRepository();
    }



}
