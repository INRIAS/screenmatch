package com.aluracursos.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.screenmatch.model.DataSerie;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvertirDatos;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t=game+of+thrones&apikey=f33c21cc");
		System.out.println(json);

		ConvertirDatos conversor = new ConvertirDatos();
		var datos = conversor.obtenerDatos(json, DataSerie.class);
		System.out.println(datos);
	}

}
