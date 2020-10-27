package it.corso.accenture.entities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SalvaCaricaLibreria {

	public static boolean salvaLibreria(String path, Libreria libreria) {

		File file = new File(path);

		try (ObjectOutputStream oos = createObjectOutStream(file)) {

			file.createNewFile();

			oos.writeObject(libreria);

			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Libreria caricaLibreria(String path) {

		File file = new File(path);

		if (!file.exists())
			return null;

		try (ObjectInputStream ois = createObjectInStream(file)) {

			return (Libreria) ois.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static ObjectOutputStream createObjectOutStream(File file) throws IOException {
		return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
	}
	
	private static ObjectInputStream createObjectInStream(File file) throws IOException {
		return new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
	}

}
