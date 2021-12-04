package com.example.asuserdetailsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.asuserdetailsapp.R
import com.example.asuserdetailsapp.database.User
import com.example.asuserdetailsapp.databinding.FragmentUserDetailsBinding
import com.example.asuserdetailsapp.di.AppModule
import com.example.asuserdetailsapp.utils.ProgressDialog
import com.example.asuserdetailsapp.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailViewFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val viewModel: UserListViewModel by activityViewModels()
    private val binding get() = _binding!!

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
        loadData()
    }

    private fun loadData() {
        val position = DetailViewFragmentArgs.fromBundle(requireArguments()).position
            viewModel.userDetailsFromDb.value?.let { list ->
                populateData(list, position)
            }
    }

    private fun populateData(
        list: ArrayList<User>,
        position: Int
    ) {
        val name = list[position].firstName + " " + list[position].lastName
        binding.firstName.text = name
        binding.ageVal.text = list[position].bio
        binding.genderVal.text = list[position].instaTubeUrl
        binding.cityVal.text = list[position].youTubeUrl
        binding.phoneVal.text = list[position].mobile
        binding.emailVal.text = list[position].lastName
        context?.let {
            Glide.with(it)
                .load(list[position].image)
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(binding.profileImg)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}