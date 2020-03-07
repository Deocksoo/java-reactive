package toby.reacitve.reactive.basic;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Pub implements Publisher<Integer> {
    Iterable<Integer> iter;

    public Pub(Iterable<Integer> iter) {
        this.iter = iter;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        Subscription subscription = new MySubscription(iter, subscriber);
        subscriber.onSubscribe(subscription);
    }
}
