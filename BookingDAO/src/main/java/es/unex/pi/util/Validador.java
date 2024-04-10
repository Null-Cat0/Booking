package es.unex.pi.util;

public class Validador {
	String validacion;

	public Validador(String s) {
		this.validacion = s;
	}

	public boolean esValido(String aux) {
		return aux.matches(validacion);
	}
	public boolean esValido(int aux) {
		return aux >= 0;
	}

	public boolean esValido(String name, String address, String tel, String city, double dist, String description,
			int petFriendly, int available) {
		if (name != null && address != null && tel != null && city != null  && description != null)
				return true;
		return false;

	}

}
