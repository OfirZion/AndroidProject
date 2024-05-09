package com.example.androidproject.ui.main.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidproject.MainActivity
import com.example.androidproject.adapters.NpmRegistryPostsAdapter
import com.example.androidproject.databinding.FragmentNpmRegistryBinding
import com.example.androidproject.helpers.LoadingState
import com.example.androidproject.helpers.extensions.observeNotNull
import com.example.androidproject.models.npm.NpmRegistryRoot
import com.example.androidproject.viewmodels.NpmRegistryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NpmRegistryFragment : Fragment() {

    private val viewModel by viewModels<NpmRegistryViewModel>()

    private var _binding: FragmentNpmRegistryBinding? = null
    private val binding: FragmentNpmRegistryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNpmRegistryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchView.setOnSearchClickListener {
            binding.tvSearchIndicator.visibility = View.GONE
        }
        binding.searchView.setOnCloseListener {
            binding.tvSearchIndicator.visibility = View.VISIBLE
            false
        }

        binding.tvSearchIndicator.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { query ->
                    Log.d("onQueryTextChange", query)
                    viewModel.search(query)
                }
                return true
            }
        })

        val adapter = NpmRegistryPostsAdapter(
            root = NpmRegistryRoot(),
            openLink = ::startActivity
        )
        binding.rvResults.adapter = adapter
        viewModel.loadingState.observe(viewLifecycleOwner) {
            (requireActivity() as? MainActivity)?.handleLoader(it)
        }
        viewModel.registryLiveData.observeNotNull(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.exceptions.observeNotNull(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it.message ?: "Faulted while querying npm registry",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}