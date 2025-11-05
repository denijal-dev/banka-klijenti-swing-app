package banka.model;

public class Klijent {
    private String ime;
    private String prezime;
    private double stanje;

    public Klijent(String ime, String prezime, double stanje) {
        this.ime = ime;
        this.prezime = prezime;
        this.stanje = stanje;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public double getStanje() {
        return stanje;
    }

    public String getPunoIme() {
        return ime + " " + prezime;
    }

    @Override
    public String toString() {
        return getPunoIme() + " (saldo: " + stanje + " KM)";
    }
}
