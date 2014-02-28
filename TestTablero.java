package colorjunction;

import junit.framework.TestCase;
import colorjunction.Tablero;
import colorjunction.conf.Configuracion;

/**
 * Clase que hace pruebas sobre los metodos de la clase Tablero.
 * Los aspectos que se prueban son el desplazamiento horizontal de las fichas,
 * el recuento de grupos correctamente, el mÈtodo firma y el desplazamiento
 * vertical(caida de fichas). Aunque cada prueba es muy especÌfica sobre un 
 * mÈtodo, en la mayorÌa se utilizan otros mÈtodos obligatoriamente, aunque
 * no sean la causa de la prueba.
 * 
 * @author Javier López Medina
 * @version 1.0
 */
public class TestTablero extends TestCase {
	/**
	 * Prueba si creando un tablero con todas las casillas de 
	 * distinto color, devuelve bien el número de grupos.
	 */
	public void testGrupoUnico (){
		int alto = 3;
		int ancho =3;
		String firma = "aaaaaaaaa";
		Configuracion configuracion;
		configuracion = new Configuracion(ancho, alto,3);
		Tablero tablero = new Tablero(configuracion, firma);
		assertEquals(1,tablero.getNGrupos());
	}
	/**
	 * Prueba si creando un tablero con todas las casillas del mismo color,
	 * devuelve bien el número de grupos.
	 */
	public void testNingunGrupo(){
		String firma = "abccabbca";
		Configuracion configuracion;
		configuracion = new Configuracion(3, 3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		assertEquals( 0 ,tablero.getNGrupos());
	}
	/**
	 * Prueba si creando un tablero con varios grupos
	 * devuelve bien el número de grupos.
	 */
	public void testBordesAncho (){
		String firma = "aabbaabbaabbaabbaa";
		Configuracion configuracion;
		configuracion = new Configuracion(6, 3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		assertEquals(9,tablero.getNGrupos());
	}
	/**
	 * Prueba lo mismo que testBordesAlto,
	 * pero el tablero esta volteado.
	 */
	public void testBordesAlto (){
		String firma = "abaabababbababaaba";
		Configuracion configuracion;
		configuracion = new Configuracion(3,6,3);
		Tablero tablero = new Tablero(configuracion, firma);
		assertEquals(9,tablero.getNGrupos());
	}
	/**
	 * Prueba si creando un tablero con casillas vacias y
	 * con 3 grupos intercalados, funciona bien el mÈtodo
	 * recursivo marca, devolviendo bien el número de grupos.
	 */
	public void testCeldasVacias (){
		String firma = "---a----a-a-a-aaaaaaababababbbaaabbbbbabbb";
		Configuracion configuracion;
		configuracion = new Configuracion(7,6,3);
		Tablero tablero = new Tablero(configuracion, firma);
		assertEquals(3,tablero.getNGrupos());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae (){
		String firma = "aabbcdabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,0);
		assertEquals("--bbcdabc",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el mÈtodo caer.
	 */
	public void testCae2 (){
		String firma = "aaabcdabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,0);
		assertEquals("---bcdabc",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae3 (){
		String firma = "caabcdabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,0);
		assertEquals("c--bcdabc",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae4 (){
		String firma = "bcdaabbcd";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,1);
		assertEquals("--dbcbbcd",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae5 (){
		String firma = "bcdaaabcd";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,1);
		assertEquals("---bcdbcd",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae6 (){
		String firma = "bcddaabcd";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(2,1);
		assertEquals("b--dcdbcd",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae7 (){
		String firma = "bcdcdbaac";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,2);
		assertEquals("--dbcbcdc",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae8 (){
		String firma = "bcdcdbaaa";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,2);
		assertEquals("---bcdcdb",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método caer.
	 */
	public void testCae9 (){
		String firma = "bcdcdbbaa";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,2);
		assertEquals("b--ccdbdb",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * y vamos haciendo click en fichas que forman un 
	 * grupo, funciona bien el método caer.
	 */
	public void testVariosClic (){
		String firma = "abcabcddd";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,0);
		assertEquals("a-ca-cddd",tablero.getFirma());
		tablero.clic(0,0);
		assertEquals("--c--cddd",tablero.getFirma());
		tablero.clic(2,0);
		assertEquals("------ddd",tablero.getFirma());
		tablero.clic(0,2);
		assertEquals("---------",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * y vamos haciendo click en fichas que forman un 
	 * grupo, funciona bien el método caer y el método
	 * desplazar.
	 */
	public void testVariosClic2 (){
		String firma = "dddabcabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,4);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,1);
		assertEquals("d-da-cadc",tablero.getFirma());
		tablero.clic(0,1);
		assertEquals("--d--cddc",tablero.getFirma());
		tablero.clic(2,1);
		assertEquals("------ddd",tablero.getFirma());
		tablero.clic(0,2);
		assertEquals("---------",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza(){
		String firma = "a--abcabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(2,1);
		assertEquals("a--ab-ab-",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza2(){
		String firma = "a--abcabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,1);
		assertEquals("a--ac-ac-",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza3(){
		String firma = "a--abcabc";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,1);
		assertEquals("---bc-bc-",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza4(){
		String firma = "abbabbabb";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,0);
		assertEquals("a--a--a--",tablero.getFirma());
		tablero.clic(0,0);
		assertEquals("---------",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza5(){
		String firma = "aabaabaab";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(1,0);
		assertEquals("b--b--b--",tablero.getFirma());
		tablero.clic(0,0);
		assertEquals("---------",tablero.getFirma());
	}
	/**
	 * Prueba si creando un tablero mediante una firma,
	 * funciona bien el método desplazar.
	 */
	public void testDesplaza6(){
		String firma = "aaaaaaaaa";
		Configuracion configuracion;
		configuracion = new Configuracion(3,3,3);
		Tablero tablero = new Tablero(configuracion, firma);
		tablero.clic(0,0);
		assertEquals("---------",tablero.getFirma());
	}
}