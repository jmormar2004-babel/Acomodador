package com.babelgroup.acomodador;

import com.babelgroup.acomodador.services.IInputService;
import com.babelgroup.acomodador.services.IOutputService;
import com.babelgroup.acomodador.services.ISeatingService;
import org.springframework.stereotype.Component;

@Component
public class Acomodador {

    private final IInputService inputService;
    private final IOutputService outputService;
    private final ISeatingService seatingService;

    public Acomodador(ISeatingService seatingService, IOutputService outputService, IInputService inputService) {
        this.seatingService = seatingService;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    public void run(){
        int op = 0;
        while(op!=5){
            outputService.imprimirMenu();
            op = inputService.recogerNumero();

            switch (op){
                case 1:
                    System.out.println("¿Cuántas butacas va a reservar?");
                    int numButacas = inputService.recogerNumero();
                    seatingService.buscarLugar(numButacas);
                    break;
                case 2:
                    outputService.imprimirButacas();
                    break;
                case 3:
                    System.out.println("Introduce la ruta al archivo (opcional).");
                    String nombreArchivo = inputService.recogerTexto();
                    outputService.guardarButacas(nombreArchivo);
                    break;
                case 4:
                    System.out.println("Introduce la ruta al archivo.");
                    String rutaArchivo = inputService.recogerTexto();
                    inputService.importarButacas(rutaArchivo);
                    break;
                case 5: break;
                default:
                    System.out.println("Esa opción no está contemplada en el menú.");
            }

        }
    }
}
