package cn.ben.learnrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SchedulerActivity extends AppCompatActivity {

    private static final String TAG = SchedulerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        rxJavaTest3();
    }

    private void rxJavaTest3() {
        Observable.just("Hello", "Word")
                // TODO: 2017/1/18 subscribe() 发生在新的线程 Creates and returns a Scheduler that creates a new Thread for each unit of work
                .subscribeOn(Schedulers.newThread())//指定 subscribe() 发生在新的线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });
    }
}
