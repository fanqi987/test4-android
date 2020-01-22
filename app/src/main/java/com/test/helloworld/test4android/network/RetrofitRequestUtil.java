package com.test.helloworld.test4android.network;

import android.util.Log;

import com.test.helloworld.test4android.bean.Province;

import java.io.IOException;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestUtil {

    private Retrofit mRetrofit;

    private String mBaseUrl = "https://android-server-fanqi.herokuapp.com/";
//    private String mBaseUrl = "https://www.baidu.com/";

    private RequestCallback requestCallback;

    public RetrofitRequestUtil(RequestCallback requestCallback) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        this.requestCallback = requestCallback;
    }

    public void request() {
        RetrofitRequestsInter requests = mRetrofit.create(RetrofitRequestsInter.class);
        Call<ResponseBody> responseBodyCall = requests.getCall();
//        Call<ResponseBody> responseBodyCall = requests.getCall();

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody responseString = response.body();

                    requestCallback.onSuccess("非rxjava\n" + responseString.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("aaa", t.toString());
            }
        });
    }

    public void request2() {
        RetrofitRequestsInter requestsInter = mRetrofit.create(RetrofitRequestsInter.class);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                //todo  被观察者被订阅时要进行什么操作？
                //todo 首先，被观察者（一条请求）先去执行网络操作
                //todo 之后获得执行结果response（一条响应）
                //todo 此时，response通过观察者（用户）去处理
                //todo 即onNext中的处理动作，在这里被回调
            }
        });
        requestsInter.getRxCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            requestCallback.onSuccess("rxjava \n" + value.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestCallback.onFailed(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }


}
