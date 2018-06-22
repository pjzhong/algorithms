package concurrency.java_memory_model;

import concurrency.anno.ThreadSafe;

@ThreadSafe
public class DoubledCheckedLocking {
    private static volatile Object singleton;

    public static Object getObject() {
        if(singleton == null) {
            synchronized (DoubledCheckedLocking.class) {
                if(singleton == null) {
                    //1. allocate the memory space
                    //2. initialize the object
                    //3. point singleton to memory address where the new created object is.
                    singleton = new Object();
                    //If you do not ensuring that publishing the shared reference happens before another threads
                    //loads the shared reference, then the write of the reference to the new object can be reordered
                    //(from the perspective of the thread consuming the object) with writes to its field -
                    // the construction process of the object
                    // to 2-step and 3-step maybe reordered
                }
            }
        }
        return singleton;
    }
}
