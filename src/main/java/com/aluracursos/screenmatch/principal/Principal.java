package com.aluracursos.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aluracursos.screenmatch.model.DataEpisodio;
import com.aluracursos.screenmatch.model.DataSerie;
import com.aluracursos.screenmatch.model.DataTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvertirDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f33c21cc";
    private ConvertirDatos conversor = new ConvertirDatos();

    public void muestraMenu() {
        // CConsulta datos genericos de las series
        System.out.println("Ingresa la Serie que deseas buscar: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DataSerie.class);
        System.out.println("******************************************");
        System.out.println(datos);
        System.out.println("******************************************");

        // Iterar los datos de las series
        List<DataTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totaldeTemporadas(); i++) {
            // String separador = "******************************************";
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DataTemporadas.class);
            // System.out.println(separador);
            temporadas.add(datosTemporadas);
        }
        // temporadas.forEach(System.out::println);


        // Mostrar solo el titulo de los episodios para cada temporada

        // for (int i = 0; i < datos.totaldeTemporadas(); i++) {
        // List<DataEpisodio> episodiosTemporada = temporadas.get(i).episodios();

        // System.out.println("");
        // System.out.println("********************");
        // System.out.println("*****Temporada= " + temporadas.get(i).numeroTemporada() +
        // "*****");
        // System.out.println("********************");
        // for (int j = 0; j < episodiosTemporada.size(); j++) {
        // System.out.println(episodiosTemporada.get(j).numeroEpisodio() + ". " +
        // episodiosTemporada.get(j).titulo());

        // }

        // }

        // Mejora uso de operaciones lambda
        // temporadas.forEach(t -> t.episodios().forEach(e ->
        // System.out.println(e.numeroEpisodio() + ". " + e.titulo())));
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.numeroEpisodio() + ". " + e.titulo())));

        // Convertir toda la informacion a una List de tipo DatosEpisodio

        List<DataEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // Top 5 Episodios

        System.out.println("*****Top 5 Episodios*****");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

                
        // Convertir datos en una lista de episodios
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // Busqueda de episodios a partir de una fecha
        System.out.println("***************************");

        System.out.println("Ingresar año de episodios a buscar sobre esta: ");
        var fecha = teclado.nextInt();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate buscarFecha=LocalDate.of(fecha, 1, 1);
        episodios.stream()
                .filter(e->e.getFechaDeLanzamiento()!= null && e.getFechaDeLanzamiento().isAfter(buscarFecha))
                .forEach(e->System.out.println(
                        "Temporada: " + e.getTemporada() + ", Episodio: " + e.getTitulo() + ", Fecha de lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
                ));
    }
}
