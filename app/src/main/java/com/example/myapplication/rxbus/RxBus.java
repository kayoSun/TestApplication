package com.example.myapplication.rxbus;

import android.util.Log;

import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.components.RxActivity;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle4.components.support.RxFragment;
import com.trello.rxlifecycle4.components.support.RxFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class RxBus {
    public static final String TAG="RxBus";

    private final List<RxCallback> _callbacks = new ArrayList<>();
    private final Subject<Object> _bus = PublishSubject.create();
    private Observer<Object> _observer;
    private final static RxBus rxBus = new RxBus();

    public static RxBus link() {
        return rxBus;
    }

    private RxBus() {
        _observer = new RxObserver() {
            @Override
            public void onNext(@NonNull Object o) {
                RxBus.this.onNext(o);
            }
        };
        _bus.subscribeOn(AndroidSchedulers.mainThread()).subscribe(_observer);
    }

    public void send(RxEvent event) {
        _bus.onNext(event);
    }

    public void register(final RxCallback callback) {
        _callbacks.add(callback);
    }

    public void register(LifecycleProvider rxActivity, final RxCallback callback) {
        _bus.observeOn(AndroidSchedulers.mainThread())
                .compose(rxActivity.bindToLifecycle())
                .subscribe(new RxObserver() {
                    @Override
                    public void onNext(@NonNull Object o) {
                        if (o instanceof RxEvent) {
                            callback.onRxCall((RxEvent) o);
                        }
                    }
                });

    }

    public void unregister(RxCallback callback) {
        _callbacks.remove(callback);
    }

    private void onNext(Object ob) {
        Log.i(TAG, "onNext: "+ob.toString());
        if (!_callbacks.isEmpty() && ob instanceof RxEvent) {
            for (int i = 0; i < _callbacks.size(); i++) {
                RxCallback rxCallback = _callbacks.get(i);
                rxCallback.onRxCall((RxEvent) ob);
            }
        }
    }

    static class RxObserver implements Observer<Object> {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull Object o) {

        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
