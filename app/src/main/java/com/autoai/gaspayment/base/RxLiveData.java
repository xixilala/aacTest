package com.autoai.gaspayment.base;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.autoai.gaspayment.utils.MainThreadAssert;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.android.BuildConfig;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class RxLiveData<T> {

    private static final String TAG = "RxLiveData";

    public static class State{
        public final Throwable mThrowable;

        private State(Throwable throwable){
            this.mThrowable = throwable;
        }

        public boolean isStart(){
            return this == START;
        }

        public boolean isComplete(){
            return this == COMPLETE;
        }

        public boolean isCancel(){
            return this == CANCEL;
        }

        public boolean isError(){
            return this.mThrowable != null;
        }
    }

    public abstract static class StateObserver implements Observer<State>{
        protected RxLiveData mRxLiveData;
    }

    private static final State START = new State(null);
    private static final State COMPLETE = new State(null);
    private static final State CANCEL = new State(null);
    private final MutableLiveData<T> mData;
    //true:start,false:completed,null:reset
    private final MutableLiveData<State> mState;
    private final AtomicReference<LiveDataSubscriber> mSubscriber;

    public RxLiveData(){
        mData = new MutableLiveData<>();
        mState = new MutableLiveData<>();
        mSubscriber = new AtomicReference<>();
    }

    /**
     *
     * @param publisher
     * @param force 是否强制执行,界面自动加载,使用false,主动请求,使用true
     */
    public void execute(@NonNull Publisher<T> publisher, boolean force){
        if (force){
            cancel();
        } else {
            if (mSubscriber.get() != null || mData.getValue() != null){
                return;
            }
        }
        LiveDataSubscriber s = new LiveDataSubscriber();
        mSubscriber.set(s);
        publisher.subscribe(s);
    }

    public void cancel(){
        LiveDataSubscriber s = mSubscriber.getAndSet(null);
        if (s != null){
            s.cancelSubscription();
        }
    }

    final class LiveDataSubscriber extends AtomicReference<Subscription> implements Subscriber<T>{

        @Override
        public void onSubscribe(Subscription s) {
            if (BuildConfig.DEBUG){
                Log.d(TAG, "onSubscribe:");
                MainThreadAssert.assertMainThread();
            }
            if (compareAndSet(null, s)){
                s.request(Long.MAX_VALUE);
                setState(START);
            } else {
                s.cancel();
                setState(CANCEL);
            }
        }

        @Override
        public void onNext(T t) {
            if (BuildConfig.DEBUG){
                Log.d(TAG, "onNext:");
                MainThreadAssert.assertMainThread();
            }
            setData(t);
        }

        @Override
        public void onError(Throwable t) {
            if (BuildConfig.DEBUG){
                Log.d(TAG, "onError:");
                MainThreadAssert.assertMainThread();
            }
            mSubscriber.compareAndSet(this, null);
            t.printStackTrace();
            setState(new State(t));
        }


        @Override
        public void onComplete() {
            if (BuildConfig.DEBUG){
                Log.d(TAG, "onComplete:");
                MainThreadAssert.assertMainThread();
            }
            mSubscriber.compareAndSet(this, null);
            setState(COMPLETE);
        }

        public void cancelSubscription(){
            if (BuildConfig.DEBUG){
                Log.d(TAG, "cancelSubscription:");
                MainThreadAssert.assertMainThread();
            }
            Subscription s = get();
            if (s != null){
                s.cancel();
                setState(CANCEL);
            }
        }
    }

    public void observeData(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        mData.observe(owner, observer);
    }

    public void observeState(@NonNull LifecycleOwner owner, @NonNull final Observer<State> observer) {
        autoInjectStateObserver(observer);
        mState.observe(owner, observer);
    }

    public void observeDataForever(@NonNull final Observer<T> observer) {
        mData.observeForever(observer);
    }

    public void observeStateForever(@NonNull final Observer<State> observer) {
        autoInjectStateObserver(observer);
        mState.observeForever(observer);
    }

    public void removeDataObservers(@NonNull final LifecycleOwner owner) {
        mData.removeObservers(owner);
    }

    public void removeStateObservers(@NonNull final LifecycleOwner owner) {
        mState.removeObservers(owner);
    }

    public void removeDataObserver(@NonNull final Observer<T> observer) {
        mData.removeObserver(observer);
    }

    public void removeStateObserver(@NonNull final Observer<State> observer) {
        mState.removeObserver(observer);
    }

    private void autoInjectStateObserver(@NonNull final Observer<State> observer) {
        if (observer instanceof StateObserver) {
            if (((StateObserver) observer).mRxLiveData != null) {
                throw new IllegalStateException("Reuse of stateobserver is not allowed");
            }
            ((StateObserver) observer).mRxLiveData = this;
        }
    }

    private void setData(@Nullable T data) {
        if (MainThreadAssert.isMainThread()) {
            mData.setValue(data);
        } else {
            mData.postValue(data);
        }
    }

    private void setState(@Nullable State state) {
        if (MainThreadAssert.isMainThread()) {
            mState.setValue(state);
        } else {
            mState.postValue(state);
        }
    }

    public void clearData() {
        setData(null);
    }

    public void clearError() {
        State state = mState.getValue();
        if (state != null && state.isError()) {
            setState(null);
        }
    }

}
