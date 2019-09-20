package co.location.locationapp.ui.home.pagingsource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.location.locationapp.BuildConfig
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.LocationRepository
import co.location.locationapp.ui.home.enums.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LocationPageKeySource(private val locationRepository: LocationRepository) :
        PageKeyedDataSource<Int, DeliveryLocation>() {

    val state: MutableLiveData<State> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    companion object {
        var pageNumber: Int = 1
    }

    val compositeDisposable = CompositeDisposable()
    private var retryCompletable: Completable? = null

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DeliveryLocation>) {
        updateState(State.LOADING)
        updateError(null)
        compositeDisposable.add(locationRepository.getLocationData(false, BuildConfig.PAGE_SIZE, params.key)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { if (it.isNullOrEmpty()) updateState(State.NO_DATA) }
                .delay(100, TimeUnit.MILLISECONDS)
                .doOnNext {
                    updateState(State.DONE)
                    updateError(null)
                }
                .doOnError {
                    updateError(it)
                    updateState(State.DONE)
                }
                .subscribe(
                        { callback.onResult(it, it.size + params.key) },
                        { setRetry(Action { loadAfter(params, callback) }) }
                ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DeliveryLocation>) {}


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DeliveryLocation>) {
        updateState(State.LOADING)
        updateError(null)
        compositeDisposable.add(locationRepository.getLocationData(pageNumber > 1, BuildConfig.PAGE_SIZE, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    updateState(State.DONE)
                    updateError(null)
                    pageNumber++
                }
                .doOnError {
                    updateError(it)
                    updateState(State.DONE)
                }
                .subscribe(
                        { callback.onResult(it, 0, it.size) },
                        {
                            if (pageNumber != 1) {
                                locationRepository.getLocationDataFromDb(BuildConfig.PAGE_SIZE, 0)
                                        .subscribe {
                                            updateState(State.DONE)
                                            updateError(null)
                                            callback.onResult(it, 0, it.size)
                                        }
                            } else {
                                setRetry(Action { loadInitial(params, callback) })
                            }
                        }
                ))
    }


    fun retry() {
        val retry = retryCompletable
        retry?.let {
            compositeDisposable.add(retry
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }


    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun updateError(error: Throwable?) {
        this.error.postValue(error)
    }

}