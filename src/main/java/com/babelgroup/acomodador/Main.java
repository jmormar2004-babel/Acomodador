package com.babelgroup.acomodador;

import com.babelgroup.acomodador.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Acomodador acomodador = context.getBean(Acomodador.class);

        acomodador.run();
    }

}
