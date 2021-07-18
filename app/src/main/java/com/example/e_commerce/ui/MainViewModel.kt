package com.example.e_commerce.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.profile.ProfileResponse
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import com.example.e_commerce.models.banners.BannersResponse
import com.example.e_commerce.models.categories.CategoriesDetails
import com.example.e_commerce.models.categories.CategoriesResponse
import com.example.e_commerce.models.home.homeData.HomeDataResponse
import com.example.e_commerce.repository.MainRepository
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(

) : ViewModel() {

    val authRepository = MainRepository()

    val login_MLD: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val register_MLD: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val prfile_MLD: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val homeData_MLD: MutableLiveData<Resource<HomeDataResponse>> = MutableLiveData()
    val banners_MLD: MutableLiveData<Resource<BannersResponse>> = MutableLiveData()
    val category_MLD: MutableLiveData<Resource<CategoriesResponse>> = MutableLiveData()
    val categoryDetails_MLD: MutableLiveData<Resource<CategoriesDetails>> = MutableLiveData()


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

    fun getProfileData(token: String) = viewModelScope.launch {
        prfile_MLD.postValue(Resource.Loading())
        val response = authRepository.getProfileData(token)
        prfile_MLD.postValue(handelGetProfileData(response))
    }

    fun getBanners() = viewModelScope.launch {
        banners_MLD.postValue(Resource.Loading())
        val response = authRepository.getBanners()
        banners_MLD.postValue(handelBanners(response))
    }

    private fun handelBanners(response: Response<BannersResponse>): Resource<BannersResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
//                Log.i("handelBanners", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun getCategories() = viewModelScope.launch {
        category_MLD.postValue(Resource.Loading())
        val response = authRepository.getCategories()
        category_MLD.postValue(handelCategoriesState(response))
    }

    private fun handelCategoriesState(response: Response<CategoriesResponse>): Resource<CategoriesResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handelCategoriesState", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getCategoryDetails(category_id: Int) = viewModelScope.launch {
        categoryDetails_MLD.postValue(Resource.Loading())
        val response = authRepository.getCategoryDetails(category_id)
        categoryDetails_MLD.postValue(handleCategoryDetailsState(response))
    }

    private fun handleCategoryDetailsState(response: Response<CategoriesDetails>): Resource<CategoriesDetails> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handleCategoryDetails", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



    private fun handelGetProfileData(response: Response<ProfileResponse>): Resource<ProfileResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handelGetProfileData", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getHomeData(token: String) = viewModelScope.launch {
        homeData_MLD.postValue(Resource.Loading())
        val response = authRepository.getHomeData(token)
        homeData_MLD.postValue(handelHomeData(response))
    }

    private fun handelHomeData(response: Response<HomeDataResponse>): Resource<HomeDataResponse> {
        if (response.isSuccessful) {
            response.body().let { resultResponse ->
                Log.e("handelHomeData", resultResponse.toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}