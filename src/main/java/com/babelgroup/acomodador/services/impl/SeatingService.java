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
            switch (comprobarButacasDeFila(posicionPrioridad(i, butacas.length), butacasParaEncajar)){
                case 0:
                    break;
                case 1:
                    System.out.println("Butacas reservadas satisfactoriamente");
                    return;
                case 2:
                    System.out.println("Cancelado exitosamente");
                    return;
                case 3:
                    System.out.println("No se ha encontrado sitio, prueba a subdividir o vuelve en otro momento.");
            }
        }



    }

    @Override
    public int comprobarButacasDeFila(int numFila, int butacasParaEncajar) {
        boolean[] fila = Arrays.copyOf(butacas[numFila], butacas[numFila].length);

        if(!fila[posicionPrioridad(0, fila.length)]) {
            for (int j = 0; j < butacasParaEncajar; j++) {
                fila[posicionPrioridad(j, fila.length)] = true;
            }

            outputService.imprimirButacas(numFila, fila);
            System.out.println("¿Le parece bien? y->si, n->no, c->cancelar");
            String opcion = String.valueOf(inputService.recogerTexto().charAt(0));
            if(opcion.equalsIgnoreCase("c")) return 2;
            if (opcion.equalsIgnoreCase("y")) {
                butacas[numFila] = fila;
                butacaRepository.setButacas(butacas);
                return 1;
            }
            return 0;
        }

        for (int i = 1; i < fila.length; i++) {
            fila = Arrays.copyOf(butacas[numFila], butacas[numFila].length);

            int posicionReal = posicionPrioridad(i, fila.length);
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
                System.out.println("¿Le parece bien? y->si, n->no, c->cancelar");
                String opcion = String.valueOf(inputService.recogerTexto().charAt(0));
                if(opcion.equalsIgnoreCase("c")) return 2;
                if (opcion.equalsIgnoreCase("y")) {
                    butacas[numFila] = fila;
                    butacaRepository.setButacas(butacas);
                    return 1;
                }
                return 0;
            }

        }
        return 3;
    }

    @Override
    public int posicionPrioridad(int posicionReal, int length) {
        int middle = length / 2;

        if (posicionReal % 2 == 0) {
            return (middle - posicionReal / 2 + length) % length;
        } else {
            return (middle + posicionReal / 2 + 1) % length;
        }
    }
}
