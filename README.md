# HandsOnArchitectureComponents
- https://developer.android.com/topic/libraries/architecture/index.html
- https://riggaroo.co.za/android-architecture-components-looking-room-livedata-part-1/

↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓上記リンクの和訳↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

## 新しいArchitectureComponentsとは何か？

アーキテクチャコンポーネントフレームワークは、Androidアプリケーションを作成するための基礎となる一連のライブラリとガイドラインです。開発者が幅広いアプリケーションにわたって直面する一般的なシナリオに対処します。このフレームワークは、ボイラープレートと反復コードの量を減らし、アプリケーションのコア機能に集中することを目指しています。

アーキテクチャコンポーネントの基本ブロックには、次のものがあります。

- `Room` - SQLiteオブジェクトマッパー。 ORMliteやgreenDAOなどの他のライブラリと非常によく似ています。これはSQLを使用しており、依然としてクエリに対してコンパイル時間の保証が可能です。
- `LiveData` - ライフサイクルを意識した観測可能なコアコンポーネント。
- `ViewModel` - アクティビティ/フラグメントのためのアプリケーションの残りの部分との通信ポイント。それらはUIコードなしで、アクティビティやフラグメントよりも長持ちします。
- `Lifecycle` - アーキテクチャコンポーネントの中核部分であり、コンポーネント（アクティビティなど）のライフサイクル状態に関する情報が含まれています。
- `LifecycleOwner` - ライフサイクル（アクティビティ、フラグメント、プロセス、カスタムコンポーネント）を持つコンポーネントのコアインタフェース。
- `LifecycleObserver` - 特定のLifecycleメソッドがトリガされたときに何が起きるかを指定します。 LifecycleObserverを作成すると、コンポーネントを自己完結型にすることができます。

## Roomとは何か？

ルームはAndroidアプリでデータベースを作成する新しい方法です。 `Room`には、アプリケーションにデータを保存するために事前に書かなければならない定型コードがたくさんあります。 `Room`はJavaクラスとSQLiteの間のORMです。 `Room`では、もはやカーソルとローダーを使用する必要はありません。 `Room`は本格的なORMではありません。たとえば、他のORMソリューションが提供するような複雑なオブジェクトのネストを行うことはできません。

`Room`では、データを照会できるいくつかの方法があります。

- LiveDataは、更新を受け取るために登録できるイベントのストリームを公開するクラスです。 これは非同期であるため、メインスレッドで使用できます。
- RxJava2 Flowable抽象クラスを使用します。
- AsyncTaskなどのバックグラウンドスレッドに同期呼び出しを配置します。 （`Room`では、メインスレッドでデータベースクエリを発行することはできません（ANRsを生成できるため）。

## LiveDataとは何か？

`LiveData`を使用すると、明示的で堅固な依存関係のパスを作成せずに、アプリケーションの複数のコンポーネントにわたるデータの変更を監視できます。 `LiveData`は、アクティビティとフラグメントのさまざまなライフサイクルを尊重します。 `LiveData`と`Room`を組み合わせると、標準のSQLiteDatabaseを使用するだけでは達成できない自動データベース更新を受け取ることができます。

## In Summary

`Room`はAndroid上でSQLiteの実装をラップする使いやすいライブラリです。 また、カーソルやコンテンツプロバイダの代わりにオブジェクトを扱うための直観的なインターフェイスも提供します。 `LiveData`で`Room`を使用することは、革命です。 これは、標準のSQLiteDatabaseで達成するのが難しい可能性があるデータ変更についてのビューを通知することを許可します。

アクティビティやフラグメントに直接データをロードすることにはいくつかの落とし穴があります。 主な問題は、あなたのアクティビティやフラグメントがデータベースに緊密に結合していることです。 これは、`テストを追加したり、別の場所でロジックを再利用したりする場合には適していません`。 データベースロジックをビューロジックから分離する方がはるかに良い方法です。 ViewModelアーキテクチャコンポーネントは、この問題を解決することを目的としています。 

# Part2

## ViewModel
Android Architectureのコンポーネントは、最近Google I / O 2017で発表されました。これらのライブラリには、いくつかのコンポーネントがあります。 これらのコンポーネントは単独で使用できますが、一緒に使用すると本当にうまく動作します。

