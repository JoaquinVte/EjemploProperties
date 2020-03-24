package main;

import java.io.*;
import java.net.InetAddress;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Properties;

public class Test {
	public static void main(String[] args) {

		final String PROPERTIES_FILE = "app.properties";
		final String XML_PROPERTIES_FILE = "appXML.properties";

		System.out.println("Obteniendo datos del fichero " + PROPERTIES_FILE);
		getProperties(PROPERTIES_FILE);

		System.out.println("\n\nObteniendo datos del fichero " + XML_PROPERTIES_FILE);
		getXMLProperties(XML_PROPERTIES_FILE);
	}

	private static void getProperties(String PROPERTIES_FILE) {
		String language = "ES";
		InetAddress IP=null;
		Integer port=null;

		Properties properties = new Properties();

		// Leemos los datos del fichero PROPERTIES_FILE
		try {
			properties.load(new BufferedReader(new FileReader(PROPERTIES_FILE)));
			language = properties.getProperty("language", "ES");
			IP = InetAddress.getByName(properties.getProperty("IP","127.0.0.1"));
			port = Integer.parseInt(properties.getProperty("port","5435"));
			
		} catch (Exception ex) {
			// Si hay algún error, por ejemplo que no exista el archivo de propiedades
			// se dejan los valores predeterminados del language, IP y port
		}

		// Muestra en pantalla los valores de las variables
		System.out.println("Idioma: " + language);
		if (port != null) {			
			System.out.println("IP: " + IP.getHostAddress());
			System.out.println("Port: " + port);			
		} else {
			System.out.println("Es la primera vez que usa esta aplicación");
		}

		// Establecemos valores a las propiedades
		properties.setProperty("language", language);
		properties.setProperty("IP","127.0.0.1");
		properties.setProperty("port", "5435");

		try {
			// Crea el archivo de propiedades
			properties.store(new FileOutputStream(PROPERTIES_FILE), "Ejemplo Properties");

		} catch (IOException ex) {
			System.out.println("Error: No se ha podido crear el archivo de propiedades");
		}
	}

	private static void getXMLProperties(String XML_PROPERTIES_FILE) {

		String language = "ES";
		Calendar lastAccess = null;

		Properties properties = new Properties();

		// Leemos los datos del fichero appXML.properties;
		try {
			properties.loadFromXML(new FileInputStream(XML_PROPERTIES_FILE));
			language = properties.getProperty("language", "ES");
			// La fecha y hora del último acceso está guardado como tiempo en milisegundos
			long lastAccessInMillis = Long.valueOf(properties.getProperty("lastAccess"));
			lastAccess = Calendar.getInstance();
			lastAccess.setTimeInMillis(lastAccessInMillis);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Muestra en pantalla los valores de las variables language y lastAccess del
		// fichero XML
		System.out.println("Idioma: " + language);
		if (lastAccess != null) {
			String strLastAccess = (DateFormat.getDateTimeInstance()).format(lastAccess.getTime());
			System.out.println("Ultimo acceso: " + strLastAccess);
			System.out.println("Ultimo acceso: " + Instant.ofEpochMilli(Long.valueOf(properties.getProperty("lastAccess"))).atZone(ZoneId.systemDefault()).toLocalDate());
		} else {
			System.out.println("Es la primera vez que usa esta aplicación");
		}

		properties.setProperty("language", language);
		// Toma el valor que se almacenará en la propiedad lastAccess
		// en función de la hora actual, y en formato de milisegundos
		lastAccess = Calendar.getInstance();
		String lastAccessInMillis = String.valueOf(lastAccess.getTimeInMillis());
		properties.setProperty("lastAccess", lastAccessInMillis);

		try {
			// Crea el archivo de propiedades
			properties.storeToXML(new FileOutputStream(XML_PROPERTIES_FILE), "Ejemplo Properties");

		} catch (IOException ex) {
			System.out.println("Error: No se ha podido crear el archivo de propiedades");
		}
	}
}
