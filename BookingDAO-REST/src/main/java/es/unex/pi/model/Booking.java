package es.unex.pi.model;

public class Booking {

	private long id;
	private long idu;
	private int totalPrice;

	public Booking(long id, long idu, int totalPrice) {
		this.id = id;
		this.idu = idu;
		this.totalPrice = totalPrice;
	}
	public Booking(long idu, int totalPrice) {
		this.idu = idu;
		this.totalPrice = totalPrice;
	}
	public Booking() {
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getIdu() {
		return idu;
	}

	public void setIdu(long idu) {
		this.idu = idu;
	}

	

}
