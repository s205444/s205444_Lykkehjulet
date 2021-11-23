package com.example.s205444_lykkehjulet.Model

enum class EnumFortunes(val points : Int, val displayText : String) {
    THOUSAND(1000, "1000"),
    HUNDRED(100, "100"),
    BANKRUPT(0, "Bankrupt"),
    EXTRA_TURN(0, "Extra turn"),
    MISS_TURN(0, "Miss turn");
}