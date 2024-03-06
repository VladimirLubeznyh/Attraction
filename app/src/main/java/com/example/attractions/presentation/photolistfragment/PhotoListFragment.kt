package com.example.attractions.presentation.photolistfragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.attractions.R
import com.example.attractions.databinding.PhotoListFragmentBinding
import com.example.attractions.appComponent
import com.example.attractions.presentation.photolistfragment.photolist.PhotoListAdapter
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoListFragment : Fragment() {
    @Inject
    lateinit var viewModalFactory: PhotoListViewModalFactory
    private var _binding: PhotoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PhotoListViewModel> { viewModalFactory }
    private val adapter = PhotoListAdapter{ onClickFooter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.injectInPhotoListFragment(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("Nat Token", it.result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhotoListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoList.adapter = adapter
        setSpanCount()
        setData()
    }

    private fun setData() = lifecycleScope.launch {
        binding.run {
            viewModel.listPhotoEntity.collect { photoList ->
                adapter.submitList(photoList.reversed())
            }
        }
    }

    private fun setSpanCount() = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> binding.photoList.layoutManager =
            GridLayoutManager(requireContext(), 3)
        else -> binding.photoList.layoutManager = GridLayoutManager(requireContext(), 6)
    }


    private fun onClickFooter() {
        findNavController().navigate(R.id.action_navigation_photo_list_to_navigation_create_photo)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
