package es.unex.pi.model;

public class Favourite {
	private long idu;
	private long idp;

	public Favourite(long idu, long idp) {
		this.idu = idu;
		this.idp = idp;
	}

	public Favourite() {

	}
	
	
	public long getIdu() {
		return idu;
	}

	public void setIdu(long idu) {
		this.idu = idu;
	}

	public long getIdp() {
		return idp;
	}

	public void setIdp(long idp) {
		this.idp = idp;
	}
}
