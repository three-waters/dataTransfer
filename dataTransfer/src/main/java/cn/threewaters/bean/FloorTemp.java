package cn.threewaters.bean;

import java.util.List;

import org.assertj.core.util.Lists;

public class FloorTemp {
	private String pjwd;
	private String zgwd;
	private String zdwd;

	private List<String> temps = Lists.newArrayList();

	public void add(String value) {
		if (value != null && !value.equals("NULL") && !value.equals("null") && !value.equals("")) {
			temps.add(value);
			if (pjwd == null) {
				pjwd = value;
			} else {
				double sum = 0.00;
				for (String temp : temps) {
					sum = sum + Double.parseDouble(temp);
				}
				pjwd = String.format("%.2f", sum / temps.size());
			}
			if (zgwd == null) {
				zgwd = value;
			} else {
				if (Double.parseDouble(value) > Double.parseDouble(zgwd)) {
					zgwd = value;
				}
			}
			if (zdwd == null) {
				zdwd = value;
			} else {
				if (Double.parseDouble(value) < Double.parseDouble(zdwd)) {
					zdwd = value;
				}
			}
		}
	}

	public String getPjwd() {
		return pjwd;
	}

	public void setPjwd(String pjwd) {
		this.pjwd = pjwd;
	}

	public String getZgwd() {
		return zgwd;
	}

	public void setZgwd(String zgwd) {
		this.zgwd = zgwd;
	}

	public String getZdwd() {
		return zdwd;
	}

	public void setZdwd(String zdwd) {
		this.zdwd = zdwd;
	}

	public List<String> getTemps() {
		return temps;
	}

	public void setTemps(List<String> temps) {
		this.temps = temps;
	}

}
