package com.alura.literalura;

import com.alura.literalura.model.dto.AutorResponseDTO;
import com.alura.literalura.model.dto.BusquedaResponseDTO;
import com.alura.literalura.model.dto.LibroDTO;
import com.alura.literalura.model.dto.LibroResponseDTO;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.BusquedaService;
import com.alura.literalura.service.GutendexService;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;

	@Autowired
	private BusquedaService busquedaService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args){
		GutendexService gutendexService = new GutendexService();
		BusquedaResponseDTO busqueda = null;
		List<LibroDTO> libros = null;
		List<LibroResponseDTO> librosBD = null;
		List<AutorResponseDTO> autoresBD = null;

		String titulo = "Oscar Wilde, Art and Morality: A Defence"; //unico resultado sin autores
        String idioma = "eN";
		Long anio = 1890l;
		Scanner scanner = new Scanner(System.in);
		String menu = """
                --- LiterAlura ---
                1. Buscar Libros por título en Gutendex
                2. Mostrar libros buscados
                3. Mostrar libros según idioma
                4. Mostrar top 10 de libros más descargados
                5. Mostrar estadísticas de libros buscados según descargas                
                6. Mostrar autores de libros buscados
                7. Mostrar autores vivos en determinado año
                8. Buscar autores por nombre
                9. Libros con mas apariciones
                10. Salir
                """;

		try {
			int opcion=0;

			do {
				System.out.println(menu);
				System.out.print("Elija una opción: ");
				try {
					opcion = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.out.println("\n❌ Por favor, ingrese un número válido.");
					continue;
				}

				switch (opcion) {
					case 1: //Buscar Libros por título en Gutendex
						System.out.print("Ingrese título a buscar:");
						titulo = scanner.next();

						libros = gutendexService.buscarLibroAutor(titulo);
						//libros.forEach( l -> autorService.agregarAutorConLibro(l));
						busqueda = busquedaService.guardarBusquedaConLibros(titulo, libros);
						System.out.println(busqueda);
						break;
					case 2: //Mostrar libros buscados
						List<BusquedaResponseDTO> busquedasBD = busquedaService.obtenerTodasBusquedas();
						if(busquedasBD.isEmpty())
							System.out.println("No se guardaron libros aún");
						else
							busquedasBD.forEach(System.out::println);

						/*
						librosBD = libroService.obtenerTodos();
						if(librosBD.isEmpty())
							System.out.println("No se guardaron libros aún");
						else
							librosBD.forEach(System.out::println);
						 */
						break;
					case 3: //Mostrar libros según idioma
						System.out.print("Ingrese idioma a buscar. Por ejemplo: es o en: ");
						idioma = scanner.next();

						librosBD = libroService.buscarLibrosPorIdioma(idioma);
						if(librosBD.isEmpty())
							System.out.println("No se encontraron libros con idioma: " + idioma);
						else {
							System.out.println("libros con idioma " + idioma + ":");
							librosBD.forEach(System.out::println);
						}
						break;
					case 4: //Mostrar top 10 de libros más descargados
						librosBD = libroService.obtenerTop10porDescargasJPQL();
						if(librosBD.isEmpty())
							System.out.println("No se guardaron libros aún");
						else {
							System.out.println("Top 10 libros con mas descargas");
							librosBD.forEach(System.out::print);
						}
						break;
					case 5: //Mostrar estadísticas de libros buscados según descargas
						{
							/*
							System.out.println("Estadisticas sobre Libros según idioma y descargas");
							Map<String, LongSummaryStatistics> estadisticasPorIdioma = libroService.obtenerTodos()
									.stream()
									.collect(Collectors.groupingBy(
											LibroResponseDTO::idioma,
											Collectors.summarizingLong(LibroResponseDTO::numeroDescargas)
									));

							estadisticasPorIdioma.forEach((filtroIdioma, estadisticas) -> {
								System.out.printf("Idioma: %s | Cantidad: %d | Descargas(Promedio: %.2f | Mín: %d | Máx: %d | Total: %d)%n",
										filtroIdioma,
										estadisticas.getCount(),
										estadisticas.getAverage(),
										estadisticas.getMin(),
										estadisticas.getMax(),
										estadisticas.getSum()
								);
							});
							 */

							System.out.println("Estadisticas de descargas según idioma:");
							libroService.obtenerEstadisticasPorIdiomaJPQL().forEach(System.out::println);

							/*
							System.out.println("Estadisticas nativa usando interface:");
							libroService.obtenerEstadisticasPorIdiomaNativoUsandoInterfaces().forEach( e ->
									System.out.printf("Idioma: %s | Cantidad: %d | Descargas(Promedio: %.2f | Mín: %d | Máx: %d | Total: %d)%n",
											e.getIdioma(),
											e.getCantidad(),
											e.getPromedioDescargas(),
											e.getMinimoDescargas(),
											e.getMaximoDescargas(),
											e.getTotalDescargas()
									)
							);

							System.out.println("Estadisticas nativa usando clases:");
							libroService.obtenerEstadisticasPorIdiomaNativoUsandoClases().forEach(System.out::println);
							 */
						}
						break;
					case 6: //Mostrar autores de libros buscados
						autoresBD = autorService.obtenerAutores();
						if(autoresBD.isEmpty())
							System.out.println("No se ingresaron autores aún");
						else
							autoresBD.forEach(System.out::println);
						break;
					case 7: //Mostrar autores vivos en determinado año
						try {
							System.out.println("Ingrese año en que vivieron los autores: ");
							anio = Long.parseLong(scanner.next());
						} catch (NumberFormatException e) {
							System.out.println("\n❌ Por favor, ingrese un año válido.");
							continue;
						}

						autoresBD = autorService.autoresPorAnioJPQL(anio);
						if(autoresBD.isEmpty())
							System.out.println("No se encontraron autores con el año ingresado");
						else {

							System.out.println("Autores que vivieron en el año " + anio + ": ");
							autoresBD.forEach(System.out::println);
						}
						break;
					case 8: //buscar autores por nombre
						System.out.println("Ingrese nombre de autor a buscar:");
						String nombre = scanner.next();

						autoresBD = autorService.obtenerAutoresPorNombre(nombre);
						if(autoresBD.isEmpty())
							System.out.println("No se encontraron autores con el nombre ingresado");
						else
							autoresBD.forEach(System.out::println);
						break;
					case 9: //Libros con mas apariciones
						libroService.obtenerLibrosConMasDeUnaBusqueda().forEach(System.out::println);
						break;
					default:
						System.out.println("Opción incorrecta. Intente nuevamente.");
				}
			} while (opcion != 10);
			scanner.close();
			System.out.println("Gracias por usar LiterAlura");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
	}
}