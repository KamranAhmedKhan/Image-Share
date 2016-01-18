package com.crystalnet.imageshare;

/**
 * Created by panacloud on 1/18/16.
 */
public interface ServiceListener<T> {

    void success(T obj);
    void error();
}
