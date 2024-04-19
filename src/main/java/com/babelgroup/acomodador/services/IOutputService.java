package com.babelgroup.acomodador.services;

public interface IOutputService {
    void imprimirMenu();
    void imprimirButacas();
    void imprimirButacas(int fila, boolean[] butacasReservadas);
    void guardarButacas(String path);
}
