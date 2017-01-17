package com.mac.training.rxjava1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Subscription mySubscription;
    private TextView tV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tV = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!mySubscription.isUnsubscribed()|| (mySubscription == null))
        mySubscription.unsubscribe();

    }

    public void onDo(View view) {

        doIt(6);

    }
    public void doIt(final int i){
        Observable<Integer> myObservable =
                Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                try {
                        Thread.sleep(i * 1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /* Make dummy data */

                return i;
            }

        });

        mySubscription = myObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("FTMACTAG", "Rslt: " + integer);
                        tV.setText("It ran for " + integer + " seconds");
                    }

                });
    }
}
