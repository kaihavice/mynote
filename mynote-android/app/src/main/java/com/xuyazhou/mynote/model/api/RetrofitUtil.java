package com.xuyazhou.mynote.model.api;


import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xuyazhou.mynote.model.bean.Response;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络获得数据后的再一次切割封装
 * <p>
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-28
 */
public class RetrofitUtil {


    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Flowable<T> flatResponse(final Response<T> response) {
        return Flowable.create(flowableEmitter -> {
            if (response.success) {

                flowableEmitter.onNext(response.data);

            } else if (response.isTokenExpired()) {
                flowableEmitter.onError(new TokenException(response.errCode, response.message));
            } else {
                flowableEmitter.onError(new APIException(response.errCode, response.message));
            }

            flowableEmitter.onComplete();

        }, BackpressureStrategy.DROP);


    }


    /**
     * 自定义异常
     */
    public static class APIException extends Exception {
        public String getCode() {
            return code;
        }

        public String code;
        public String message;

        public APIException(String status, String message) {
            this.code = status;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    //token过期情况
    public static class TokenException extends Exception {

        public TokenException(String status, String message) {
            this.code = status;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public String code;
        public String message;

    }

    public static class TokenNullException extends Exception {

        public TokenNullException(String status, String message) {
            this.code = status;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public String code;
        public String message;

    } //用户token异常情况


    public static class BindCardFail extends Exception {

        public BindCardFail() {

        }

    }

    public static class PayWordFail extends Exception {

        public PayWordFail() {

        }

    }

    /**
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     * 利用RxLifeCyle自动管理observe生命周期
     */
    protected <T> FlowableTransformer<T, T> applySchedulersForActivity(RxAppCompatActivity context) {

        return upstream -> upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())//获得回调后在主线程处理
                .compose(context.bindToLifecycle())//利用RxLifeCyle自动管理observe生命周期
                .flatMap((Function) response ->
                        flatResponse((Response<Object>) response));
    }

    protected <T> FlowableTransformer<T, T> applySchedulersLifecycle(RxAppCompatActivity context) {

        return upstream -> upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())//获得回调后在主线程处理
                .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), ActivityEvent.DESTROY))
                .flatMap((Function) response ->
                        flatResponse((Response<Object>) response));
    }


    protected <T> FlowableTransformer<T, T> applySchedulersForFragment(RxFragment context) {

        return observable -> (observable).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(context.bindToLifecycle())
                .flatMap((Function) response ->
                        flatResponse((Response<Object>) response));
    }


}
