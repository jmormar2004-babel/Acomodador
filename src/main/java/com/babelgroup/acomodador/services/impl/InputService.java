package com.babelgroup.acomodador.services.impl;

import com.babelgroup.acomodador.repositories.IButacaRepository;
import com.babelgroup.acomodador.services.IInputService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class InputService implements IInputService {

    private final Scanner sc = new Scanner(System.in);
    private final IButacaRepository butacaRepository;

    public InputService(IButacaRepository butacaRepository){
        this.butacaRepository = butacaRepository;
    }

    @Override
    public int recogerNumero() {
        System.out.print("> ");
        String option = sc.nextLine();
        int integerOption;
        try {
            integerOption = Integer.parseInt(option);
        } catch (NumberFormatException ex) {
            System.err.println("No se ha introducido ningún número");
            return -1;
        }
        return integerOption;
    }

    @Override
    public boolean recogerConfirmar() {
        System.out.print("> ");
        String option = sc.nextLine();
        return option.equalsIgnoreCase("y");
    }

    @Override
    public void importarButacas(String path) {
        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            boolean[][] matrix = new boolean[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                String[] line = lines.get(i).trim().split(" ");
                boolean[] row = new boolean[line.length];
                for (int j = 0; j < line.length; j++) {
                    row[j] = line[j].equals("1");
                }
                matrix[i] = row;
            }

            butacaRepository.setButacas(matrix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String recogerTexto() {
        System.out.print("> ");
        return sc.nextLine();
    }
}
