package com.example.e_commerce.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.util.Resource
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream
import java.io.InputStream

class RegisterFragment : Fragment() {

    private val TAG: String = "hii"

    private val GALLERY_REQUEST_CODE = 1024

    private lateinit var userImage: String
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    lateinit var viewModel: AuthViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel.register_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.e("test", response.massage.toString())
                    Log.e("test", "Success${response.data!!.message.toString()}")
                    Log.e("test", response.data!!.status.toString())
                    Toast.makeText(
                            requireActivity(),
                            "Resource.Success : ${response.massage.toString()}",
                            Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Error -> {
                    response.massage?.let {
                        Log.e("test", "Resource.Error : ${response.massage.toString()}")
                        Log.e("test", "Resource.Error : ${response.data!!.message.toString()}")
                        Log.e("test", "Resource.Error : ${response.data!!.status.toString()}")
                        Toast.makeText(activity, "Resource.Error : ${it}", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(activity, response.massage.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(activity, response.data?.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.ivUserImage.setOnClickListener {
            pickFromGallery()
            Log.e(TAG, " pickFromGallery")
        }

        binding.btnRegister.setOnClickListener() {
            viewModel.register(RegisterRequest(
                    binding.tvName.text.toString(),
                    binding.tvPhone.text.toString(),
                    binding.tvEmail.text.toString(),
                    binding.tvPassword.text.toString(),
                    userImage
            ))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                        binding.ivUserImage.setImageURI(uri)
                        userImage = encoder2(uri)
                        Log.e(TAG, "GALLERY_REQUEST_CODE")
                        Log.e(TAG, encoder2(uri))
                    }
                } else {
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory.")
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                Log.e(TAG, "CROP_IMAGE_ACTIVITY_REQUEST_CODE")
                if (resultCode == Activity.RESULT_OK) {
                    Log.e(TAG, "Crop done:}")
                    Toast.makeText(requireActivity(), "done", Toast.LENGTH_LONG).show()
                    setImage(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.error}")
                    Toast.makeText(requireContext(), "${result.error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    // Saved Broken Image
    private fun encoder2(imageUri: Uri): String {
        val input = requireActivity().contentResolver.openInputStream(imageUri)
        //val bm = BitmapFactory.decodeResource(resources, R.drawable.test)
        val image = BitmapFactory.decodeStream(input, null, null)
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        //bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        image!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()

        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.NO_WRAP)
        //return Base64.getEncoder().encodeToString(imageBytes) // Not Worked, too.
    }

    private fun setImage(uri: Uri) {
        Glide.with(requireActivity())
                .load(uri)
                .into(binding.ivUserImage)
//        Toast.makeText(requireContext(), "setImage", Toast.LENGTH_LONG).show()
//        Log.e(TAG, "setImage")
//        binding.imageCropper.setImageURI(uri)
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireActivity())
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

}


//private lateinit var binding: FragmentRegisterBinding
//private lateinit var navController: NavController
//
//private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
//    override fun createIntent(context: Context, input: Any?): Intent {
//        return CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
//                .getIntent(requireActivity())
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//        return CropImage.getActivityResult(intent)?.uri
//    }
//}
//
//private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
//override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//): View? {
//    binding = FragmentRegisterBinding.inflate(inflater)
//    return binding.root
//}
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    navController = Navigation.findNavController(view)
//
//    cropActivityResultLauncher = requireActivity().registerForActivityResult(cropActivityResultContract){
//        it?.let { uri ->
////                Glide.with(requireActivity()).load(uri).into(binding.imageCropper)
//            binding.imageCropper.setImageURI(uri)
//        }
//    }
//
//    binding.btnPickImage.setOnClickListener(){
//        cropActivityResultLauncher.launch(null)
//    }
//
//}
//
//}