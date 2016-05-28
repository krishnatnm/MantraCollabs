package com.mantra.chatatmantra.view.activities;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mantra.chatatmantra.R;
import com.mantra.chatatmantra.utils.DateTimeSerializer;
import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import org.joda.time.DateTime;

import lombok.Getter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by rajat on 28/05/16.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ChatBean {

    @Getter(lazy = true)
    private final Gson gson = createGson();

    @Getter(lazy = true)
    private final OkHttpClient httpClient = createOkHttpClient();

    @Getter
    @StringRes(R.string.url)
    String url;
    private Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(new TypeToken<DateTime>() {
                }.getType(), new DateTimeSerializer())
                .create();
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient();
    }
    @Getter(lazy = true)
    private final RestAdapter restAdapter = createRestAdapter();


    private RestAdapter createRestAdapter() {
        return new RestAdapter.Builder()
                .setClient(new OkClient(getHttpClient()))
                .setConverter(new GsonConverter(getGson()))
                .setEndpoint(url)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("app_version", "1.0");
                    }
                })
                .build();
    }
}
