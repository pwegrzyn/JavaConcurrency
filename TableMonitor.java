import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TableMonitor {

	final private Lock lock;
	final private Condition tableFreeCond;
	final private Table table;
	final private Condition[] pairsFull;
	final private int[] pairsCount;
	
	public TableMonitor(Table table) {
		this.table = table;
		this.lock = new ReentrantLock(true);	// fair=true, forces chronological order for the scheduler
		this.tableFreeCond = lock.newCondition();
		pairsFull = new Condition[5];
		pairsCount = new int[5];
		for(int i = 0; i < 5; i++) {
			pairsFull[i] = lock.newCondition();
			pairsCount[i] = 0;
		}
	}

	public void wantTable(int id) throws InterruptedException {
		lock.lock();
		try {
			pairsCount[id]++;
			pairsFull[id].signal();
			while (pairsCount[id] < 2) {
				pairsFull[id].await();
			}
			while (table.checkPairBelonging() != -1 && table.checkPairBelonging() != id) {
				tableFreeCond.await();
			}
			if(table.checkPairBelonging() == -1) {
				table.setPairBelonging(id);
				tableFreeCond.signal();
			}
		} finally {
			lock.unlock();
		}
		
	}

	public void release() {
		lock.lock();
		try {
			int pair = table.checkPairBelonging();
			pairsCount[pair]--;
			if(pairsCount[pair] == 0) {
				table.setPairBelonging(-1);
				tableFreeCond.signal();
			}
		} finally {
			lock.unlock();
		}
	}
	
}