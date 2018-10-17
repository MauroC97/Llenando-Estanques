package estanques;

public class Estanque {
	int sup_base;
	int prof_total;
	int prof_canio;//si prof canio = 0 es el ultimo tanque 
	int volumen;//los tanques comienzan vacios
	
	public Estanque(int sup_base, int prof_total, int prof_canio) {
		this.sup_base = sup_base;
		this.prof_total = prof_total;
		this.prof_canio = prof_canio;
		this.volumen=0;
	}
	
	public int volumen_total() {
		return sup_base*prof_total;
	}
	public int volumen_canio() {
		return this.volumen_total()-(sup_base*prof_canio);
	}
	public int volumen_en_metros() {
		return (int) (Math.round((double)(this.volumen*this.prof_total)/(double) this.volumen_total()));// regla de 3
	}
	
}
