package com.example.asuserdetailsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.asuserdetailsapp.R
import com.example.asuserdetailsapp.database.DateBaseHelperImpl
import com.example.asuserdetailsapp.database.User
import com.example.asuserdetailsapp.database.UserDataBase
import com.example.asuserdetailsapp.databinding.FragmentUserDetailsBinding
import com.example.asuserdetailsapp.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailViewFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val viewModel: UserListViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var user: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onStart()
        populateData()
        binding.save.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser() {
        user?.firstName = binding.firstNameVal.text.toString()
        user?.lastName = binding.lastNameVal.text.toString()
        user?.bio = binding.bioVal.text.toString()
        user?.youTubeUrl = binding.youTubeUrlVal.text.toString()
        user?.instaTubeUrl = binding.instaUrlVal.text.toString()
        user?.mobile = binding.mobileVal.text.toString()
        val dbHelper =
            context?.let { DateBaseHelperImpl(UserDataBase.DatabaseBuilder.getInstance(it)) }
        dbHelper?.let { _dbHelper ->
            user?.let { _user ->
                viewModel.updateDb(
                    _dbHelper,
                    _user
                )
            }
        }
        val controller = view?.let { Navigation.findNavController(it) }
        controller?.popBackStack()
    }

    private fun populateData() {
        val position = DetailViewFragmentArgs.fromBundle(requireArguments()).position
        viewModel.userDetailsFromDb.value?.let { list ->
            user = list[position]
            binding.firstNameVal.setText(list[position].firstName)
            binding.lastNameVal.setText(list[position].lastName)
            binding.instaUrlVal.setText(list[position].instaTubeUrl)
            binding.youTubeUrlVal.setText(list[position].youTubeUrl)
            binding.mobileVal.setText(list[position].mobile)
            binding.bioVal.setText(list[position].lastName)
            context?.let {
                Glide.with(it)
                    .load(list[position].image)
                    .placeholder(R.drawable.ic_default_dp)
                    .centerCrop()
                    .into(binding.profileImg)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}