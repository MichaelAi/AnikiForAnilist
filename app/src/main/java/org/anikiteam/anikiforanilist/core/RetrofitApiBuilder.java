package org.anikiteam.anikiforanilist.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

/**
 * Builds a retrofit api object to execute network calls on
 */
public class RetrofitApiBuilder {

    private  Retrofit retrofit;
    private long timeout;
    private boolean forceSSL;
    private String baseUrl;
    private String language;
    private boolean enableLogging;

    public RetrofitApiBuilder setTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public RetrofitApiBuilder forceSSL(boolean forceSSL){
        this.forceSSL = forceSSL;
        return this;
    }

    public RetrofitApiBuilder setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitApiBuilder setLanguage(String language){
        this.language = language;
        return this;
    }

    public RetrofitApiBuilder enableLogging(boolean enableLogging){
        this.enableLogging = enableLogging;
        return this;
    }

    public <T> T build(Class<T> clazz) {
        retrofit = retrofit(okHttpClient(), httpUrl(baseUrl));
        return retrofit.create(clazz);
    }

    private HttpUrl httpUrl(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            return HttpUrl.get(url);
        } catch (MalformedURLException e) {
            throw new InvalidParameterException();
        }
    }

    private OkHttpClient.Builder okHttpClientBuilder() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (forceSSL) {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_0)
                    .allEnabledCipherSuites()
                    .build();
            okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));
        }

        okHttpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            request = request.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Language", language)
                    .build();
            return chain.proceed(request);
        });
        return okHttpClientBuilder;
    }

    private OkHttpClient okHttpClient() {
        return okHttpClientBuilder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
    }

    private Retrofit retrofit(OkHttpClient okHttpClient, HttpUrl baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(jacksonConverterFactory())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();
    }

    private JacksonConverterFactory jacksonConverterFactory(){
        //Configure Jackson
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .setPropertyNamingStrategy(SNAKE_CASE);
        return JacksonConverterFactory.create(objectMapper);
    }
}
