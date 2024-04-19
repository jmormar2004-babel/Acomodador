package com.babelgroup.acomodador.services;

public interface ISeatingService {
    void buscarLugar(int butacasParaEncajar);
    boolean comprobarButacasDeFila(int numFila, int butacasParaEncajar);
    int posicionPrioridad(int posicionReal);
}
