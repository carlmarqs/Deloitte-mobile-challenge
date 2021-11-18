package com.deloitte.wtest.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.deloitte.wtest.R
import com.deloitte.wtest.database.DatabaseConnection
import com.deloitte.wtest.databinding.MainFragmentBinding
import com.deloitte.wtest.repository.PostalCodeRepository
import com.deloitte.wtest.utils.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)

        val application = this.requireActivity().application
        val dataSource = DatabaseConnection.getInstance(application).postalCodeDao
        val viewModelFactory = MainViewModelFactory(dataSource)
        val repository = PostalCodeRepository(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val sp = activity?.getPreferences(Context.MODE_PRIVATE)
            if (sp?.getBoolean("dataSaved", false) != true) {
                repository.addPostalCodes()
                sp?.edit()?.putBoolean("dataSaved", true)?.apply()
            }
            viewModel.filterPostalCodes()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecyclerAdapter()

        onClickCancelButton()

        viewModel.postalCodes.observe(viewLifecycleOwner, { postalCodesList ->
            binding.loadingSpinner.isVisible = true
            adapter.postalCodeList = postalCodesList
            binding.loadingSpinner.isVisible = false
        })

        binding.recyclerPostalCode.adapter = adapter
        binding.recyclerPostalCode.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun onClickCancelButton() {
        binding.buttonCancel.setOnClickListener {
            binding.editTextFilter.text.clear()
            viewModel.filterPostalCodes()
        }

        binding.editTextFilter.setOnKeyListener( { _, _, keyEvent ->
            val text = binding.editTextFilter.text.toString()
            if(keyEvent.action == KeyEvent.ACTION_UP && text.length > 2){
                binding.loadingSpinner.isVisible = true
                viewModel.filterPostalCodes(text)
                binding.loadingSpinner.isVisible = false
            }
            false
        })
    }
}