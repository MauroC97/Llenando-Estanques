package estanques;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class EstanqueTest {

	@Test
	void test() throws IOException {
		Criadero cri = new Criadero("Pruebas/in/02_tanquesllenos.in","Pruebas/out/02_tanquesllenos.out");
		cri.procesar_entrada();
		cri.procesar_salida();
		
	}

}
