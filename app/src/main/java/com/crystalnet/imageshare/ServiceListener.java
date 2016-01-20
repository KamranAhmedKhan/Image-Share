package com.crystalnet.imageshare;

/**
 * Created by panacloud on 1/18/16.
 */
public interface ServiceListener<S,E> {

    void success(S obj);
    void error(E obj);
}
