package demo.igorzor.tdump.data.helpers;

import demo.igorzor.tdump.base.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return new TestScheduler();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return new TestScheduler();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return new TestScheduler();
    }
}
