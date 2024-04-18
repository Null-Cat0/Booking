package es.unex.pi.model;

public class Review {

	private long idp;
	private long idu;
	private String review;
	private int grade;
	
	
	public Review(long idp, long idu, String review, int grade) {
		this.idp = idp;
		this.idu = idu;
		this.review = review;
		this.grade = grade;
	}
	
	public Review() {
		
	}	
	public long getIdp() {
		return idp;
	}
	public void setIdp(long idr) {
		this.idp = idr;
	}
	public long getIdu() {
		return idu;
	}
	public void setIdu(long idu) {
		this.idu = idu;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public String toString() {
		return "Review [idp=" + idp + ", idu=" + idu + ", review=" + review + ", grade=" + grade + "]";
	}
	
	
	
	
}
