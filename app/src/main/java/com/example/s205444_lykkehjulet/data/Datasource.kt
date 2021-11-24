package com.example.s205444_lykkehjulet.data

import com.example.s205444_lykkehjulet.R

class Datasource {

    fun loadCategories(): List<Categories> {
        return listOf<Categories>(
            Categories(R.string.Category1),
            Categories(R.string.Category2),
            Categories(R.string.Category3),
            Categories(R.string.Category4),
            Categories(R.string.Category5),
            Categories(R.string.Category6),
        )
    }
}