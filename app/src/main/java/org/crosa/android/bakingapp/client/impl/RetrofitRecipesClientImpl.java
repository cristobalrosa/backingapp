package org.crosa.android.bakingapp.client.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import org.crosa.android.bakingapp.client.RecipesClient;
import org.crosa.android.bakingapp.client.exceptions.RecipesClientException;
import org.crosa.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRecipesClientImpl implements RecipesClient {
    private static final String TAG = RetrofitRecipesClientImpl.class.getSimpleName();
    private static final String BASE_URL = " https://d17h27t6h515a5.cloudfront.net/";
    private final RetrofitRecipesClient retrofitService;

    public RetrofitRecipesClientImpl(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.retrofitService = retrofit.create(RetrofitRecipesClient.class);
    }

    public RetrofitRecipesClientImpl() {
        this(BASE_URL);
    }


    @Override
    public List<Recipe> getRecipes() throws RecipesClientException {
        RecipesClientException exception;
        try {
            Response<List<Recipe>> response = retrofitService.getRecipes().execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                exception = new RecipesClientException(response.errorBody().string());
            }
        } catch (Exception e) {
            exception = new RecipesClientException("Unable to parse recipe details", e);
        }
        throw exception;
    }

}
