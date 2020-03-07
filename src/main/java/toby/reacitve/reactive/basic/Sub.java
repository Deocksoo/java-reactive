package toby.reacitve.reactive.basic;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class Sub implements Subscriber<Integer> {
    @Override
    public void onSubscribe(Subscription subscription) {
        log.debug("onSubscribe: ");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Integer integer) {
        log.debug("onNext: {}", integer);
    }

    @Override
    public void onError(Throwable t) {
        log.debug("onError: {}", t);
    }

    @Override
    public void onComplete() {

    }
}
