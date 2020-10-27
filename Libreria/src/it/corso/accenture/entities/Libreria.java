package it.corso.accenture.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Libreria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Set<Libro> libri;

	public Libreria() {
		super();
		libri = new TreeSet<Libro>();
	}

	public Set<Libro> getLibri() {
		return libri;
	}

	public void setLibri(Set<Libro> libri) {
		this.libri = libri;
	}

	public boolean insertLibro(Libro Libro) {
		return libri.add(Libro);
	}

	public boolean deleteLibro(String titolo) {	
		return libri.removeIf(c -> c.getTitolo().equalsIgnoreCase(titolo));
	}

	public Libro ricercaLibro(Predicate<Libro> condizione) {
		return libri.stream().filter(condizione).findFirst().orElse(null);
	}
	
	public List<Libro> ricercaLibri(Predicate<Libro> condizione) {
		return libri.stream().filter(condizione).collect(Collectors.toList());
	}
}
