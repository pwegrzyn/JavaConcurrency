public class Guest implements Runnable {

	final private TableMonitor tableMonitor;
	final private Table table;
	private int id;
	private int pair_id;
	private static int globalCounter = 0;
	
	public Guest(TableMonitor tableMonitor, Table table, int pair) {
		this.tableMonitor = tableMonitor;
		this.table = table;
		this.id = globalCounter++;
		this.pair_id = pair;
	}
	
	public void run() {
		while(true) {
			doOwnBussiness();
			try {
				tableMonitor.wantTable(pair_id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			table.useToEat();
			tableMonitor.release();
		}
	}
	
	private void doOwnBussiness() {
		try {
			Thread.sleep((long)(Math.random() * 15000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Guest-" + this.id + " from pair " + this.pair_id;
	}

}