package com.myproject.app.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myproject.app.data.db.DataCurrency
import retrofit2.HttpException

class CurrencyPagingSource(private val repository: DataJsonRepository) :
    PagingSource<Int, DataCurrency>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataCurrency> {
        return try {
            val currentPage = params.key ?: 1
            val getResponseCurrency = repository.getCurrencyPage(currentPage)
            val data = getResponseCurrency.body()!!.rates
            val responseData = mutableListOf<DataCurrency>()

            for (entry in data) {
                responseData.add(
                    DataCurrency(
                        currency = entry.key,
                        value = entry.value.toString()
                    )
                )
            }

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (eHttp: HttpException) {
            LoadResult.Error(eHttp)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataCurrency>): Int? {
        return null
    }
}