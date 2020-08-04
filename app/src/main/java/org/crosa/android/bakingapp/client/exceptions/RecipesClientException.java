package org.crosa.android.bakingapp.client.exceptions;

public class RecipesClientException extends Exception {
    public RecipesClientException(String message) {
        super(message);
    }

    public RecipesClientException(String message, Throwable e) {
        super(message, e);
    }
}
