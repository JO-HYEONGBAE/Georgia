package model;

public class StaffVO {
	private int s_no;
	private String s_name;
	private String s_job;
	private String s_ptime;
	private String s_id;
	private String s_pwd;
	private String s_phone;
	private String s_address;
	private String s_hiredate;
	private String s_birth;
	private String s_gender;
	private String s_state;
	private String s_image;
	public String getS_image() {
		return s_image;
	}
	public void setS_image(String s_image) {
		this.s_image = s_image;
	}
	public int getS_no() {
		return s_no;
	}
	public void setS_no(int s_no) {
		this.s_no = s_no;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_job() {
		return s_job;
	}
	public void setS_job(String s_job) {
		this.s_job = s_job;
	}
	public String getS_ptime() {
		return s_ptime;
	}
	public void setS_ptime(String s_ptime) {
		this.s_ptime = s_ptime;
	}
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public String getS_pwd() {
		return s_pwd;
	}
	public void setS_pwd(String s_pwd) {
		this.s_pwd = s_pwd;
	}
	public String getS_phone() {
		return s_phone;
	}
	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}
	public String getS_address() {
		return s_address;
	}
	public void setS_address(String s_address) {
		this.s_address = s_address;
	}
	public String getS_hiredate() {
		return s_hiredate;
	}
	public void setS_hiredate(String s_hiredate) {
		this.s_hiredate = s_hiredate;
	}
	public String getS_birth() {
		return s_birth;
	}
	public void setS_birth(String s_birth) {
		this.s_birth = s_birth;
	}
	public String getS_gender() {
		return s_gender;
	}
	public void setS_gender(String s_gender) {
		this.s_gender = s_gender;
	}
	public String getS_state() {
		return s_state;
	}
	public void setS_state(String s_state) {
		this.s_state = s_state;
	}

	public StaffVO(int s_no, String s_name, String s_job, String s_ptime, String s_id, String s_pwd, String s_phone,
			String s_address, String s_hiredate, String s_birth, String s_gender, String s_state, String s_image) {
		super();
		this.s_no = s_no;
		this.s_name = s_name;
		this.s_job = s_job;
		this.s_ptime = s_ptime;
		this.s_id = s_id;
		this.s_pwd = s_pwd;
		this.s_phone = s_phone;
		this.s_address = s_address;
		this.s_hiredate = s_hiredate;
		this.s_birth = s_birth;
		this.s_gender = s_gender;
		this.s_state = s_state;
		this.s_image = s_image;
	}
	public StaffVO() {
		
	}
	@Override
	public String toString() {
		return "StaffVO [s_no=" + s_no + ", s_name=" + s_name + ", s_job=" + s_job + ", s_ptime=" + s_ptime + ", s_id="
				+ s_id + ", s_pwd=" + s_pwd + ", s_phone=" + s_phone + ", s_address=" + s_address + ", s_hiredate="
				+ s_hiredate + ", s_birth=" + s_birth + ", s_gender=" + s_gender + ", s_state=" + s_state + "]";
	}
	
	
}	

