package com.myproject.app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.databinding.FragmentFavoriteBinding
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCase
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCaseImpl
import com.myproject.app.presentation.adapter.MyAdapterRecyclerFavorite
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataJsonRepository: DataJsonRepository
    private lateinit var selectAllCurrencyUseCase: SelectAllCurrencyUseCase
    private lateinit var myAdapterRecycler: MyAdapterRecyclerFavorite

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

        myAdapterRecycler = MyAdapterRecyclerFavorite(requireContext())

        dataJsonRepository = DataJsonRepository(requireContext())
        selectAllCurrencyUseCase = SelectAllCurrencyUseCaseImpl(dataJsonRepository)

        binding.listFavorite.adapter = myAdapterRecycler
        binding.listFavorite.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {

            val listData = selectAllCurrencyUseCase.selectAll()
            myAdapterRecycler.addList(listData)

        }

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