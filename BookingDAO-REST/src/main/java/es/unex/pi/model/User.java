package es.unex.pi.model;

public class User {

	private long id;
	private String name;
	private String surname;
	private String email;
	private String password;

	public User(String name2, String surname2, String email2, String password2) {
		// TODO Auto-generated constructor stub
		this.name = name2;
		this.surname = surname2;
		this.email = email2;
		this.password = password2;
	}
	public User(Long id2,String name2, String surname2, String email2, String password2) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.name = name2;
		this.surname = surname2;
		this.email = email2;
		this.password = password2;
	}

	public User() {
		// TODO Auto-generated constructor stub
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + "]";
	}
}
