package toby.reacitve.reactive;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PubSubTest {

    @Test
    void pubsub() throws InterruptedException {
        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
        ExecutorService es = Executors.newSingleThreadExecutor();

        Publisher publisher = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> it = iter.iterator();

                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        es.execute(() -> {
                            int i = 0;
                            try {
                                while (i++ < n) {
                                    if (it.hasNext()) {
                                        subscriber.onNext(it.next());
                                    } else {
                                        subscriber.onComplete();
                                        break;
                                    }
                                    Thread.sleep(1000);
                                }
                            } catch (RuntimeException | InterruptedException e) {
                                subscriber.onError(e);
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {// 최초 request는 여기서 해야한다.
                System.out.println(Thread.currentThread().getName() + "onSubscribe");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) { // 데이터를 하나씩 끌어옴.
                System.out.println(Thread.currentThread().getName() + "onNext" + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) { // 어떤 종류의 에러든 얘가 받아서 처리할 수 있음.
                // 이안에 try catch 구문이 필요 없다.
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        publisher.subscribe(subscriber);

        es.awaitTermination(10, TimeUnit.HOURS);
        es.shutdown();
    }
}