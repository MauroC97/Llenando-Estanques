package estanques;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Criadero {
	private ArrayList<Estanque> array = new ArrayList<Estanque>();
	private int vol_a_llenar;
	private String path_entrada;
	private String path_salida;

	public Criadero(String input, String output) {
		this.path_entrada = input;
		this.path_salida = output;
	}

	public void procesar_entrada() throws IOException {
		Scanner sc = new Scanner(new File(path_entrada));
		int tam = sc.nextInt();
		for (int i = 0; i < tam - 1; i++)
			array.add(new Estanque(sc.nextInt(), sc.nextInt(), sc.nextInt()));
		array.add(new Estanque(sc.nextInt(), sc.nextInt(), 0));// ultimo estanque no tiene tubo(1 valor menos que leer)
		vol_a_llenar = sc.nextInt();// ultimo valor del txt es la cantidad de agua que ingresa
		sc.close();
	}



	public int hay_desborde() {
		int acum = 0;
		for (Estanque e : array)
			acum += e.volumen_total();
		return acum - vol_a_llenar;// retorna <=0 si no desborda o >0 si desborda
									// si da >0 ese es el valor que hay que escribir en el archivo
	}

	public void procesar_salida() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(path_salida));
		int desborde = this.hay_desborde();
		if (desborde > 0) {
			pw.println("Hay desborde: " + desborde);
			pw.close();
			return;
		}
		
		
		
		pw.close();
	}
}
