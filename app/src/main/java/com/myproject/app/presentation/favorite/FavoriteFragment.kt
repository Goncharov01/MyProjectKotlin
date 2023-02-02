package com.myproject.app.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.databinding.FragmentFavoriteBinding
import com.myproject.app.presentation.adapter.MyAdapterRecyclerFavorite
import com.myproject.app.presentation.adapter.MyViewModelFactory
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val dataJsonRepository: DataJsonRepository by lazy { DataJsonRepository(requireContext()) }
    private val myAdapterRecycler: MyAdapterRecyclerFavorite by lazy {
        MyAdapterRecyclerFavorite { DataCurrency ->
            clickListenerForAdapter(
                DataCurrency
            )
        }
    }
    private val viewModelFavorite: ViewModelFavorite by lazy {
        ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(dataJsonRepository)
        )[ViewModelFavorite::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFavorite.adapter = myAdapterRecycler
        binding.listFavorite.layoutManager = LinearLayoutManager(requireContext())

        viewModelFavorite.selectAllLiveData.observe(viewLifecycleOwner) {
            myAdapterRecycler.addList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelFavorite.selectAllCurrencyDatabase()
        }

    }

    private fun clickListenerForAdapter(dataCurrency: DataCurrency) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelFavorite.deleteFavoriteCurrency(dataCurrency.id)
        }

    }
}