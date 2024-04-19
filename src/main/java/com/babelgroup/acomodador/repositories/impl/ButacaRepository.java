package com.babelgroup.acomodador.repositories.impl;

import com.babelgroup.acomodador.repositories.IButacaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ButacaRepository implements IButacaRepository {

    private boolean[][] butacas;

    @Override
    public void init() {
        this.butacas = new boolean[9][10];
    }

    @Override
    public boolean[][] exportButacas() {
        return this.butacas;
    }

    @Override
    public void setButacas(boolean[][] butacas) {
        this.butacas = butacas;
    }
}
