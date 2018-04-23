package demo.igorzor.tdump.di;

import dagger.Component;
import demo.igorzor.tdump.ui.details.QuoteListFragment;
import demo.igorzor.tdump.ui.landing.LandingActivity;
import demo.igorzor.tdump.ui.tagcloud.TagCloudFragment;

@PerActivity
@Component(modules = {DataSourceModule.class})
public interface AppComponent {

    void inject(LandingActivity landingActivity);
    void inject(TagCloudFragment tagCloudFragment);
    void inject(QuoteListFragment quoteListFragment);

}
