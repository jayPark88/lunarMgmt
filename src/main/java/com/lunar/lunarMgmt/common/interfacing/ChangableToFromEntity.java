package com.lunar.lunarMgmt.common.interfacing;

public interface ChangableToFromEntity<E> {
    public E to();
    public void from (E entity);
}