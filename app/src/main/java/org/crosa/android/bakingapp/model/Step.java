package org.crosa.android.bakingapp.model;

import java.io.Serializable;

import lombok.Value;

@Value
public class Step implements Serializable {
    private String id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailURL;
}
