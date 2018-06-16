package com.felix.simplebook.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.felix.simplebook.R;
import com.felix.simplebook.callback.ICallBack;
import com.felix.simplebook.database.TypeBean;
import com.felix.simplebook.model.HomeModel;
import com.felix.simplebook.model.IHomeModel;
import com.felix.simplebook.utils.timer.GetTime;
import com.felix.simplebook.view.IHomeView;

/**
 * Created by chaofei.xue on 2018/1/2.
 */

public class HomePresenter implements IHomePresenter {
    private IHomeModel homeModel;
    private IHomeView homeView;
    private String year, month, day;
    private Context context;

    public HomePresenter(IHomeView homeView, Context context) {
        this.homeView = homeView;
        this.context = context;
        homeModel = new HomeModel();

        String time = GetTime.get();
        String[] times = time.split("\\.");
        year = times[0];
        month = times[1];
        day = times[2];

        initType();
    }

    @Override
    public void query() {
        homeModel.queryData(new ICallBack<String[]>() {
            @Override
            public void successful(String[] strings) {
                homeView.setQueryResult(strings);
            }

            @Override
            public void error(String value) {

            }
        }, year, month, day);
    }

    public void initType(){
        SharedPreferences preferences = context.getSharedPreferences("config.sb",
                Context.MODE_PRIVATE);
        boolean flag = preferences.getBoolean("first_read", false);
        if (!flag) {
            TypeBean typeBean1 = new TypeBean();
            typeBean1.setType(context.getResources().getString(R.string.type_buy));
            typeBean1.save();
            TypeBean typeBean2 = new TypeBean();
            typeBean2.setType(context.getResources().getString(R.string.type_food));
            typeBean2.save();
            TypeBean typeBean3 = new TypeBean();
            typeBean3.setType(context.getResources().getString(R.string.type_money));
            typeBean3.save();
            TypeBean typeBean5 = new TypeBean();
            typeBean5.setType(context.getResources().getString(R.string.type_bus));
            typeBean5.save();
            TypeBean typeBean6 = new TypeBean();
            typeBean6.setType(context.getResources().getString(R.string.type_other));
            typeBean6.save();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_read", true).commit();
        }
    }
}
