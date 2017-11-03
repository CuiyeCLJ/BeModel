package android.bemodel.com.bemodel.ui.modelcircle;

import android.bemodel.com.bemodel.base.BasePresenter;
import android.bemodel.com.bemodel.base.BaseView;

/**
 * Created by Lifu.Zheng on 2017.10.29.
 */

public interface ModelCircleContract {

    interface View<M> extends BaseView<Presenter> {
        /**
         * Display a loading view while loading data in background
         * The loading view must have the id = R.id.pb_loadingView
         * @param pullToRefresh true, if pullToRefresh has been invoked loading.
         */
        public void showLoading(boolean pullToRefresh);

        /**
         * Show the content view
         * The content view must have the id = R.id.srl_contentView
         */
        public void showContent();

        /**
         * Show the error view.
         * The error view must be a TextView with the id = R.id.tv_errorView
         * @param e The Throwable that has caused this error
         * @param pullToRefresh true, if the exception was thrown during pull-to-refresh, otherwise false.
         */
        public void showError(Throwable e, boolean pullToRefresh);

        /**
         * The data that should be displayed with {@link #showContent()}
         * @param data
         */
        public void setData(M data);

        public void loadData(boolean pullToRefresh);
    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        public void updateStatus(boolean pullToRefresh);

    }
}
