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
import com.myproject.app.databinding.FragmentFavoriteBinding
import com.myproject.app.presentation.adapter.MyAdapterRecyclerFavorite
import com.myproject.app.presentation.adapter.MyViewModelFactory
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    // Комментарий про lateinit в PopularFragment подходит и сюда.

    /**
     * И на будущее, как проверить "чистоту" фрагмента например:
     *     1) он не должен ходить в сеть или БД
     *     2) он должен только показывать данные, полученные из вьюмодели, отправлять данные
     *     (о кликах, ввод юзера, или что-то еще что ему пришло в аргументах допустим, все что не связано напрямую с вью)
     *     во вьюмодель, и должен содержать логику связанную только со своими вьюшками.
     *     Если фрагмент меняет поле в классе модельки, это нехорошо.
     *     Если фрагмент вызывает функцию вьюмодели, которая берет текущий айтем и меняет ему поле, это хорошо
     *
     *     Смысл фрагмента - показывать актуальные данные и реагировать на действия пользователя
     *     Смысл вьюмодели - производить операции над данными, менять их, содержать какую то логику, запускать юзкейсы (то есть только операции над данными)
     *     Смысл даты - получать данные и отдавать их. Неважно откуда - из сети или из БД.
     *     Смысл домена - содержать в себе лоигику. Например:
     *     класс CanEnterUseCase который принимает возраст и выдает true или false в зависимости от того, подходит ли возраст юзера чтобы войти куда-то
     *
     *     Домен не должен знать ни о вью, ни о вьюмодели, ни о чем вообще -
     *     это корневой слой и все остальное под него подстраивается.
     *
     *     Дата не должна знать о вью. Также есть Repository Pattern который решает, какие данные отдать: из бд или из сети
     *     Если нужно реализовать проверку на актуальность данных, можно сделать это в классе-репозитории
     *
     *     Кстати:
     *     @android_architecture - чат по архитектуре, там в закрепе или в описании где-то есть ссылки
     *     на кучу полезных материалов, ну и всегда можно задать вопрос
     *
     *     А, и еще. Юзкейсам не обязательно наследовать какой-то интерфейс, но если они его наследуют, то
     *     тогда это должен быть какой нибудь BaseUseCase в котором функция execute например.
     *     Тогда у всех юзкейсов будет один родитель, и они будут отличаться лишь тем, как они имплементируют функцию execute
     */

    private lateinit var dataJsonRepository: DataJsonRepository
    private lateinit var myAdapterRecycler: MyAdapterRecyclerFavorite
    private lateinit var viewModelFavorite: ViewModelFavorite

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

        binding.listFavorite.adapter = myAdapterRecycler
        binding.listFavorite.layoutManager = LinearLayoutManager(requireContext())

        viewModelFavorite =
            ViewModelProvider(
                requireActivity(),
                MyViewModelFactory(dataJsonRepository)
            )[ViewModelFavorite::class.java]

        viewModelFavorite.selectAllLiveData.observe(viewLifecycleOwner) {
            myAdapterRecycler.addList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelFavorite.selectAllCurrencyDatabase()
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