public class Main {

	public static void main(String[] args) {
		
		//boundedBufferProducerConsumerDemo();
		//printersDemo();
		waiterDemo();

	}
	
	public static void boundedBufferProducerConsumerDemo() {
		BoundedBuffer b = new BoundedBuffer();
		Thread p1 = new Thread(new Producer(b));
        Thread c1 = new Thread(new Consumer(b));
        Thread p2 = new Thread(new Producer(b));
        Thread c2 = new Thread(new Consumer(b));
        Thread p3 = new Thread(new Producer(b));
        Thread c3 = new Thread(new Consumer(b));
        p1.start();
        c1.start();
        p2.start();
        c2.start();
        p3.start();
        c3.start();
		try {
			p1.join();
			c1.join();
			p2.join();
			c2.join();
			p3.join();
			c3.join();
		} catch(InterruptedException e) {
			System.out.println("Error");
		}
		
	}
	
	public static void printersDemo() {
		Thread[] clients = new Thread[10];
		Printer[] printers = new Printer[3];
		for(int i = 0; i < 3; i++) {
			printers[i] = new Printer(i);
		}
		PrinterMonitor monitor = new PrinterMonitor(3, printers);
		for(int i = 0; i < 10; i++) {
			clients[i] = new Thread(new PrinterUser(monitor, printers));
		}
	
		for(Thread t : clients) {
			t.start();
		}
		
		for(Thread t : clients) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void waiterDemo() {
		Thread[] clients = new Thread[10];
		Table table = new Table();
		TableMonitor tableMonitor = new TableMonitor(table);
		for(int i = 0; i < 10; i++) {
			clients[i] = new Thread(new Guest(tableMonitor, table, i % 5));
		}
	
		for(Thread t : clients) {
			t.start();
		}
		
		while(true) {
			table.whatHappensAtTheTable();
		}
		
//		for(Thread t : clients) {
//			try {
//				t.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
