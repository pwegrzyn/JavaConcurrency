
public class Table {

	private int belongsToPair = -1;
	
	public void useToEat() {
		try {
			Thread.sleep((long)(Math.random() * 10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setPairBelonging(int pair) {
		this.belongsToPair = pair;
	}
	
	public int checkPairBelonging() {
		return this.belongsToPair;
	}


	public void whatHappensAtTheTable() {
		System.out.println("Currently at the table: pair " + this.belongsToPair);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
