package com.jwzhu.platform.plugs.jsonEscape.base;

import javax.annotation.Resource;

import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscapeInterface;


public abstract class JsonEscapeCacheInterface<T> implements JsonEscapeInterface<T> {

    @Resource
    private JsonEscapeCacheUtil jsonEscapeCacheUtil;

    public Object getMessage(T id) {
        String cacheHashKey = id.toString();
        Object messages = jsonEscapeCacheUtil.get(this.getClass(), cacheHashKey, String[].class);
        if(messages != null){
            return messages;
        }

        messages = getFormDB(id);
        jsonEscapeCacheUtil.add(this.getClass(), cacheHashKey, messages);
        return messages;
    }

    public Object getFormDB(T id){
        return null;
    }

}
