package it.corso.accenture.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Libro implements Comparable<Libro>, Serializable {

	private static final long serialVersionUID = 1L;

	private String titolo;
	private String autore;
	private double prezzo;
	private Calendar dataUscita;

	public Libro() {
		super();
	}

	public Libro(String titolo, String autore, double prezzo, Calendar dataUscita) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.prezzo = prezzo;
		this.dataUscita = dataUscita;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public Calendar getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(Calendar dataUscita) {
		this.dataUscita = dataUscita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Libro))
			return false;
		Libro other = (Libro) obj;
		return titolo.equalsIgnoreCase(other.titolo);
	}

	@Override
	public int compareTo(Libro o) {
		return titolo.compareToIgnoreCase(o.getTitolo());
	}

	@Override
	public String toString() {
		return "Libro titolo : " + titolo + ", autore : " + autore + ", prezzo : " + prezzo + ", dataUscita : "
				+ dataUscitaSemplice();
	}

	public String dataUscitaSemplice() {
		return new SimpleDateFormat("dd/mm/yyyy").format(dataUscita.getTime());
	}

}
