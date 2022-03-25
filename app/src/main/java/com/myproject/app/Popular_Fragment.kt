package com.myproject.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.CurrencyApi
import com.myproject.app.databinding.FragmentPopularBinding
import com.myproject.app.recycler.DataCurrency
import com.myproject.app.recycler.MyAdapterRecycler
import com.myproject.app.room.DataBaseBuilder
import com.myproject.app.room.DataBaseDataCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Popular_Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var job: Job = Job()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val listCurrency = mutableListOf<DataCurrency>()

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

        val myAdapterRecycler: MyAdapterRecycler = MyAdapterRecycler()
        binding.listPopular.adapter = myAdapterRecycler
        binding.listPopular.layoutManager = LinearLayoutManager(context)

        //Bug
//        val dataBaseDataCurrency: DataBaseDataCurrency =
//            DataBaseBuilder.getInstans(requireContext())

//        GlobalScope.launch {
//            dataBaseDataCurrency.dataCurrencyDao()
//                .insertCurrency(DataCurrency(currency = "qwerty", value = "qwerty"))
//        }

        job = GlobalScope.launch(Dispatchers.Main) {

            val currencyRates = CurrencyApi.getApi().getCurrency().rates

            for (entry in currencyRates) {
                listCurrency.add(DataCurrency(currency = entry.key, value = entry.value.toString()))
            }

            myAdapterRecycler.addList(listCurrency)

        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Popular_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}