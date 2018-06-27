
package com.producthuntpost.model.collections;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.Collection;


public class CollectionsPostModel implements Serializable{

    @SerializedName("collections")
    @Expose
    private List<Collection> collections = null;

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

}
