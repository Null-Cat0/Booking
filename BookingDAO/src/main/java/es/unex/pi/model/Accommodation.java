package es.unex.pi.model;

public class Accommodation {
	
	private long id;
	private String name;
	private int price;
	private String description;
	private long idp;
	private int numAccommodations;
	
	public Accommodation(String name2, int price2, String description2, int nAccommodations,long idp2) {
		this.name = name2;
		this.price =  price2;
		this.description = description2;
		this.numAccommodations = nAccommodations;
		this.idp = idp2;
		
	}

	public Accommodation() {
        // TODO Auto-generated constructor stub
    }
	public long getIdp() {
		return idp;
	}
	public void setIdp(long idp) {
		this.idp = idp;
	}
	public int getNumAccommodations() {
		return numAccommodations;
	}
	public void setNumAccommodations(int numAccom) {
		this.numAccommodations = numAccom;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
