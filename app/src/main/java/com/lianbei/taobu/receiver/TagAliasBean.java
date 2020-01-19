package com.lianbei.taobu.receiver;

import java.util.Set;

public class TagAliasBean {
    int action;
    Set<String> tags;
    String alias;
    boolean isAliasAction;

    @Override
    public String toString() {
        return "TagAliasBean{" +
                "action=" + action +
                ", tags=" + tags +
                ", alias='" + alias + '\'' +
                ", isAliasAction=" + isAliasAction +
                '}';
    }
}
