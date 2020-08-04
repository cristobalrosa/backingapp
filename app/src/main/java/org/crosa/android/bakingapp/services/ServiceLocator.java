package org.crosa.android.bakingapp.services;

import android.content.Context;

import org.crosa.android.bakingapp.client.impl.RetrofitRecipesClientImpl;
import org.crosa.android.bakingapp.services.impl.RecipesServiceImpl;

public class ServiceLocator {

    private static ServiceLocator instance = null;
    private static RecipesService recipesService;

    private final Context context;

    private ServiceLocator(Context context) {
        this.context = context;
    }

    public static ServiceLocator getInstance(Context context) {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator(context);
                // Create all the instances at the same time go make them singleton too.
                recipesService = new RecipesServiceImpl(new RetrofitRecipesClientImpl(), context);
            }
        }
        return instance;
    }

    public RecipesService getRecipesService() {
        return recipesService;
    }

}