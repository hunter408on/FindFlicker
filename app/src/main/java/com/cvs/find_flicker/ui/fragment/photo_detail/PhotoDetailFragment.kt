package com.cvs.find_flicker.ui.fragment.photo_detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.cvs.find_flicker.R
import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.KEY_PHOTO
import com.cvs.find_flicker.data.models.Photo
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.databinding.FragmentPhotoDetailBinding
import com.cvs.find_flicker.ui.fragment.BaseFragment
import com.cvs.find_flicker.viewmodel.PhotosViewModel
import com.cvs.find_flicker.utils.observe
import com.cvs.find_flicker.viewmodel.PhotoDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : BaseFragment() {

    private val viewModel: PhotoDetailViewModel by viewModels()
    private val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentPhotoDetailBinding
    private lateinit var photo: Photo

    companion object {
        fun newInstance() = PhotoDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater)
        photo = args.photo
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_PHOTO, photo)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelable<Photo>(KEY_PHOTO)?.let {
            photo = it
        }
    }

    override fun initViews() {
        binding.title.text = getString(R.string.title, photo.title)
        val htmlAsAstring = Html.fromHtml(photo.description).toString()
        binding.description.text = getString(R.string.description, htmlAsAstring)
        binding.width.text = getString(R.string.width, "")
        binding.height.text = getString(R.string.height, "")
        binding.author.text = getString(R.string.title, photo.author)

        Picasso.get()
            .load(photo.media.m)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imageView)
    }

    override fun observeViewModel() {

    }
}