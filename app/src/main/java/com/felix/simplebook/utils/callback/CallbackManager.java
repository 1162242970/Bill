package com.felix.simplebook.utils.callback;


import java.util.WeakHashMap;

/**
 * 全局回调管理类
 */

public class CallbackManager {

    private static final WeakHashMap<Object, IGlobalCallback> CALLBACKS = new WeakHashMap<>();

    private CallbackManager() {

    }

    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object tag, IGlobalCallback callback) {
        CALLBACKS.put(tag, callback);
        return this;
    }

    public IGlobalCallback getCallback(Object tag) {
        return CALLBACKS.get(tag);
    }
}