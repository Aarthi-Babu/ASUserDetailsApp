package com.example.asuserdetailsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.asuserdetailsapp.database.DateBaseHelperImpl
import com.example.asuserdetailsapp.database.UserDataBase
import com.example.asuserdetailsapp.databinding.FragmentListBinding
import com.example.asuserdetailsapp.di.AppModule
import com.example.asuserdetailsapp.utils.ProgressDialog
import com.example.asuserdetailsapp.utils.Resource
import com.example.asuserdetailsapp.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListViewFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val viewModel: UserListViewModel by activityViewModels()
    private var adapter: ListViewAdapter? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialogFactory: AppModule.ProgressDialogFactory

    private val progressDialog: ProgressDialog by lazy { progressDialogFactory.create(this.context) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onStart()
        viewModel.authToken?.let {
            invokeDB("Bearer $it")

        }
        recyclerView()
    }

    private fun invokeDB(token: String) {
        val dbHelper =
            context?.let { DateBaseHelperImpl(UserDataBase.DatabaseBuilder.getInstance(it)) }
        dbHelper?.let {
            viewModel.getUserDetails(token, it).observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        progressDialog.hideLoading()
                        Toast.makeText(context, "user api ${resource.data}", Toast.LENGTH_SHORT)
                            .show()
                        loadData()
                    }
                    Resource.Status.ERROR -> {
                        progressDialog.hideLoading()
                        Toast.makeText(context, "user api error.", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING -> {
                        progressDialog.showLoading()
                    }
                }
            }
        }
    }

    private fun loadData() {
        viewModel.userDetailsFromDb.value?.let { list ->
            adapter?.addData(list)
        }
    }

    private fun recyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        val sGridLayoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        binding.recyclerView.layoutManager = sGridLayoutManager

        adapter = ListViewAdapter {
            view?.findNavController()
                ?.navigate(UserListViewFragmentDirections.actionUserListViewFragmentToDetailViewFragment(it))
        }

        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            0F
        ).apply {
            duration = 1000
            fillAfter = true
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.startAnimation(animate)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}