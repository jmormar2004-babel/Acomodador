package com.babelgroup.acomodador.services.impl;

import com.babelgroup.acomodador.repositories.IButacaRepository;
import com.babelgroup.acomodador.services.IInputService;
import com.babelgroup.acomodador.services.IOutputService;
import com.babelgroup.acomodador.services.ISeatingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SeatingService implements ISeatingService {

    private final IButacaRepository butacaRepository;
    private boolean[][] butacas;

    private final IOutputService outputService;
    private final IInputService inputService;

    public SeatingService(IButacaRepository butacaRepository, IOutputService outputService, IInputService inputService){
        this.butacaRepository = butacaRepository;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    @Override
    public void buscarLugar(int butacasParaEncajar) {

        if(butacasParaEncajar>10){
            System.out.println("Si quieres reservar más de 10, subdivídelo para que no supere 10.");
        }

        this.butacas = butacaRepository.exportButacas();

        if(this.butacas == null){
            butacaRepository.init();
            this.butacas = butacaRepository.exportButacas();
        }

        for (int i = 0; i < this.butacas.length; i++) {
            if(comprobarButacasDeFila(i, butacasParaEncajar)) return;
        }

        System.out.println("Lo sentimos, no hemos encontrado sitio");

    }

    @Override
    public boolean comprobarButacasDeFila(int numFila, int butacasParaEncajar) {
        boolean[] fila = Arrays.copyOf(butacas[numFila], butacas[numFila].length);

        if(!fila[posicionPrioridad(0)]) {
            for (int j = 0; j < butacasParaEncajar; j++) {
                fila[posicionPrioridad(j)] = true;
            }

            outputService.imprimirButacas(numFila, fila);
            System.out.println("¿Le parece bien? (y/n)");
            if (inputService.recogerConfirmar()) {
                butacas[numFila] = fila;
                butacaRepository.setButacas(butacas);
                return true;
            }
            return false;
        }

        for (int i = 1; i < fila.length; i++) {
            fila = Arrays.copyOf(butacas[numFila], butacas[numFila].length);

            int posicionReal = posicionPrioridad(i);
            int cuantosPuedenEncajarse = 0;

            if(posicionReal + butacasParaEncajar > fila.length) continue;

            for (int j = 0; j < butacasParaEncajar; j++) {
                if(fila[posicionReal+j]){
                    break;
                }
                else {
                    cuantosPuedenEncajarse++;
                    fila[posicionReal+j] = true;
                }
            }

            if(cuantosPuedenEncajarse == butacasParaEncajar){
                outputService.imprimirButacas(numFila, fila);
                System.out.println("¿Le parece bien? (y/n)");
                if (inputService.recogerConfirmar()) {
                    butacas[numFila] = fila;
                    butacaRepository.setButacas(butacas);
                    return true;
                }
                return false;
            }

        }
        return false;
    }

    @Override
    public int posicionPrioridad(int posicionReal) {
        return switch (posicionReal) {
            case 0 -> 4;
            case 1 -> 5;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 2;
            case 5 -> 7;
            case 6 -> 1;
            case 7 -> 8;
            case 8 -> 0;
            case 9 -> 9;
            default -> -1;
        };
    }
}
