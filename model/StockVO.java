package model;

public class StockVO {
	private int st_no;
	private String st_name;
	private int st_price;
	private int st_amount;
	private String st_date;
	private String st_status;
	
	public int getSt_no() {
		return st_no;
	}
	public void setSt_no(int st_no) {
		this.st_no = st_no;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public int getSt_price() {
		return st_price;
	}
	public void setSt_price(int st_price) {
		this.st_price = st_price;
	}
	public int getSt_amount() {
		return st_amount;
	}
	public void setSt_amount(int st_amount) {
		this.st_amount = st_amount;
	}
	public String getSt_date() {
		return st_date;
	}
	public void setSt_date(String st_date) {
		this.st_date = st_date;
	}
	public String getSt_status() {
		return st_status;
	}
	public void setSt_status(String st_status) {
		this.st_status = st_status;
	}
	public StockVO(int st_no, String st_name, int st_price, int st_amount, String st_date, String st_status) {
		super();
		this.st_no = st_no;
		this.st_name = st_name;
		this.st_price = st_price;
		this.st_amount = st_amount;
		this.st_date = st_date;
		this.st_status = st_status;
	}
	public StockVO() {
		
	}
	@Override
	public String toString() {
		return "StockVO [st_no=" + st_no + ", st_name=" + st_name + ", st_price=" + st_price + ", st_amount="
				+ st_amount + ", st_date=" + st_date + ", st_status=" + st_status + "]";
	}
}
