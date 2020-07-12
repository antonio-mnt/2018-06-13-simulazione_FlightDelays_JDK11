package it.polito.tdp.flightdelays.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Arco implements Comparable<Arco>{
	
	private Airport a1;
	private Airport a2;
	private Double peso;
	
	public Arco(Airport a1, Airport a2, Double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso/this.getDistanza(a1, a2);
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Arco [a1=" + a1 + ", a2=" + a2 + ", peso=" + peso + "]";
	}
	
	public double getDistanza(Airport a1, Airport a2) {
		
		double longitudine1 = a1.getLongitude();
		double latitudine1 = a1.getLatitude();
		LatLng posizione1 = new LatLng(latitudine1,longitudine1);
		
		double longitudine2 = a2.getLongitude();
		double latitudine2 = a2.getLatitude();
		LatLng posizione2 = new LatLng(latitudine2,longitudine2);
		
		double distanzaReale  = LatLngTool.distance(posizione1, posizione2, LengthUnit.KILOMETER);
		
		return distanzaReale;

}
	@Override
	public int compareTo(Arco o) {
		return -this.peso.compareTo(o.peso);
	}
}
