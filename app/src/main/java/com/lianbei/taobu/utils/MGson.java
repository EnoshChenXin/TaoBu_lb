package com.lianbei.taobu.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lianbei.taobu.mine.model.MemberInfo;

import java.util.Map;

public class MGson {


    public MGson(String model){
    }
    public MGson(){

    }


    public Gson Gson(){
        Gson gson = new GsonBuilder ().registerTypeAdapter(new TypeToken <Map <String, Object>> (){
        }.getType(), new GsonTypeAdapter ()).create();
        return gson;
    }

}
