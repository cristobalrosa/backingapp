package org.crosa.android.bakingapp.model;

import lombok.Value;

@Value
public class Step {
    private String id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailURL;
}
