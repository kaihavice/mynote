package com.xuyazhou.mynote.model.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


/**
 * 利用Rxjava实现的一直事件总线控制器
 * *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-28
 */

public class RxBus {


    private final FlowableProcessor<Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }


    /**
     * 发送事件
     */
    public void send(Object event) {
        mBus.onNext(event);
    }

    //添加订阅
    public <T> Disposable subscribe(Consumer<T> onNext, Class<T> type) {
        return mBus.hide().ofType(type).subscribe(onNext);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }


    /**
     * Stciky 相关
     */

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        send(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Flowable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Flowable<T> observable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return observable.mergeWith(Flowable.create(subscriber -> {
                    subscriber.onNext(eventType.cast(event));
                }, BackpressureStrategy.ERROR));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
