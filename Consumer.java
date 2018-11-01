

public class Consumer implements Runnable {
    private BoundedBuffer b;

    public Consumer(BoundedBuffer b) {
        this.b = b;
    }

   public void run() {

        for(int i = 0;  i < 1000;   i++) {
            try {
				b.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }

    }
}