前回の記事を思い出すことができれば、以下の図は「デートカウントダウン」アプリをどのように構築するかを示すものです。
![](https://i0.wp.com/riggaroo.co.za/wp-content/uploads/2017/05/MVVM-using-the-new-android-architecture-components-1.png?ssl=1)

この記事では、上の図に示すEventListViewModelとAddEventViewModelを作成します。 投稿のすべてのソースコードは[ここ](https://github.com/riggaroo/android-arch-components-date-countdown)で見ることができます。 ViewModelの作成に入る前に、まずViewModelが何であるかを見ておきましょう。

## ViewModelとは何か？

ViewModelは新しい概念ではなく、必然的にAndroidの概念でもありません。 ViewModelという名前は、2005年頃にMicrosoftによって設計されたMVVMパターンから来ました。新しいArchitecture Componentsでは、新しいクラスの1つにViewModelクラスがあります。

ViewModelsはViewの`データを準備`します。変更を監視しているすべてのビューにデータを公開します。 AndroidのViewModelクラスには、使用時に留意すべきいくつかの具体的な事実があります。

- ViewModelは、アクティビティ構成の変更全体でその状態を保持できます。それが保持するデータは、onSaveInstanceState（）にデータを保存して手動で復元することなく、すぐに次のActivityインスタンスで使用できます。
- ViewModelは、特定のActivityインスタンスまたはFragmentインスタンスよりも長生きします。
- ViewModelを使用すると、フラグメント間でデータを簡単に共有できます（つまり、アクティビティを介してアクションを調整する必要がなくなります）。
- ViewModelは、スコープのライフサイクルが永久に消えるまでメモリ内にとどまります。アクティビティの場合は、終了します。フラグメントの場合、一度それが切り離されます。
- ViewModelはActivityインスタンスまたはFragmentインスタンスよりも寿命が長いため、内部に直接ビューを参照したり、コンテキストを参照したりしてはいけません。これはメモリリークを引き起こす可能性があります。
- ViewModelが（例えば、システムサービスを見つけるために）アプリケーションコンテキストを必要とする場合、それはAndroidViewModelクラスを拡張し、コンストラクタ内でアプリケーションを受け取るコンストラクタを実装します。

## Date Countdown AppのViewModelの作成

### EventListViewModel

EventListViewModelクラスは、日付カウントダウンアプリが初めて開かれたときに表示されるイベントのリストに使用されます。

- EventListViewModelというクラスを作成します。 ViewModelがArchitectureコンポーネントクラスから継承されていることを確認してください。 このクラスでは、以前にEventListFragmentに配置されたコードをViewModelに移行します。
- LiveData変数をEventListViewModelに追加します。 EventRepository変数はDaggerを使用して注入されます。 EventListViewModelクラスは次のようになります。

```
public class EventListViewModel extends ViewModel implements CountdownComponent.Injectable {

    private LiveData<List<Event>> events = new MutableLiveData<>();
  
    @Inject
    EventRepository eventRepository;

    @Override
    public void inject(CountdownComponent countdownComponent) {
        countdownComponent.inject(this);
        events = eventRepository.getEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }
}
```
EventListFragmentでは、以前の投稿で書き込んだイベントロードコードを、ViewModelでイベントのリストを取得する代わりに置き換えます。
```
public class EventListFragment extends LifecycleFragment {
  
    private EventListViewModel eventListViewModel;
  
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //.. inflate view etc
        eventListViewModel = ViewModelProviders.of(this, new CountdownFactory(countdownApplication).get(EventListViewModel.class);
      
        eventListViewModel.getEvents().observe(this, events -> {
            adapter.setItems(events);
        });
        return v;
    }
}
```
ViewModelProvider.of（..）を含む行は、存在しない場合は新しいEventListViewModelクラスを作成するか、定義されているスコープ（この場合はEventListFragment）に存在するものをフェッチします。 これが魔法の起こる場所です。 デバイスが回転され、フラグメントが再作成されると、ここで返されるViewModelは以前使用されたViewModelになります。 これにより、手動で情報をBundleに保存して復元することなく、画面の状態を保持することができます。

- Dagger CountdownComponentを作成します。これを使用して、依存関係をViewModelに挿入します。
```
@Singleton
@Component(modules = {CountdownModule.class})
public interface CountdownComponent {

    void inject(EventListViewModel eventListViewModel);

    void inject(AddEventViewModel addEventViewModel);

    interface Injectable {
        void inject(CountdownComponent countdownComponent);
    }
}
```
- ViewModelをインスタンス化するために使用されるカスタムViewModelProvider.NewInstanceFactoryを作成します。 ViewModelProviderがViewModelの新しいインスタンスを作成する必要がある場合、ViewModelProviderは下のファクトリで定義されたcreateメソッドを使用します。
```
public class CountdownFactory extends ViewModelProvider.NewInstanceFactory {

    private CountdownApplication application;

    public CountdownFactory(CountdownApplication application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof CountdownComponent.Injectable) {
            ((CountdownComponent.Injectable) t).inject(application.getCountDownComponent());
        }
        return t;
    }
}
```
- イベントを削除するには、ViewModelにメソッドを追加します。 これはEventRepositoryに委譲してイベントを削除します。 この例では、RxJavaを使用してバックグラウンドスレッドに委譲しています。
```
public class EventListViewModel extends ViewModel implements CountdownComponent.Injectable {
    //.. 
    public void deleteEvent(Event event) {
        eventRepository.deleteEvent(event)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted event");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("OnError - deleted event: ", e);
                    }
                });
    }
}
```
ここでは、ViewModelに委譲したFragmentでこのdeleteメソッドを使用します。

AddEventViewModel実装については、ここで完全なソースコードの例を見てください。 Roomを使用してデータベースからイベントをロードするDateCountdownアプリと、新しいイベントを追加する機能が追加されました。 私たちはViewModelsを使って、データがローテーションで保持され、フラグメントからコードを分離するようにしました。

## In Summary

新しいAndroidアーキテクチャコンポーネントは、以前は簡単に処理できなかった一般的なシナリオに対応しています。 ViewModelとViewModelProviderを使用すると、画面の回転を処理するのが簡単になりました。

すべてのアクションと関連する単体テストを見るには、ここで入手可能な完全なコードサンプルをチェックアウトしてください。 

あなたの考えをtwitter @riggaroo に教えてください。
