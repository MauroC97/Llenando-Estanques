package estanques;

public class Estanque {
	int sup_base;
	int prof_total;
	int prof_canio;//si prof canio = 0 es el ultimo tanque 
	double volumen;//los tanques comienzan vacios
	
	public Estanque(int sup_base, int prof_total, int prof_canio) {
		this.sup_base = sup_base;
		this.prof_total = prof_total;
		this.prof_canio = prof_canio;
		this.volumen=0;
	}
	
	public double volumen_total() {
		return sup_base*prof_total;
	}
	public double volumen_canio_ant(int prof) {
		return this.volumen_total()-(sup_base*prof);
	}
	public double volumen_canio_sig() {
		return this.volumen_total()-(sup_base*prof_canio);
	}
	public int volumen_en_metros() {
		return (int) (Math.round(this.volumen*this.prof_total)/this.volumen_total());// regla de 3
	}
	
}
