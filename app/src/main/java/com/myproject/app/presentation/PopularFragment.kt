package com.myproject.app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.databinding.FragmentPopularBinding
import com.myproject.app.presentation.adapter.MyAdapterRecyclerPopular
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCase
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCaseImpl
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PopularFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val listCurrency = mutableListOf<DataCurrency>()

    private lateinit var dataJsonRepository: DataJsonRepository
    private lateinit var getCurrencyUseCase: GetCurrencyUseCase
    private lateinit var myAdapterRecycler: MyAdapterRecyclerPopular

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        myAdapterRecycler = MyAdapterRecyclerPopular(requireContext())

        dataJsonRepository = DataJsonRepository(requireContext())
        getCurrencyUseCase = GetCurrencyUseCaseImpl(dataJsonRepository)

        binding.listPopular.adapter = myAdapterRecycler
        binding.listPopular.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {

            val currencyRates = getCurrencyUseCase.getCurrency().rates

            for (entry in currencyRates) {
                listCurrency.add(DataCurrency(currency = entry.key, value = entry.value.toString()))
            }

            myAdapterRecycler.addList(listCurrency)

        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PopularFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}