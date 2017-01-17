package cn.ben.learnrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("UnusedAssignment")
public class ActionActivity extends AppCompatActivity {

    private static final String TAG = ActionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        Observable.just("Hello", "World")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });

        Observable observable = Observable.just("Hello", "World");
        //处理onNext()中的内容
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        };
        //处理onError()中的内容
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        //处理onCompleted()中的内容
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "Completed");
            }
        };

        //使用 onNextAction 来定义 onNext()
        Observable.just("Hello", "World").subscribe(onNextAction);
        //使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        Observable.just("Hello", "World").subscribe(onNextAction, onErrorAction);
        //使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        Observable.just("Hello", "World").subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    public void jump(@SuppressWarnings("UnusedParameters") View view) {
        startActivity(new Intent(this, MapActivity.class));
    }
}
