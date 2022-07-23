package es.com.kete1987.sportmonks.library.model.stats;

public class Shots 
{
	private Integer total = 0;
	private Integer ongoal = 0;
	private Integer offgoal = 0;
	private Integer blocked = 0;
	private Integer insidebox = 0;
	private Integer outsidebox = 0;
	
	public Shots() {}

	public Integer getTotal() {
		return total;
	}

	public Integer getOngoal() {
		return ongoal;
	}

	public Integer getOffgoal() {
		return offgoal;
	}

	public Integer getBlocked() {
		return blocked;
	}

	public Integer getInsidebox() {
		return insidebox;
	}

	public Integer getOutsidebox() {
		return outsidebox;
	}
}
