package com.example.storyapp.utils

import com.example.storyapp.presentation.data.responses.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryResponse> {
        val items: MutableList<StoryResponse> = arrayListOf()
        for (i in 0..100) {
            val quote = StoryResponse(
                i.toString(),
                "createdAt + $i",
                "description $i",
                0.0,
                0.0,
                "name $i",
                "photoUrl $i",
            )
            items.add(quote)
        }
        return items
    }
}