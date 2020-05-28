package com.example.domain.save

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.SaveListData
import io.reactivex.Single

class GetSaveFeedListUseCase(private val saveRepository: SaveRepository) : SingleUseCase<Unit, SaveListData>() {
    override fun execute(parameter: Unit): Single<Result<SaveListData>> {
        return saveRepository.getSavedFeedList()
    }
}