import java.util.Random;

public class PrinterUser implements Runnable {

	final private PrinterMonitor printersMonitor;
	final private Printer[] printers;
	private int id;
	private static int globalCounter = 0;
	
	public PrinterUser(PrinterMonitor printersMonitor, Printer[] printers) {
		this.printersMonitor = printersMonitor;
		this.printers = printers;
		this.id = globalCounter++;
	}
	
	public void run() {
		while(true) {
			String dataToPrint = preparePrintData(10);
			try {
				int reservedPrinterId = printersMonitor.makeReservation();
				System.out.println("User " +  this.id + " has acquired access to the printer " + reservedPrinterId);
				Printer providedPrinter = printers[reservedPrinterId];
				providedPrinter.usePrinter(dataToPrint);
				System.out.println("User " +  this.id + " has stopped using the printer " + reservedPrinterId);
				printersMonitor.releasePrinter(reservedPrinterId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String preparePrintData(int length) {
		 Random random = new Random();
		 StringBuilder builder = new StringBuilder(length);
		 for(int i = 0; i < length; i++) {
			 int randomInt = 97 + (int)(random.nextFloat() * (122 - 97 + 1));
			 builder.append((char) randomInt);
		 }
		 return builder.toString();
	}

}
