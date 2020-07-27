package com.cuncis.bukatoko.ui.transaction.upload

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.databinding.FragmentTransactionUploadBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.FileUtils
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import java.io.File


class TransactionUploadFragment : Fragment() {

    private lateinit var binding: FragmentTransactionUploadBinding
    private val transactionUploadViewModel by inject<TransactionUploadViewModel>()

    private val args by navArgs<TransactionUploadFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_upload,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = transactionUploadViewModel

        initListener()

        observeViewModel()
    }

    private fun initListener() {
        binding.tvGallery.setOnClickListener {
            permissionGallery()
        }
    }

    private fun observeViewModel() {
        transactionUploadViewModel.uploadImage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.linearUpload.showView()
                    binding.progressbar.hideView()
                    it.data?.status.let { status ->
                        Toast.makeText(requireContext(), status?.description, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_transactionUploadFragment_to_nav_transaction)
                    }
                }
                Status.ERROR -> {
                    binding.linearUpload.showView()
                    binding.progressbar.hideView()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.linearUpload.hideView()
                    binding.progressbar.showView()
                }
            }
        })
    }

    private fun uploadImage(file: File) {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val image = MultipartBody.Part.createFormData("foto", file.name, requestFile)
        binding.code = args.transactionCode.trim()
        binding.imageFile = image
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1)
    }

    private fun permissionGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            2)
        } else {
            openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            binding.tvGallery.text = "Change Image"
            binding.btnUpload.showView()
            binding.imgUpload.setImageURI(data?.data)
            val file = FileUtils.getFile(requireContext(), data?.data)
            if (file != null) {
                uploadImage(file)
            }
        }
    }

}











