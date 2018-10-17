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
	public int repartir_agua(){//debe llenar los volumenes de los estanques del array
		int cant_tanques=0,prof_canio_ant,i=0;
		boolean es_ult_tanque;
//		boolean es_pri_tanque;
		while(i<array.size()) {
		Estanque e = array.get(i);
		do {//primer pasada, llenando el agua mientras no sea necesario emparejar tanques
			cant_tanques++;
		if(vol_a_llenar<e.volumen_canio_sig())
		{
			e.volumen+=vol_a_llenar;
			return cant_tanques;
		}
		e.volumen+=e.volumen_canio_sig();
		vol_a_llenar-=e.volumen_canio_sig();
		prof_canio_ant=e.prof_canio;
		i++;
		e = array.get(i);
		es_ult_tanque= e.prof_canio==0;
		if(es_ult_tanque||e.prof_canio<prof_canio_ant) {//logica para cargar el ultimo tanque parejo hasta el caño anterior(no tiene sig)
			if(vol_a_llenar<e.volumen_canio_ant(prof_canio_ant))
			{
				e.volumen+=vol_a_llenar;
				return cant_tanques;
			}
			e.volumen+=e.volumen_canio_ant(prof_canio_ant);
			vol_a_llenar-=e.volumen_canio_ant(prof_canio_ant);
		}
		}while(e.prof_canio>=prof_canio_ant&&!es_ult_tanque);
		//si llego aca resta emparejar nivel de agua con los tanques anteriores y volver a comenzar a llenar.
		
		int j;
		ArrayList<Estanque> arr_a_emparejar= new ArrayList<Estanque>();
		Estanque e_ant;
		double dif_vol;
		j=cant_tanques;
			e=array.get(j-1);//estanque actual
			//busco saber todos los estanques anteriores que deben emparejarse hasta una cierta altura
			//comenzando por emparejar de la menor altura vacia hasta la altura del sig. caño.
			int alt_max_a_nivelar=e.prof_canio;//antes de poder cargar mas agua,necesito emparejar todos los caños a esta altura de ser posible
			arr_a_emparejar.add(e);//siempre voy a emparejar el estanque actual
			do {
				j--;
				e=array.get(j-1);
				arr_a_emparejar.add(e);
				e_ant = array.get(j-2); 
				dif_vol=e.diferencia_vol_entre_ant_sig(e_ant.prof_canio);
				if(vol_a_llenar<(dif_vol*arr_a_emparejar.size()))
				{
					dif_vol/=arr_a_emparejar.size();
					for(Estanque x:arr_a_emparejar)
						x.volumen+=dif_vol;
					return cant_tanques;
				}
				for(Estanque x:arr_a_emparejar)
					x.volumen+=dif_vol;
				
			}while(e_ant.prof_canio>alt_max_a_nivelar&&j>1);
			
		}
		//si llego aca queda agua y estan todos los tanques llenos y emparejados
		vol_a_llenar/=array.size();
		for(Estanque x:array)
			x.volumen+=vol_a_llenar;
		return cant_tanques;
		
		
	}
	
	public void procesar_salida() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(path_salida));
		int desborde = this.hay_desborde();
		if (desborde > 0) {
			pw.println("Hay desborde: " + desborde);
			pw.close();
			return;
		}
		int cant_tanques=this.repartir_agua();
		pw.println(cant_tanques);
		for(int i=0;i<cant_tanques;i++)
		{
			Estanque e = array.get(i);
			pw.println(i+1+" "+e.volumen_en_metros());
		}
		pw.close();
	}
}
