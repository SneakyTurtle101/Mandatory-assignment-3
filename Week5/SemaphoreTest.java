package exercises05;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

public class SemaphoreTest {
    private SemaphoreImp sem;

    @BeforeEach
    public void init() {
        sem = new SemaphoreImp(5);
    }

    @RepeatedTest(5000)
    void overflowTest() throws Exception {

        CyclicBarrier cb = new CyclicBarrier(5 + 1);
        sem.release();
        sem.release();
        sem.release();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    sem.acquire();
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        cb.await();
        
        assertTimeout(Duration.ofSeconds(1), () -> sem.acquire());

    }
}
// 5.2.2
// This test should fail all 5000 times because it should not be possible to acquire a 6th time when the capacity is 5.
// However it successeds because there is no check for the state being negative which means that there can be more
// threads acquirering than permited.