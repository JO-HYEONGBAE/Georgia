package model;

public class WorkVO {
	private int w_no;
	private String w_go;
	private String w_out;
	private String w_name;
	private int s_no;
	public int getW_no() {
		return w_no;
	}
	public void setW_no(int w_no) {
		this.w_no = w_no;
	}
	public String getW_go() {
		return w_go;
	}
	public void setW_go(String w_go) {
		this.w_go = w_go;
	}
	public String getW_out() {
		return w_out;
	}
	public void setW_out(String w_out) {
		this.w_out = w_out;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	public int getS_no() {
		return s_no;
	}
	public void setS_no(int s_no) {
		this.s_no = s_no;
	}
	public WorkVO(int w_no, String w_go, String w_out, String w_name, int s_no) {
		super();
		this.w_no = w_no;
		this.w_go = w_go;
		this.w_out = w_out;
		this.w_name = w_name;
		this.s_no = s_no;
	}
	
	public WorkVO() {
		
	}
	@Override
	public String toString() {
		return "WorkVO [w_no=" + w_no + ", w_go=" + w_go + ", w_out=" + w_out + ", w_name=" + w_name + ", s_no=" + s_no
				+ "]";
	}
	
	
}
