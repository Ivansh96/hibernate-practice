package com.ivansh.mapper;

public interface CustomMapper<F, T> {
    T mapFrom(F object);
}
