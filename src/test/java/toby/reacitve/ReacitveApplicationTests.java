package toby.reactive;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ReactiveApplicationTests {

    @Test
    void iterable() {
        Iterable<Integer> iter = () -> new Iterator<Integer>() {
            private int i = 0;
            private static final int MAX = 10;

            @Override
            public boolean hasNext() {
                return i < MAX;
            }

            @Override
            public Integer next() {
                return ++i;
            }
        };

        for (Integer i : iter) {
            System.out.println(i);
        }
    }

    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    @Test
    void observable() {
        Observer ob = (o, arg) -> System.out.println(Thread.currentThread().getName() + " " + arg);

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        io.run();

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + " " + "EXIT");
        es.shutdown();
    }
}
