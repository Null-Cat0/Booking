package es.unex.pi.model;

public class BookingsAccommodations {

	private long idb;
	private long idacc;
	private long numAccommodations; 
	

	public BookingsAccommodations(long idb, long idacc, long numAccom) {
		this.idb = idb;
		this.idacc = idacc;
		this.numAccommodations = numAccom;
	}

	public BookingsAccommodations(long idacc, long numAccom) {
		this.idacc = idacc;
		this.numAccommodations = numAccom;
	}
	public BookingsAccommodations() {
	
	}	
	public long getIdb() {
		return idb;
	}

	public void setIdb(long idb) {
		this.idb = idb;
	}

	public long getIdacc() {
		return idacc;
	}

	public void setIdacc(long idacc) {
		this.idacc = idacc;
	}

	public long getNumAccommodations() {
		return numAccommodations;
	}

	public void setNumAccommodations(long numAccom) {
		this.numAccommodations = numAccom;
	}

}
