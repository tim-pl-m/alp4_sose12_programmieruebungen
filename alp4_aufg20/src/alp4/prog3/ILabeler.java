package alp4.prog3;

import java.util.concurrent.BrokenBarrierException;

public interface ILabeler {
	void process(int[][] image, int[][] label) throws InterruptedException, BrokenBarrierException;
}
