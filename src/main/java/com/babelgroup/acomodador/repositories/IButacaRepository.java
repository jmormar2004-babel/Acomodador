package com.babelgroup.acomodador.repositories;

public interface IButacaRepository {
    void init();
    boolean[][] exportButacas();
    void setButacas(boolean[][] butacas);
}
