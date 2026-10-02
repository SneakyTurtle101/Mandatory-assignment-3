package exercises05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.jupiter.api.BeforeEach;
// TODO: Very likely you need to expand the list of imports
import org.junit.jupiter.api.RepeatedTest;

public class ConcurrentSetTest {

    // Variable with set under test
    private ConcurrentIntegerSet set;

    // TODO: Very likely you should add more variables here

    // Uncomment the appropriate line below to choose the class to
    // test
    // Remember that @BeforeEach is executed before each test
    @BeforeEach
    public void initialize() {
        // init set
        // set = new ConcurrentIntegerSetBuggy();
        // set = new ConcurrentIntegerSetSync();
        set = new ConcurrentIntegerSetLibrary();
    }
    //5.1.1
    // TODO: Define your tests below
    @RepeatedTest(5000)
    void addTest() throws Exception {
        int threadnr = 10;
        CyclicBarrier cbt = new CyclicBarrier(threadnr);
        CyclicBarrier cbm = new CyclicBarrier(threadnr + 1);
        for (int i = 0; i < threadnr; i++) {
            new Thread(() -> {
                try {
                    cbt.await();
                    set.add(1);
                    cbm.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        cbm.await();
        assertEquals(1, set.size());
    }
    // it finds the interleaving where there is zero or more than 1
    // element in the set.

    //5.1.2
    @RepeatedTest(5000)
    void removeTest() throws Exception {
        set.add(1);
        int threadnr = 10;
        CyclicBarrier cbt = new CyclicBarrier(threadnr);
        CyclicBarrier cbm = new CyclicBarrier(threadnr + 1);
        for (int i = 0; i < threadnr; i++) {
            new Thread(() -> {
                try {
                    cbt.await();
                    set.remove(1);
                    cbm.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cbm.await();
        assertEquals(0, set.size());
    }
    // it finds the interleaving where there is less than zero or 1
    // element in the set.

}

// 5.1.4 
// There are no errors which was to be expected since the java ConcurrentIntegerSetLibrary is thread safe.

// 5.1.5
// No. Because if the test is wrong it tells us nothing about the tested collection.
// You can't prove that there is something wrong with the collection because it might as well be a fault in the test.

// 5.1.6
// it tells me that the possibilities tested does not fail. But it does not nessecarily tell you that the collection is 
// completely thread safe. You cannot test for the absence of bugs, only the precense of bugs.