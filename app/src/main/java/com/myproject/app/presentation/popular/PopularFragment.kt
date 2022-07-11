package com.myproject.app.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.databinding.FragmentPopularBinding
import com.myproject.app.presentation.adapter.MyAdapterRecyclerPopular
import com.myproject.app.presentation.adapter.MyViewModelFactory
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PopularFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    // Лучше не использовать lateinit. Есть альтернатива - by lazy { }
    // Первые две переменные используются только внутри onViewCreated(), поэтому их можно инициализировать прямо там

    private lateinit var dataJsonRepository: DataJsonRepository
    private lateinit var myAdapterRecycler: MyAdapterRecyclerPopular
    private lateinit var viewModelPopular: ViewModelPopular

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

        binding.listPopular.adapter = myAdapterRecycler
        binding.listPopular.layoutManager = LinearLayoutManager(context)

        viewModelPopular =
            ViewModelProvider(
                requireActivity(),
                MyViewModelFactory(dataJsonRepository)
            )[ViewModelPopular::class.java]

        viewModelPopular.getCurrencyLiveData.observe(viewLifecycleOwner) {
            myAdapterRecycler.addList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelPopular.getCurrencyData()
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