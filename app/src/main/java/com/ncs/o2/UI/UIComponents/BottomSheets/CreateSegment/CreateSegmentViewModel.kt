package com.ncs.o2.UI.UIComponents.BottomSheets.CreateSegment

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ncs.o2.Domain.Models.Segment
import com.ncs.o2.Domain.Models.ServerResult
import com.ncs.o2.Domain.UseCases.CreateSegmentUseCase
import com.ncs.o2.Domain.Utility.Codes
import com.ncs.o2.HelperClasses.ServerExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
File : CreateSegmentViewModel.kt -> com.ncs.o2.UI.UIComponents.BottomSheets.CreateSegment
Description : ViewModel for Segment creation 

Author : Alok Ranjan (VC uname : apple)
Link : https://github.com/arpitmx
From : Bitpolarity x Noshbae (@Project : O2 Android)

Creation : 10:35 am on 11/06/23

Todo >
Tasks CLEAN CODE : 
Tasks BUG FIXES : 
Tasks FEATURE MUST HAVE : 
Tasks FUTURE ADDITION :

*/


@HiltViewModel
class CreateSegmentViewModel @Inject constructor(
    val usecase : CreateSegmentUseCase
    ):
    ViewModel(){


    private val _segmentValidityLiveData = MutableLiveData<String>()
    val segmentValidityLiveData : LiveData<String> get() = _segmentValidityLiveData

    private val _showprogressLD = MutableLiveData<Boolean>()
    val showprogressLD: LiveData<Boolean> get() = _showprogressLD

    fun createSegment(segment: Segment){
            usecase.doCheckAndCreateSegment(segment) { callback ->
                    when (callback){
                        is ServerResult.Failure -> {

                            Handler(Looper.getMainLooper()).postDelayed({
                                _showprogressLD.postValue(false)
                            },400)

                            when (callback.exception) {
                                ServerExceptions.projectDoesNotExists -> {
                                    _segmentValidityLiveData.postValue(ServerExceptions.projectDoesNotExists.exceptionDescription)
                                }
                                ServerExceptions.duplicateNameException -> {
                                    _segmentValidityLiveData.postValue(ServerExceptions.duplicateNameException.exceptionDescription)
                                }
                                else -> {
                                    _segmentValidityLiveData.postValue(callback.exception.message.toString())
                                }
                            }
                        }

                        is ServerResult.Progress -> {
                            _showprogressLD.postValue(true)
                        }

                        is ServerResult.Success -> {

                            Handler(Looper.getMainLooper()).postDelayed({
                                _showprogressLD.postValue(false)
                            },400)

                            //Valid
                            if (callback.data == 200){
                               _showprogressLD.postValue(false)
                                _segmentValidityLiveData.postValue(Codes.Status.VALID_INPUT)
                            }

                        }
                    }
            }
    }

}