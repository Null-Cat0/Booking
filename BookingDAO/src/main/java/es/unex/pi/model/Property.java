package es.unex.pi.model;

public class Property {

	private long id;
	private String name;
	private String address;
	private String telephone;
	private double gradesAverage;
	private String city;
	private double centerDistance;
	private String description;
	private int petFriendly;
	private int available;
	private int idu;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getGradesAverage() {
		return gradesAverage;
	}
	public void setGradesAverage(double gradesAverage) {
		this.gradesAverage = gradesAverage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getIdu() {
		return idu;
	}
	public void setIdu(int idu) {
		this.idu = idu;
	}
	public double getCenterDistance() {
		return centerDistance;
	}
	public void setCenterDistance(double centerDistance) {
		this.centerDistance = centerDistance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPetFriendly() {
		return petFriendly;
	}
	public void setPetFriendly(int petFriendly) {
		this.petFriendly = petFriendly;
	}


	
	
}
