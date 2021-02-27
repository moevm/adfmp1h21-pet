package abdulmanov.eduard.pets.presentation._common.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun Completable.safeSubscribe(onSuccess: () -> Unit) {
        val disposable = subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess)
        compositeDisposable.add(disposable)
    }

    protected fun <T> Single<T>.safeSubscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        val disposable = subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    protected fun Completable.addDispatchers(): Completable {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}