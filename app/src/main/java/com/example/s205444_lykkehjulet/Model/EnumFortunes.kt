package com.example.s205444_lykkehjulet.Model

import com.example.s205444_lykkehjulet.R

enum class EnumFortunes(val points : Int, val displayText : String) {
    THOUSAND(1000, "1000 \n >>> Choose a letter, then spin"),
    HUNDRED(50, "50 \n >>> Choose a letter, then spin"),
    BANKRUPT(0, "Bankrupt"),
    EXTRA_TURN(0, "Extra turn"),
    MISS_TURN(0, "Miss turn");
}