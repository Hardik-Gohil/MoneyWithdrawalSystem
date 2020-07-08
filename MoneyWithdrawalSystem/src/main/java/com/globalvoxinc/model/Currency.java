package com.globalvoxinc.model;

import java.io.Serializable;
import java.util.List;

public class Currency implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<Integer> choiceOfNotes;
	private double rateInINR;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getChoiceOfNotes() {
		return choiceOfNotes;
	}

	public void setChoiceOfNotes(List<Integer> choiceOfNotes) {
		this.choiceOfNotes = choiceOfNotes;
	}

	public double getRateInINR() {
		return rateInINR;
	}

	public void setRateInINR(double rateInINR) {
		this.rateInINR = rateInINR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Currency [name=" + name + ", choiceOfNotes=" + choiceOfNotes + ", rateInINR=" + rateInINR + "]";
	}
}
