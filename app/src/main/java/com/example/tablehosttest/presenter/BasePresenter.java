package com.example.tablehosttest.presenter;

public interface BasePresenter<T> {

    void registerCallback(T callback);

    void unregisterCallback();
}
