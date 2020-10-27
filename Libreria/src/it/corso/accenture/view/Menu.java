package it.corso.accenture.view;

import java.io.Closeable;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import it.corso.accenture.entities.Libreria;
import it.corso.accenture.entities.Libro;
import it.corso.accenture.entities.SalvaCaricaLibreria;

public class Menu implements Closeable {

	UserInterface ui;
	private Libreria libreria;
	private String fileName;

	public Menu() {
		ui = new UserInterface();

		fileName = "libri.bin";

		libreria = SalvaCaricaLibreria.caricaLibreria(fileName);

		if (libreria != null)
			System.out.println("Libri caricati.\n");
		else
			libreria = new Libreria();

	}

	public void mostra() {

		for (boolean esci = false; !esci;) {

			String messaggioMenu = "--Effettua operazioni sui libri della libreria--\n" +
					"Numero libri salvati : " + libreria.getLibri().size() + "\n" +
					"\n" +
					"Scegli un opzione:\n";

			UIOpzione[] opzioni = new UIOpzione[] {
					new UIOpzione("1)Visualizza elenco libri,", () -> visualizzaElenco()),
					new UIOpzione("2)Inserisci nuovo libro,", () -> inserisciLibro()),
					new UIOpzione("3)Rimuovi libro,", () -> rimuoviLibro()),
					new UIOpzione("4)Cerca libro per anno,", () -> cercaPerAnno()),
					new UIOpzione("5)Confronta date di uscita,", () -> confrontaLibri()),
					new UIOpzione("6)Esci", () -> esci())
			};
			
			int scelta = ui.sceltaOpzioni(messaggioMenu, opzioni);
			opzioni[scelta-1].esegui();
			
			esci = scelta == opzioni.length;

			ui.aspettaUtente();
		}
	}

	public void visualizzaElenco() {
		System.out.println();

		if (libreria.getLibri().size() > 0) {
			System.out.println("I libri salvati nella libreria sono:");
			libreria.getLibri().forEach(l -> System.out.println(l));
		} else
			System.out.println("Non è stato inserito nessun libro nella libreria.");

		System.out.println();
	}

	public void inserisciLibro() {
		System.out.println();

		String titolo = ui.chiediStringa("Inserisci il titolo del libro.");
		String autore = ui.chiediStringa("Inserisci l'autore del libro.");
		double prezzo = ui.chiediDoublePos("Inserisci il prezzo del libro.");
		Calendar dataUscita = ui.chiediCalendar("Inserisci la data di uscita del libro (formato dd/mm/yyyy).",
				"dd/mm/yyyy");

		Libro nuovo = new Libro(titolo, autore, prezzo, dataUscita);
		System.out.println();

		if (libreria.insertLibro(nuovo))
			System.out.println("Il libro è stato inserito nella libreria.");
		else
			System.out.println("Inserimento non effettuato. Il libro è già presente nella libreria.");

		System.out.println();

		salvaLibreria();
	}

	public void rimuoviLibro() {
		System.out.println();
		String titolo = ui.chiediStringa("Inserisci il titolo del libro.");
		System.out.println();

		if (libreria.deleteLibro(titolo))
			System.out.println("Il libro è stato rimosso dalla libreria.");
		else
			System.out.println("Rimozione non effettuata. Il libro non è presente nella libreria.");

		System.out.println();

		salvaLibreria();
	}

	public void cercaPerAnno() {

		System.out.println();
		int anno = ui.chiediIntPos("Inserisci l'anno dei libri da trovare.");
		System.out.println();

		Predicate<Libro> criterio = l -> l.getDataUscita().get(Calendar.YEAR) == anno;
		List<Libro> trovati = libreria.ricercaLibri(criterio);

		if (trovati.size() > 0) {
			System.out.println("I libri trovati dell'anno richiesto sono:");
			trovati.forEach(l -> System.out.println(l));
		} else
			System.out.println("Libri non trovati. Non sono presenti libri usciti nell'anno richiesto.");

		System.out.println();
	}

	public void confrontaLibri() {

		System.out.println();
		String titolo1 = ui.chiediStringa("Inserisci il titolo del primo libro.");
		System.out.println();

		Libro libro1 = libreria.ricercaLibro(l -> l.getTitolo().equalsIgnoreCase(titolo1));

		if (libro1 == null) {
			System.out.println("Il libro non è stato trovato.");
			System.out.println();
			return;
		}

		String titolo2 = ui.chiediStringa("Inserisci il titolo del secondo libro.");
		System.out.println();

		Libro libro2 = libreria.ricercaLibro(l -> l.getTitolo().equalsIgnoreCase(titolo2));

		if (libro2 == null) {
			System.out.println("Il libro non è stato trovato.");
			System.out.println();
			return;
		}

		Long millisLibro1 = libro1.getDataUscita().getTimeInMillis();
		Long millisLibro2 = libro2.getDataUscita().getTimeInMillis();
		Long differenzaGiorni = Math.abs((millisLibro1 - millisLibro2) / (1000 * 60 * 60 * 24));

		System.out.println("La differenza tra le 2 date di uscita dei libri è di " + differenzaGiorni + " giorni.");

		System.out.println();
	}

	public void esci() {
		System.out.println("\nFine Programma\n");
	}

	public void salvaLibreria() {
		boolean salvata = SalvaCaricaLibreria.salvaLibreria(fileName, libreria);
		System.out.println(salvata ? "Libreria salvata" : "Non è stato possibile salvare le modifiche.");
	}

	@Override
	public void close() {
		ui.close();
	}

}