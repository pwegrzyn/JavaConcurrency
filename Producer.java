

public class Producer implements Runnable {
    private BoundedBuffer b;

    public Producer(BoundedBuffer b) {
        this.b = b;
    }

   public void run() {

        for(int i = 0;  i < 1000;   i++) {
            try {
				b.put("message-" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }

    }
}

