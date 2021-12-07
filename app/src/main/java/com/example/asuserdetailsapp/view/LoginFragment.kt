package com.example.asuserdetailsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.asuserdetailsapp.databinding.FragmentLoginBinding
import com.example.asuserdetailsapp.di.AppModule
import com.example.asuserdetailsapp.utils.ProgressDialog
import com.example.asuserdetailsapp.utils.Resource
import com.example.asuserdetailsapp.utils.isNetworkConnected
import com.example.asuserdetailsapp.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val viewModel: UserListViewModel by activityViewModels()
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialogFactory: AppModule.ProgressDialogFactory

    private val progressDialog: ProgressDialog by lazy { progressDialogFactory.create(this.context) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onStart()
        binding.submitButton.setOnClickListener {
            if (validate(binding.emailId.text.toString(), binding.password.text.toString()))
                login(
                    binding.emailId.text.toString(),
                    binding.password.text.toString()
                )
            else
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_LONG).show()
        }
    }

    private fun login(email: String, password: String) {
        if (context?.isNetworkConnected() == true)
            viewModel.getLoginDetails(email, password).observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        progressDialog.hideLoading()
                        viewModel.authToken = resource.data?.token
                        view?.findNavController()
                            ?.navigate(LoginFragmentDirections.actionLoginFragmentToUserListViewFragment())
                    }
                    Resource.Status.ERROR -> {
                        progressDialog.hideLoading()
                        Toast.makeText(
                            context,
                            "login api error. ${resource.throwable}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Resource.Status.LOADING -> {
                        progressDialog.showLoading()
                    }
                }
            }
    }

    fun validate(userName: String, password: String): Boolean {
        return userName == "demo@gmail.com" && password == "123456"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}