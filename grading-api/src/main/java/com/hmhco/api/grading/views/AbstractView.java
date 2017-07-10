package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by srikanthk on 4/24/17.
 */
public class AbstractView {

    @JsonIgnore
    private String version;

    public String getVersion(){return version;}

    public void setVersion(String version){this.version = version;}
}
