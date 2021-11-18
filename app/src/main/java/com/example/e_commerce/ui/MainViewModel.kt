package com.example.e_commerce.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.api.ApiService
import com.example.e_commerce.api.RetrofitInstance
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.profile.ProfileResponse
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import com.example.e_commerce.repository.MainRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(

) : ViewModel() {




    private val mainRepository = MainRepository(RetrofitInstance.retrofit.create(ApiService::class.java))


    val login_MLD: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val register_MLD: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val prfile_MLD: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()



    fun login(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.IO) {
        login_MLD.postValue(Resource.Loading())
        val response = mainRepository.login(loginRequest)
        login_MLD.postValue(handleLoginState(response))
    }

    private fun handleLoginState(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun register(registerRequest: RegisterRequest) = viewModelScope.launch(Dispatchers.IO) {
        register_MLD.postValue(Resource.Loading())
        val response = mainRepository.register(registerRequest)
        register_MLD.postValue(handleRegisterState(response))
    }

    private fun handleRegisterState(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getProfileData(token: String) = viewModelScope.launch(Dispatchers.IO) {
        prfile_MLD.postValue(Resource.Loading())
        val response = mainRepository.getProfileData(token)
        prfile_MLD.postValue(handleGetProfileData(response))
    }

    private fun handleGetProfileData(response: Response<ProfileResponse>): Resource<ProfileResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handleGetProfileData", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}