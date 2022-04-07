package com.myproject.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.databinding.FragmentFavoriteBinding
import com.myproject.app.recycler.MyAdapterRecyclerFavorite
import com.myproject.app.room.DataBaseBuilder
import com.myproject.app.room.DataBaseDataCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var jobFavorite: Job

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
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataBaseDataCurrency: DataBaseDataCurrency =
            DataBaseBuilder.getInstans(requireContext())

        val myAdapterRecycler: MyAdapterRecyclerFavorite =
            MyAdapterRecyclerFavorite(dataBaseDataCurrency.dataCurrencyDao())

        binding.listFavorite.adapter = myAdapterRecycler
        binding.listFavorite.layoutManager = LinearLayoutManager(context)

        jobFavorite = GlobalScope.launch(Dispatchers.Main) {

            val listData =
                DataBaseBuilder.getInstans(requireContext()).dataCurrencyDao().selectAll()
            myAdapterRecycler.addList(listData)

            myAdapterRecycler.addList(listData)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        jobFavorite.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}