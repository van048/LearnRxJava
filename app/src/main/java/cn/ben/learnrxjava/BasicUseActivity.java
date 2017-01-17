package cn.ben.learnrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

@SuppressWarnings("UnusedAssignment")
public class BasicUseActivity extends AppCompatActivity {

    private static final String TAG = BasicUseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_use);

        //创建一个观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "Error");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };

        // TODO: 2017/1/17 Observable.OnSubscribe<String>
        //使用Observable.create()创建被观察者
        Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        //订阅
        observable1.subscribe(observer);


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }
        };

        Observable observable2 = Observable.just("Hello", "World");

        String [] words = {"Hello", "World"};
        Observable observable3 = Observable.from(words);

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        Observable observable4 = Observable.from(list);
    }

    public void jump(@SuppressWarnings("UnusedParameters") View view) {
        startActivity(new Intent(this, ActionActivity.class));
    }
}
