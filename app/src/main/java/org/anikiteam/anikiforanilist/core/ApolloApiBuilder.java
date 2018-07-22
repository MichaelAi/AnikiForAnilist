package org.anikiteam.anikiforanilist.core;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy;
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;

/**
 * Created by Mike Ai on 21-Jul-18.
 */

public class ApolloApiBuilder {

    private long timeout;
    private boolean forceSSL;
    private String baseUrl;
    private String language;
    private boolean enableLogging;


    public ApolloApiBuilder setTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public ApolloApiBuilder forceSSL(boolean forceSSL){
        this.forceSSL = forceSSL;
        return this;
    }

    public ApolloApiBuilder setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }

    public ApolloApiBuilder setLanguage(String language){
        this.language = language;
        return this;
    }

    public ApolloApiBuilder enableLogging(boolean enableLogging){
        this.enableLogging = enableLogging;
        return this;
    }

    public ApolloClient build() {
        return ApolloClient.builder()
                .serverUrl(baseUrl)
                .okHttpClient(okHttpClient())
                .normalizedCache(normalizedCacheFactory(), cacheKeyResolver())
                .build();
    }

    private CacheKeyResolver cacheKeyResolver(){
        return new CacheKeyResolver() {
            @NotNull
            @Override
            public CacheKey fromFieldRecordSet(@NotNull ResponseField field, @NotNull Map<String, Object> recordSet) {
                String typeName = (String) recordSet.get("__typename");
                if ("User".equals(typeName)) {
                    String userKey = typeName + "." + recordSet.get("login");
                    return CacheKey.from(userKey);
                }
                if (recordSet.containsKey("id")) {
                    String typeNameAndIDKey = recordSet.get("__typename") + "." + recordSet.get("id");
                    return CacheKey.from(typeNameAndIDKey);
                }
                return CacheKey.NO_KEY;
            }

            // Use this resolver to customize the key for fields with variables: eg entry(repoFullName: $repoFullName).
            // This is useful if you want to make query to be able to resolved, even if it has never been run before.
            @NotNull @Override
            public CacheKey fromFieldArguments(@NotNull ResponseField field, @NotNull Operation.Variables variables) {
                return CacheKey.NO_KEY;
            }
        };
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

    private NormalizedCacheFactory normalizedCacheFactory() {
        return new LruNormalizedCacheFactory(EvictionPolicy.NO_EVICTION);
    }
}
