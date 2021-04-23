package com.example.e_commerce.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.profile.Profile
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import com.example.e_commerce.repository.AuthRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(

) : ViewModel() {

    val authRepository = AuthRepository()

    val login_MLD: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val register_MLD: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val prfile_MLD: MutableLiveData<Resource<Profile>> = MutableLiveData()
    

    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        login_MLD.postValue(Resource.Loading())
        val response = authRepository.login(loginRequest)
        login_MLD.postValue(handelLoginState(response))
    }

    private fun handelLoginState(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        register_MLD.postValue(Resource.Loading())
        val response = authRepository.register(registerRequest)
        register_MLD.postValue(handelRegisterState(response))
    }

    private fun handelRegisterState(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getProfileData(token : String) = viewModelScope.launch {
        prfile_MLD.postValue(Resource.Loading())
        val response = authRepository.getProfileData(token)
        prfile_MLD.postValue(handelGetProfileData(response))
    }

    private fun handelGetProfileData(response: Response<Profile>): Resource<Profile> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handelGetProfileData", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}