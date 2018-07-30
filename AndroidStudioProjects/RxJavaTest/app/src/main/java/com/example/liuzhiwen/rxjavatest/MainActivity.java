package com.example.liuzhiwen.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);


        //创建事件产生者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        //创建事件消费者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始盯着事件生产者，一有事件出来就吃掉");
            }

            @Override
            public void onNext(Integer value) {

                Toast.makeText(MainActivity.this, String.valueOf(value), Toast.LENGTH_LONG).show();
                Log.i(TAG, "value==" + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "事件生产者不再生产");
            }
        };

        //创建生产者和消费者的通道,张小黑在盯着你呢
        observable.subscribe(observer);

        //RxJava的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始盯着事件生产者，一有事件出来就吃掉");
            }

            @Override
            public void onNext(Integer value) {

                Toast.makeText(MainActivity.this, String.valueOf(value), Toast.LENGTH_LONG).show();
                Log.i(TAG, "value==" + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "事件生产者不再生产");
            }
        });
    }
}
