package ch1;

import io.reactivex.Observable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartRxJavaTest {

    @Test
    @DisplayName("TestObserver를 활용한 테스트 코드")
    void testByJunit() {
        String[] data = {"1", "2", "3"};
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);

        Integer[] expected = {1, 2, 3};

        source.test().assertResult(expected);
    }
}