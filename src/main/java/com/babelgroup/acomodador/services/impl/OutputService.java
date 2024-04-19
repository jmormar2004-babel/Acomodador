package com.babelgroup.acomodador.services.impl;

import com.babelgroup.acomodador.repositories.IButacaRepository;
import com.babelgroup.acomodador.services.IOutputService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OutputService implements IOutputService {

    private final IButacaRepository butacaRepository;
    boolean[][] butacas;

    public OutputService(IButacaRepository butacaRepository){
        this.butacaRepository = butacaRepository;
    }

    @Override
    public void imprimirMenu() {
        System.out.println("CINES KAKA");
        System.out.println("1. Reservar asientos");
        System.out.println("2. Visualizar sala");
        System.out.println("3. Guardar disposición de butacas actual");
        System.out.println("4. Importar disposición de butacas");
        System.out.println("5. Salir del programa");
    }

    @Override
    public void imprimirButacas() {

        if(this.butacas == null){
            this.butacas = butacaRepository.exportButacas();
        }

        if(this.butacas == null){
            butacaRepository.init();
            this.butacas = butacaRepository.exportButacas();
        }

        for (boolean[] fila : this.butacas) {
            for (boolean butaca : fila) {
                System.out.print("["+(butaca?"X":" ")+"]");
            }
            System.out.println();
        }
    }

    @Override
    public void imprimirButacas(int fila, boolean[] butacasReservadas) {

        this.butacas = butacaRepository.exportButacas();

        for (int i = 0; i < this.butacas.length; i++) {
            if(i == fila){
                for (int j = 0; j < butacasReservadas.length; j++) {
                    if(this.butacas[fila][j]){
                        System.out.print("[X]");
                    } else if(butacasReservadas[j]) {
                        System.out.print("[R]");
                    } else {
                        System.out.print("[ ]");
                    }
                }
                System.out.println();
                continue;
            }
            for (boolean butaca : this.butacas[i]) {
                System.out.print("["+(butaca?"X":" ")+"]");
            }
            System.out.println();
        }
    }

    @Override
    public void guardarButacas(String path) {
        if(path.isEmpty()) path = "butacas"+ new SimpleDateFormat("dd-MM-yyyy--hh_mm_ss").format(new Date()) +".txt";

        this.butacas = butacaRepository.exportButacas();

        try (PrintWriter writer = new PrintWriter(path)){
            for (boolean[] fila : butacas){
                for (boolean butaca : fila){
                    writer.print(butaca ? "1":"0");
                    writer.print(" ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo de destino");
        }
    }
}
