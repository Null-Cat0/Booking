package es.unex.giiis.pi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {
	String validacion;

	public Validador(String s) {
		this.validacion = s;
	}

	public boolean esValido(String aux) {
		return aux.matches(validacion);
	}

}
