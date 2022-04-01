package com.cvs.find_flicker.ui.fragment.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.databinding.FragmentPhotosBinding
import com.cvs.find_flicker.ui.fragment.BaseFragment
import com.cvs.find_flicker.ui.fragment.photos.adapter.QueryAdapter
import com.cvs.find_flicker.utils.observe
import com.cvs.find_flicker.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : BaseFragment() {

    private val viewModel: PhotosViewModel by viewModels()
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var queryAdapter: QueryAdapter

    companion object {
        fun newInstance() = PhotosFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater)
        return binding.root
    }

    override fun initViews() {
        photosAdapter = PhotosAdapter()
        binding.photoList.adapter = photosAdapter
        queryAdapter = QueryAdapter {
            binding.searchView.setQuery(it, true)
        }
        binding.recentQueryList.adapter = queryAdapter
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.tryFetchWith(query ?: "")
                hideRecentQueryList()
                return false
            }
        })

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showRecentQueryList()
            } else {
                hideRecentQueryList()
            }
        }
    }

    override fun observeViewModel() {
        observe(viewModel.photosLiveData, this::handlePhotoList)
        observe(viewModel.queriesLiveData, this::handleQueryList)
    }

    private fun handlePhotoList(resource: Resource<PhotoList>) {
        when (resource.status) {
            Resource.Status.LOADING -> {
                showLoading()
            }
            Resource.Status.ERROR -> {
                hideLoading()
                showError(resource.message ?: "")
            }
            Resource.Status.SUCCESS -> {
                hideLoading()
                photosAdapter.submitList(resource.data?.items ?: emptyList())
            }
        }
    }

    private fun handleQueryList(queryList: List<String>) {
        queryAdapter.updateQueryList(queryList)
    }

    private fun showRecentQueryList() {
        binding.recentQueryList.visibility = View.VISIBLE
    }

    private fun hideRecentQueryList() {
        binding.recentQueryList.visibility = View.GONE
        binding.searchView.clearFocus()
    }

    override fun showLoading() {
        binding.loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingSpinner.visibility = View.GONE
    }
}