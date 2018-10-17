package estanques;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	public int repartir_agua(){
		int cant_tanques=1;//cantidad de tanques cargados
		Estanque e=array.get(0);
		int canio_ant=e.prof_canio;
		double vol_may,vol_min;
		
		if(vol_a_llenar < e.volumen_canio_sig()) {//no alcanzo a llenar el sig tanque hasta el caño.
				e.volumen+=vol_a_llenar;
				return cant_tanques;
			}
			e.volumen+=e.volumen_canio_sig();
			vol_a_llenar-=e.volumen_canio_sig();
			for(int i=1;i<array.size();i++)
			{
				cant_tanques++;
				e=array.get(i);
				vol_may=e.volumen_canio_ant(canio_ant)<e.volumen_canio_sig()?e.volumen_canio_sig():e.volumen_canio_ant(canio_ant);
				vol_min=e.volumen_canio_ant(canio_ant)>e.volumen_canio_sig()?e.volumen_canio_sig():e.volumen_canio_ant(canio_ant);
				if(vol_a_llenar < vol_min) {//no alcanzo a llenar el sig tanque hasta el caño.
					e.volumen+=vol_a_llenar;
					return cant_tanques;
				}
				e.volumen+=vol_min;
				vol_a_llenar-=vol_min;
				
				
			}
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
