// NOTE: In this file, you should only modify the class ConcurrentIntegerSetSync
package exercises05;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentSkipListSet;

public interface ConcurrentIntegerSet {
    public boolean add(Integer element);

    public boolean remove(Integer element);

    public int size();
}

class ConcurrentIntegerSetBuggy implements ConcurrentIntegerSet {
    final private Set<Integer> set;

    public ConcurrentIntegerSetBuggy() {
        this.set = new HashSet<Integer>();
    }

    public boolean add(Integer element) {
        return set.add(element);
    }

    public boolean remove(Integer element) {
        return set.remove(element);
    }

    public int size() {
        return set.size();
    }
}

// TODO: Fix this class to pass your tests
//5.1.3
class ConcurrentIntegerSetSync implements ConcurrentIntegerSet {
    final private Set<Integer> set;

    public ConcurrentIntegerSetSync() {
        this.set = new HashSet<Integer>();
    }

    public synchronized boolean add(Integer element) {
        return set.add(element);
    }

    public synchronized boolean remove(Integer element) {
        return set.remove(element);
    }

    public int size() {
        return set.size();
    }
}
//5.1.4 
// Because the thread is now synchronized we have mutual exclusion and no thread can interleave with another.
 

class ConcurrentIntegerSetLibrary implements ConcurrentIntegerSet {
    final private Set<Integer> set;

    public ConcurrentIntegerSetLibrary() {
        this.set = new ConcurrentSkipListSet<Integer>();
    }

    public boolean add(Integer element) {
        return set.add(element);
    }

    public boolean remove(Integer element) {
        return set.remove(element);
    }

    public int size() {
        return set.size();
    }
}
