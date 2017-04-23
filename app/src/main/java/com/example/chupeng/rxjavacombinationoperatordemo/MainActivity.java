package com.example.chupeng.rxjavacombinationoperatordemo;

import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainActivity extends AppCompatActivity {
    private Button mergeButton;
    private Button startWithButton;
    private Button concatButton;
    private Button zipButton;
    private Button combineLatestButton;
    private Button switchOnNextButton;
    private Button joinButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mergeButton = (Button) findViewById(R.id.mergeButton);
        startWithButton = (Button) findViewById(R.id.startWithButton);
        concatButton = (Button) findViewById(R.id.concatButton);
        zipButton = (Button) findViewById(R.id.zipButton);
        combineLatestButton = (Button) findViewById(R.id.combineLatestButton);
        switchOnNextButton = (Button) findViewById(R.id.switchOnNextButton);
        joinButton = (Button) findViewById(R.id.joinButton);

        mergeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                final String[] upperCase = new String[]{"A", "B", "C", "D", "E", "F"};
                Observable<String> observableA = Observable.interval(300, TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, String>()
                        {
                            public String call(Long aLong)
                            {
                                return upperCase[aLong.intValue()];
                            }
                        })
                        .take(upperCase.length);

                Observable<Long> observableB = Observable.interval(500, TimeUnit.MILLISECONDS)
                        .take(6);

                Observable.merge(observableA, observableB)
                        .subscribe(new Action1<Serializable>()
                        {
                            public void call(Serializable serializable)
                            {
                                Log.d("merge", serializable.toString());
                            }
                        });
            }
        });

        startWithButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                /*Observable<String> observableA = Observable.just("A", "B", "C", "D", "E", "F");
                List<String> list = new ArrayList<String>();
                list.add("a");
                list.add("b");
                list.add("c");
                list.add("d");
                list.add("e");
                list.add("f");
                observableA.startWith(list)
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("startWith", s);
                            }
                        });*/
                Observable<String> observableA = Observable.just("A", "B", "C", "D", "E", "F");
                Observable<String> observableB = Observable.just("a", "b", "c", "d", "e", "f");
                observableA.startWith(observableB)
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("startWith", s);
                            }
                        });
            }
        });

        concatButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                final String[] upperCase = new String[]{"A", "B", "C", "D", "E", "F"};
                Observable<String> observableA = Observable.interval(300, TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, String>()
                        {
                            public String call(Long aLong)
                            {
                                return upperCase[aLong.intValue()];
                            }
                        })
                        .take(upperCase.length);

                Observable<Long> observableB = Observable.interval(500, TimeUnit.MILLISECONDS)
                        .take(6);

                Observable.concat(observableA, observableB)
                        .subscribe(new Action1<Serializable>()
                        {
                            public void call(Serializable serializable)
                            {
                                Log.d("concat", serializable.toString());
                            }
                        });
            }
        });

        zipButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable<String> observableA = Observable.just("A", "B", "C", "D", "E", "F");
                Observable<String> observableB = Observable.just("a", "b", "c", "d", "e", "f");
                Observable.zip(observableA, observableB, new Func2<String, String, String>()
                {
                    public String call(String s, String s2)
                    {
                        return s + "+" + s2;
                    }
                })
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("zip", s);
                            }
                        });
            }
        });

        combineLatestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                final String[] upperCase = new String[]{"A", "B", "C", "D", "E", "F"};
                Observable<String> observableA = Observable.interval(300, TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, String>()
                        {
                            public String call(Long aLong)
                            {
                                return upperCase[aLong.intValue()];
                            }
                        })
                        .take(upperCase.length);

                Observable<Long> observableB = Observable.interval(500, TimeUnit.MILLISECONDS)
                        .take(6);

                Observable.combineLatest(observableA, observableB, new Func2<String, Long, String>()
                {
                    public String call(String s, Long aLong)
                    {
                        return s + "+" + aLong;
                    }
                })
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("combineLatest", s);
                            }
                        });
            }
        });

        switchOnNextButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

            }
        });

        joinButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable<String> observableA = Observable.just("A", "B", "C", "D", "E", "F");
                Observable<String> observableB = Observable.just("1", "2", "3");
                observableA.join(observableB,
                        new Func1<String, Observable<String>>()
                        {
                            public Observable<String> call(String s)
                            {
                                Log.d("Func1.1", s);
                                return Observable.just(s).delay(800, TimeUnit.MILLISECONDS);
                            }
                        },
                        new Func1<String, Observable<String>>()
                        {
                            public Observable<String> call(String s)
                            {
                                Log.d("Func1.2", s);
                                return Observable.just(s).delay(200, TimeUnit.MILLISECONDS);
                            }
                        },
                        new Func2<String, String, String>()
                        {
                            public String call(String s, String s2)
                            {
                                return s+s2;
                            }
                        })
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("join", s);
                            }
                        });
            }
        });
    }
}
