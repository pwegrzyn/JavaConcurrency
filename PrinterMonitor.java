import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {

	final private Lock lock;
	final private Condition freePrinterExists;
	private int availablePrintersNumber;
	final private Printer[] printers;
	
	public PrinterMonitor(int numberOfPrinters, Printer[] printers) {
		this.lock = new ReentrantLock(true);	// fair=true, forces chronological order for the scheduler
		this.freePrinterExists = lock.newCondition();
		this.availablePrintersNumber = numberOfPrinters;
		this.printers = printers;
	}
	
	public int makeReservation() throws InterruptedException {
		lock.lock();
		try {
			while(availablePrintersNumber == 0) {
				freePrinterExists.await();
			}
			int result = -1;
			for(Printer p : printers) {
				if(!p.checkAvailability()) {
					result = p.getId();
					p.changeUsageStatus(true);
					availablePrintersNumber--;
					break;
				}
			}
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	public void releasePrinter(int printerId) {
		lock.lock();
		try {
			printers[printerId].changeUsageStatus(false);
			availablePrintersNumber++;
			freePrinterExists.signal();
		} finally {
			lock.unlock();
		}
	}
	
}
