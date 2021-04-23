package com.example.e_commerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
//        val binding: ActivityMainBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding.lifecycleOwner = this
        navController = findNavController(R.id.NavHostFragment)


    }
}

//try {
//            GlobalScope.launch {
//                var response = RetrofitInstance.api.login(LoginRequest("mm@gmail.com", "123456"))
//                response.let {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@MainActivity, it.body()!!.message, Toast.LENGTH_LONG).show()
//                    }
//
//                }
//            }
//
//            //                if (response.isSuccessful) {
////                    withContext(Dispatchers.Main){
////                        Toast.makeText(this@MainActivity , response.body()!!.message , Toast.LENGTH_LONG).show()
////                    }
////                    Log.e("Test1", response.message())
////                    Log.e("Test1", response.body()!!.message)
////                } else {
////                    withContext(Dispatchers.Main){
////                        Toast.makeText(this@MainActivity , response.body()!!.message , Toast.LENGTH_LONG).show()
////                        Toast.makeText(this@MainActivity , response.body()!!.status.toString() , Toast.LENGTH_LONG).show()
////                    }
////                    Log.e("Test1", response.message())
////                    Log.e("Test1", response.body()!!.message)
////                    Log.e("Test1", response.body()!!.status.toString())
////                }
//////            ?.let {
//////                Log.e("Test1" , respnse.message())
//////            }
////            }
//
//
//        }
//        catch (e: Exception) {
//            Log.e("Test1", e.message.toString())
//        }