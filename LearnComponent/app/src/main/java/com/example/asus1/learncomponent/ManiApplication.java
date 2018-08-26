package com.example.asus1.learncomponent;

import android.app.Application;
import android.print.PrinterId;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.componentbase.BaseApp;
import com.example.componentbase.BaseConfig;

public class ManiApplication extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();

        if(isDebug()){
            ARouter.openLog();

            ARouter.openDebug();
        }

        ARouter.init(this);

        initModuleApp(this);
        initModuleData(this);
    }

    private boolean isDebug(){
        return BuildConfig.DEBUG;
    }

    @Override
    public void initModuleApp(Application application) {
        for(String moduleApp: BaseConfig.moduleApps){
            try {
                Class clazz  = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp)clazz.newInstance();
                baseApp.initModuleApp(this);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InstantiationException e){
                e.printStackTrace();

            }


        }

    }

    @Override
    public void initModuleData(Application application) {
        for(String moduleApp: BaseConfig.moduleApps){
            try {
                Class clazz  = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp)clazz.newInstance();
                baseApp.initModuleData(this);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InstantiationException e){
                e.printStackTrace();

            }


        }
    }
}
