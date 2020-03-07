package toby.reacitve.reactive.basic;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class MySubscription implements Subscription {
    Iterable<Integer> iter;
    Subscriber<? super Integer> subscriber;

    public MySubscription(Iterable<Integer> iter, Subscriber<? super Integer> subscriber) {
        this.iter = iter;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        try {
            iter.forEach(s -> subscriber.onNext(s));
            subscriber.onComplete();
        } catch (Throwable t) {
            subscriber.onError(t);
        }
    }

    @Override
    public void cancel() {

    }
}
