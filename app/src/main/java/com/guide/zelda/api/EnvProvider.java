package com.guide.zelda.api;


import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.guide.zelda.R;
import com.guide.zelda.common.utils.PreferenceFactory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class EnvProvider implements Provider<EnvData>, Env {

    private final Map<Integer, EnvData> envDataMap = new ArrayMap<>();
    private PreferenceFactory factory;

    @Inject
    public EnvProvider(Context context, PreferenceFactory factory) {
        this.factory = factory;
        envDataMap.put(DEV, new EnvData(context.getString(R.string.dev_url)));
        envDataMap.put(STAG, new EnvData(context.getString(R.string.stag_url)));
        envDataMap.put(PROD, new EnvData(context.getString(R.string.prod_url)));
    }

    @Override
    public EnvData get() {
        ApiConfig config = factory.create(ApiConfig.class);
        int setEnv = config.getEnv();
//        if (setEnv == UNSET) {
//            return envDataMap.get(BuildConfig.ENV_VALUE);
//        } else {
            return envDataMap.get(setEnv);
//        }
    }

}