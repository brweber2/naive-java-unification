package com.brweber2.unification;

/**
 * @author brweber2
 * Copyright: 2012
 */
public interface Constant<T> {
    // todo should we just use equals???
    boolean same(T t);
}
