package model;

public class MenuVO {
	private int m_no;
	private String m_name;
	private int m_price;
	private int m_amount;
	private int st_no;
	
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public int getM_price() {
		return m_price;
	}
	public void setM_price(int m_price) {
		this.m_price = m_price;
	}
	public int getM_amount() {
		return m_amount;
	}
	public void setM_amount(int m_amount) {
		this.m_amount = m_amount;
	}
	public int getSt_no() {
		return st_no;
	}
	public void setSt_no(int st_no) {
		this.st_no = st_no;
	}
	public MenuVO(int m_no, String m_name, int m_price, int m_amount, int st_no) {
		super();
		this.m_no = m_no;
		this.m_name = m_name;
		this.m_price = m_price;
		this.m_amount = m_amount;
		this.st_no = st_no;
	}
	public MenuVO() {
		
	}
	@Override
	public String toString() {
		return "MenuVO [m_no=" + m_no + ", m_name=" + m_name + ", m_price=" + m_price + ", m_amount=" + m_amount
				+ ", st_no=" + st_no + "]";
	}
	
	
	
}
