package es.unex.pi.model;

import java.util.Comparator;
import java.util.Objects;

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

	public Property() {
		// TODO Auto-generated constructor stub
	}

	public Property(String name, String address, String telephone, double gradesAverage, String city,
			double centerDistance, String description, int petFriendly, int available, int idu) {
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.gradesAverage = gradesAverage;
		this.city = city;
		this.centerDistance = centerDistance;
		this.description = description;
		this.petFriendly = petFriendly;
		this.available = available;
		this.idu = idu;

	}
	public Property(long id2,String name, String address, String telephone, String city,
			double centerDistance, String description, int petFriendly, int available, int idu) {
		this.id=id2;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.city = city;
		this.centerDistance = centerDistance;
		this.description = description;
		this.petFriendly = petFriendly;
		this.available = available;
		this.idu = idu;

	}
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

	public static Comparator<Property> gradesAverageComparator = Comparator.comparingDouble(Property::getGradesAverage);
	
	
	@Override
	public String toString() {
		return "Property [id=" + id + ", name=" + name + ", address=" + address + ", telephone=" + telephone
				+ ", gradesAverage=" + gradesAverage + ", city=" + city + ", centerDistance=" + centerDistance
				+ ", description=" + description + ", petFriendly=" + petFriendly + ", available=" + available
				+ ", idu=" + idu + "]";
	}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return id == property.id &&
                Objects.equals(name, property.name);
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
