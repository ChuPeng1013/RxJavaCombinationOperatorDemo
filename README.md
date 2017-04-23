# RxJavaCombinationOperatorDemo
## RxJava中部分组合类操作符，包括Merge()、StartWith()、Concat()、Zip()、CombineLatest()、SwitchOnNext()、Join()
### (1)Merge
merge(Observable, Observable)将两个Observable发射的事件序列组合并成一个事件序列，就像是一个Observable发射的一样。你可以简单的将它理解为两个Observable合并成了一个Observable，合并后的数据是无序的。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/merge.png)<br>
我们看下面的例子，一共有两个Observable：一个用来发送字母，另一个用来发送数字；现在我们需要两连个Observable发射的数据合并。<br>
```Java
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
```
所以此时控制台将会打印出：A0BC1D2EF345<br>
merge(Observable[])将多个Observable发射的事件序列组合并成一个事件序列，就像是一个Observable发射的一样。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/merge1.png)
### (2)StartWith
startWith(T)用于在源Observable发射的数据前插入数据。使用startWith(Iterator<T>)我们还可以在源Observable发射的数据前插入Iterator。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/startWith.png)<br>
这里我们将前面Merge操作符的例子拿过来稍做修改，并将操作符换成startWith。<br>
```Java
Observable<String> observableA = Observable.just("A", "B", "C", "D", "E", "F");  
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
        });
```
所以此时控制台将会打印出：abcdefABCDEF<br>
startWith(Observable<T>)用于在源Observable发射的数据前插入另一个Observable发射的数据(这些数据会被插入到 源Observable发射数据的前面)。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/startWith1.png)<br>
这里我们将前面Merge操作符的例子拿过来稍做修改，并将操作符换成startWith。<br>
```Java
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
```
所以此时控制台将会打印出：abcdefABCDEF<br>
### (3)Concat
concat(Observable<? extends T>, Observable<? extends T>)和concat(Observable<？ extends Observable<T>>)用于将多个Observable发射的的数据进行合并发射，concat严格按照顺序发射数据，前一个Observable没发射玩是不会发射后一个Observable的数据的。它和merge、startWitch和相似，不同之处在于merge合并后发射的数据是无序的，startWitch只能在源Observable发射的数据前插入数据，而concat是在另一个Observable上进行合并并且合并的发射数据是有序的。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/concat.png)<br>
这里我们将前面Merge操作符的例子拿过来稍做修改，并将操作符换成concat。<br>
```Java
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
```
然后我们看看执行结果，在控制台中可以打印出：ABCDEF012345<br>
### (4)Zip
zip(observableA, observableB, Func2)用来合并两个Observable对象发射的数据项并合成一个新Observable对象，根据Func2函数生成一个新的值并发射出去，在这里Func2就相当于observableA和observableB的合并规则，当其中一个Observable对象发送数据结束或者出现异常后，另一个Observable对象也将停在发射数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/zip.png)<br>
这里我们将前面Merge操作符的例子拿过来稍做修改，并将操作符换成zip。<br>
```Java
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
```
然后我们看看执行结果，在控制台中可以打印出：A+a B+b C+c D+d E+e F+f<br>
### (5)CombineLatest
combineLatest(observableA, observableB, Func2)用于将两个Observable最近发射的数据已经Func2函数的规则进行组合。<br>
combineLatest()和zip()都是对observableA和observableB按照Func2中制定的规则进行组合，二者最大的不同在于，zip()的组合顺序是observableA和observableB中的元素有一一对应的关系，相同位置的元素按照Func2中制定的规则进行组合，combineLatest()就没有这种所谓的一一对应的关系，而是observableA或者observableB发射一个元素时，这个元素会向前去寻找另一个observable发射出来的元素，直到寻找到一个为止，然后再按照Func2中制定的规则进行组合。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/combineLatest.png)<br>
这里我们将前面Merge操作符的例子拿过来稍作修改，并将操作符换成combineLatest。<br>
```Java
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
```
然后我们看看执行结果，在控制台中可以打印出：A+0 B+0 C+0 C+1 D+1 D+2 E+2 F+2 F+3 F+4 F+5<br>
### (6)SwitchOnNext
switchOnNext(Observable<? extends Observable<? extends T>>是把一组Observable转换成一个Observable，转换规则为，对于这组Observable中的每一个Observable所产生的结果，如果在同一个时间内存在两个或多个Observable提交的结果，只取最后一个Observable提交的结果给订阅者。也就是说如果一个小的Observable正在发射数据的时候，源Observable又发射出一个新的小Observable，则前一个Observable发射的数据会被抛弃，直接发射新 的小Observable所发射的数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/switchOnNext.png)<br>
在官方给出的原理图中可以看出黄色的圆形丢失了，这就是因为在黄色的圆形发射时，另一个Observable的黄色的三角形也在发射，这时switchOnNext就会将黄色的圆形抛弃直接发射黄色的三角形。<br>
### (7)Join
join(Observable, Func1, Func1, Func2)我们先介绍下join操作符的4个参数：<br>
* Observable：源Observable需要组合的Observable,这里我们姑且称之为目标Observable<br>
* Func1：接收从源Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了源Observable发射出来的数据的有效期<br>
* Func1：接收目标Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了目标Observable发射出来的数据的有效期<br>
* Func2：接收从源Observable和目标Observable发射出来的数据，并将这两个数据组合后返回<br>
<br>
所以Join操作符的语法结构大致是这样的：observableA.join(observableB, 控制observableA发射数据有效期的函数， 控制observableB发射数据有效期的函数，两个observable发射数据的合并规则)，它是将两个Observable产生的结果合并成一个新Observable对象。<br>
join操作符的效果类似于排列组合，把第一个数据源A作为基座窗口，他根据自己的节奏不断发射数据元素，第二个数据源B，每发射一个数据，我们都把它和第一个数据源A中已经发射的数据进行一对一匹配；举例来说，如果某一时刻B发射了一个数据“B”，此时A已经发射了0，1，2，3共四个数据，那么我们的合并操作就会把“B”依次与0，1，2，3配对，得到四组数据： [0, B]、[1, B]、[2, B]、[3, B]。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/join.png)<br>
这里我们将前面Merge操作符的例子拿过来稍做修改，并将操作符换成join。<br>
```Java
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
```
然后我们看看执行结果，在控制台中可以打印出：<br>
![](https://github.com/ChuPeng1013/RxJavaCombinationOperatorDemo/raw/master/material/joinResult.png)<br>
通过控制台打印出的结果可以看出，第一个Func1接收从源Observable发射来的数据并且数据全部发送完毕以后，才到第二个Func1，在第二个Func1中接收目标Observable发射来的数据，并且目标Observable每发射一个数据就根据Func2中的组合规则进行组合并且发射。<br>
[想了解更多关于Android开发的内容欢迎进入我的CSDN博客](http://blog.csdn.net/zackchu)

 
 
 
 
 
 
 
 
 
 
 
 
 
 
