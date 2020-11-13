package com.pjzhong.concurrency.example;

import com.pjzhong.concurrency.anno.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {

    private Object expensiveObject = null;

    public  Object getExpensiveObject() {
        if(expensiveObject == null) {
            expensiveObject = new Object();
        }

        return expensiveObject;
    }
}
