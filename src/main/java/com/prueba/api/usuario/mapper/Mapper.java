package com.prueba.api.usuario.mapper;

@FunctionalInterface
public interface Mapper<T, V> {
    V map(T request);
}

