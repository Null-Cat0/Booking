package es.unex.pi.util;

import java.util.Collections;
import java.util.List;
import es.unex.pi.model.Property;

//El nombre es una referencia a rastreator
public class Ordenator {
	
	public static void ordenarLista(List<Property> listProperties, String[] valores) {
        if (valores != null && valores.length > 0) {
            switch (valores[0]) {
                case "grades":
                    Collections.sort(listProperties, Property.gradesAverageComparator.reversed());
                    break;
                default:
                    break;
            }
        }
    }
	
	
	public static void filtrarLista(List<Property> listProperties, String[] filtros) {
        if (filtros != null && filtros.length > 0) {
			switch (filtros[0]) {
			case "available":
				listProperties.removeIf(p -> p.getAvailable() == 0);
				break;
			case "notAvailable":
				listProperties.removeIf(p -> p.getAvailable() == 1);
				break;
			default:
				break;
			}
        }
    }
	
}
