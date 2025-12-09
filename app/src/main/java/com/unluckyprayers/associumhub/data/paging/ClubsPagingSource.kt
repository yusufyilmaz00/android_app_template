package com.unluckyprayers.associumhub.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unluckyprayers.associumhub.data.remote.mapper.toDomain
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.domain.model.club.Club

class ClubsPagingSource(
    private val api: ApiService
) : PagingSource<Int, Club>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Club> {
        return try {
            val page = params.key ?: 1

            val response = api.getClubs(page = page)

            val data = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.meta.has_next_page) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Club>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }
}
