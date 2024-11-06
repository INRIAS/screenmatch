package com.aluracursos.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.screenmatch.principal.EjemploStream;
import com.aluracursos.screenmatch.principal.Principal;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		 Principal principal = new Principal();
		 principal.muestraMenu();
		

		//  EjemploStream ejemploStream = new EjemploStream();
		//  ejemploStream.muestraEjemplo();
	}
}
