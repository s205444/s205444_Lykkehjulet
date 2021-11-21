package com.example.s205444_lykkehjulet

enum class EnumFortunes {
    THOUSAND{
            fun returnInt() : Int {
                return 1000
            }
            },
    HUNDRED,
    BANKRUPT,
    EXTRA_TURN,
    MISS_TURN
}