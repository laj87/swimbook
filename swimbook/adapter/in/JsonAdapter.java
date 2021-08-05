package com.swimbook.swimbook.adapter.in;

import com.google.gson.Gson;


public class JsonAdapter {

    private Gson gson;

    public JsonAdapter(){
        this.gson = new Gson();
    }

    public String toJSON(Object obj) {
        return this.gson.toJson(obj);
    }


}
