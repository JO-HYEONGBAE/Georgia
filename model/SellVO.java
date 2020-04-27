package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class SellVO {
	private int se_no;
	private String se_name;
	private int se_price;
	private String se_date;
	private int se_amount;
	private int m_no;
	private int me_no;
	private int se_check;
	
	
	
	public int getSe_check() {
		return se_check;
	}


	public void setSe_check(int se_check) {
		this.se_check = se_check;
	}


	public int getSe_no() {
		return se_no;
	}


	public void setSe_no(int se_no) {
		this.se_no = se_no;
	}


	public String getSe_name() {
		return se_name;
	}


	public void setSe_name(String se_name) {
		this.se_name = se_name;
	}


	public int getSe_price() {
		return se_price;
	}


	public void setSe_price(int se_price) {
		this.se_price = se_price;
	}


	public String getSe_date() {
		return se_date;
	}


	public void setSe_date(String se_date) {
		this.se_date = se_date;
	}


	public int getSe_amount() {
		return se_amount;
	}


	public void setSe_amount(int se_amount) {
		this.se_amount = se_amount;
	}


	public int getM_no() {
		return m_no;
	}


	public void setM_no(int m_no) {
		this.m_no = m_no;
	}


	public int getMe_no() {
		return me_no;
	}


	public void setMe_no(int me_no) {
		this.me_no = me_no;
	}

	public SellVO(int se_no, String se_name, int se_price, String se_date, int se_amount, int m_no, int me_no) {
		super();
		this.se_no = se_no;
		this.se_name = se_name;
		this.se_price = se_price;
		this.se_date = se_date;
		this.se_amount = se_amount;
		this.m_no = m_no;
		this.me_no = me_no;
	}
	public SellVO() {
		
	}
	public SellVO(int se_no, String se_name, int se_price, String se_date, int se_amount, int m_no, int me_no,
			int se_check) {
		super();
		this.se_no = se_no;
		this.se_name = se_name;
		this.se_price = se_price;
		this.se_date = se_date;
		this.se_amount = se_amount;
		this.m_no = m_no;
		this.me_no = me_no;
		this.se_check = se_check;
	}


	@Override
	public String toString() {
		return "SellVO [se_no=" + se_no + ", se_name=" + se_name + ", se_price=" + se_price + ", se_date=" + se_date
				+ ", se_amount=" + se_amount + ", m_no=" + m_no + ", me_no=" + me_no + "]";
	}
	
	
	
	
}
