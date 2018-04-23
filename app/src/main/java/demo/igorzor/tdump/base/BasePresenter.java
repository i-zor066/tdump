package demo.igorzor.tdump.base;

import demo.igorzor.tdump.data.DataSource;

public interface BasePresenter {
    void create();
    void destroy();
    void resume();
    BaseView getView();
    DataSource getRepository();


}
