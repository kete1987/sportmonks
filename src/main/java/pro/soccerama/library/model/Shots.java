package pro.soccerama.library.model;

public class Shots 
{
	private int total = 0;
	private int ongoal = 0;
	private int offgoal = 0;
	private int blocked = 0;
	private int insidebox = 0;
	private int outsidebox = 0;
	
	public Shots() {}

	public int getTotal() {
		return total;
	}

	public int getOngoal() {
		return ongoal;
	}

	public int getOffgoal() {
		return offgoal;
	}

	public int getBlocked() {
		return blocked;
	}

	public int getInsidebox() {
		return insidebox;
	}

	public int getOutsidebox() {
		return outsidebox;
	}
}
