public class Printer {

	final private int id;
	private boolean isUsed;
	
	public Printer(int id) {
		this.id = id;
		this.isUsed = false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean checkAvailability() {
		return isUsed;
	}
	
	public void changeUsageStatus(boolean status) {
		isUsed = status;
	}
	
	public void usePrinter(String doc) {
		System.out.println(doc);
		try {
			Thread.sleep((long)(Math.random() * 15000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}