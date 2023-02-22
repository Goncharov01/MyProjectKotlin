package com.myproject.app.presentation.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.databinding.FragmentPopularBinding
import com.myproject.app.presentation.adapter.MyAdapterRecyclerPopular
import com.myproject.app.presentation.adapter.MyViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val dataJsonRepository: DataJsonRepository by lazy { DataJsonRepository(requireContext()) }
    private val myAdapterRecycler: MyAdapterRecyclerPopular by lazy {
        MyAdapterRecyclerPopular { clickListener -> clickListenerForAdapter(clickListener) }
    }
    private val viewModelPopular: ViewModelPopular by lazy {
        ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(dataJsonRepository)
        )[ViewModelPopular::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listPopular.adapter = myAdapterRecycler
        binding.listPopular.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launchWhenCreated {
            viewModelPopular.currencyGetList.collect {
                myAdapterRecycler.submitData(it)
            }
        }
    }

    private fun clickListenerForAdapter(dataCurrency: DataCurrency) {

        viewLifecycleOwner.lifecycleScope.launch {

            if (dataCurrency.favorite) {

                Log.i("Database info", "Currency deleted in database")
                dataCurrency.favorite = false
                viewModelPopular.deleteCurrencyInDataBase(dataCurrency.currency)

            } else {

                Log.i("Database info", "Currency insert in database")
                dataCurrency.favorite = true
                viewModelPopular.insertCurrencyInDataBase(dataCurrency)

            }

        }

    }

}