package org.hogel.naroubrowser.rx;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;

public class EventHandler<T> {
    private final Observable<T> observable = Observable.create(new Observable.OnSubscribe<T>() {
        @Override
        public void call(Subscriber<? super T> subscriber) {
            subscribers.add(subscriber);
        }
    });

    private final List<Subscriber<? super T>> subscribers = new ArrayList<>();

    public void subscribe(Action1<T> action) {
        observable.subscribe(action);
    }

    public void subscribe(Subscriber<T> subscriber) {
        observable.subscribe(subscriber);
    }

    public void onNext(T val) {
        for (Subscriber<? super T> subscriber : subscribers) {
            subscriber.onNext(val);
        }
    }

    public void onCompleted() {
        for (Subscriber<? super T> subscriber : subscribers) {
            subscriber.onCompleted();
        }
    }
}
