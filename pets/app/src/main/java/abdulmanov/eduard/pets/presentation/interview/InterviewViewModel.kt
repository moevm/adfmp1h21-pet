package abdulmanov.eduard.pets.presentation.interview

import abdulmanov.eduard.pets.domain.interactors.InterviewsInteractor
import abdulmanov.eduard.pets.domain.models.interview.Interview
import abdulmanov.eduard.pets.presentation._common.viewmodel.BaseViewModel
import abdulmanov.eduard.pets.presentation.event.model.EventPresentationModel
import abdulmanov.eduard.pets.presentation.interview.model.InterviewPresentationModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate
import javax.inject.Inject

class InterviewViewModel @Inject constructor(
    private val router: Router,
    private val interviewsInteractor: InterviewsInteractor
) : BaseViewModel() {

    private val _currentInterview = MutableLiveData<InterviewPresentationModel>()
    val currentInterview: LiveData<InterviewPresentationModel>
        get() = _currentInterview

    fun onBackCommandClick() = router.exit()

    fun getInterviewForDate(date: LocalDate){
        Log.d("FuckFuck","$date")
        interviewsInteractor.getInterviewByDate(date)
            .map(InterviewPresentationModel::fromDomain)
            .safeSubscribe(
                {
                    _currentInterview.value = it
                },
                {
                    _currentInterview.value = InterviewPresentationModel(date = date.toString())
                }
            )
    }

    fun createOrUpdateInterview(rating: Int){
        getSingeCreateOrUpdate(rating)
            .map(InterviewPresentationModel::fromDomain)
            .safeSubscribe { _currentInterview.value = it }
    }

    private fun getSingeCreateOrUpdate(rating: Int): Single<Interview> {
        val domainModel = InterviewPresentationModel.toDomain(_currentInterview.value!!)
            .copy(rating = rating)

        return if (_currentInterview.value!!.isNew()) {
            interviewsInteractor.createInterview(domainModel)
        } else {
            interviewsInteractor.updateInterview(domainModel)
        }
    }
}