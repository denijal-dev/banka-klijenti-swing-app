package banka.test;

import static org.junit.Assert.*;
import org.junit.Test;
import banka.model.Klijent;

public class KlijentTest {

    @Test
    public void testKreiranjeKlijenta() {
        Klijent k = new Klijent("Denijal", "Imamović", 15000.0);
        assertEquals("Denijal", k.getIme());
        assertEquals("Imamović", k.getPrezime());
        assertEquals(15000.0, k.getStanje(), 0.001);
    }

    @Test
    public void testPunoIme() {
        Klijent k = new Klijent("Lejla", "Babić", 9000.0);
        assertEquals("Lejla Babić", k.getPunoIme());
    }
    
    @Test
    public void testNajbogatijiKlijent() {
        java.util.List<Klijent> lista = new java.util.ArrayList<>();
        lista.add(new Klijent("Aida", "A.", 5000));
        lista.add(new Klijent("Marko", "M.", 12000));
        lista.add(new Klijent("Sara", "S.", 8000));

        Klijent top = lista.stream()
                .max(java.util.Comparator.comparingDouble(Klijent::getStanje))
                .orElse(null);

        assertNotNull(top);
        assertEquals("Marko", top.getIme());
        assertEquals(12000, top.getStanje(), 0.001);
    }

    @Test
    public void testProsjekStanja() {
        java.util.List<Klijent> lista = new java.util.ArrayList<>();
        lista.add(new Klijent("Aida", "A.", 5000));
        lista.add(new Klijent("Marko", "M.", 15000));

        double prosjek = lista.stream()
                .mapToDouble(Klijent::getStanje)
                .average()
                .orElse(0.0);

        assertEquals(10000.0, prosjek, 0.001);
    }


    @Test
    public void testToString() {
        Klijent k = new Klijent("Amir", "Kovačević", 8420.75);
        String s = k.toString();
        assertTrue(s.contains("Amir"));
        assertTrue(s.contains("Kovačević"));
        assertTrue(s.contains("8420"));
    }
}
