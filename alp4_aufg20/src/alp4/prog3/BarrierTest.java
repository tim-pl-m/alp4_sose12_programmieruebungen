package alp4.prog3;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTest {


    class Worker extends Thread {
        CyclicBarrier barrier;
        // name of the thread
        String name;

        public Worker(String threadName, CyclicBarrier cBarrier) {
            barrier = cBarrier;
            name = threadName;
        }

        public void run() {
            System.out.println("WAITING says:" + name);
            try {
                barrier.await();
                System.out.println("WORKING HARD NOW says:"+ name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("FINISHED says: "+ name);
        }
    }

    public void testCyclicBarrier() {
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
                public void run() {
                    try {
                        System.out.println("START Barrier Action ....");
                        Thread.sleep(1000);
                        System.out.println("STOP Barrier Action ....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        for (int i = 0; i < 2; i++) {
            Worker worker = new Worker("Thread_"+i,barrier);
            worker.start();
        }

    }


    public static void main(String[] args) {
        BarrierTest t = new BarrierTest();
        t.testCyclicBarrier();
    }
}