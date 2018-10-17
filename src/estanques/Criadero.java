package estanques;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Criadero {
	ArrayList<Estanque> array = new ArrayList<Estanque>();
	int vol_a_llenar;
	String path_entrada;
	String path_salida;
	
	public Criadero(String input,String output)
	{
		this.path_entrada = input;
		this.path_salida = output;
	}
	
	public void procesar_entrada() throws IOException {
		Scanner sc = new Scanner(new File(path_entrada));
		int tam=sc.nextInt();
		for (int i=0;i<tam-1;i++)
			array.add(new Estanque(sc.nextInt(),sc.nextInt(),sc.nextInt()));
		array.add(new Estanque(sc.nextInt(),sc.nextInt(),0));//ultimo estanque no tiene tubo(1 valor menos que leer)
		vol_a_llenar=sc.nextInt();//ultimo valor del txt es la cantidad de agua que ingresa
		sc.close();
	}
	
}
