package com.deepak.pokequest.domain.model


data class Pokemon(
    val id: String,
    val smallImage: String? = null,
    val largeImage: String? = null,
    val name: String,
    val types: List<String> = emptyList(),
    val subTypes: List<String> = emptyList(),
    val level: String,
    val hp: String,
    val attacks: List<Attack> = emptyList(),
    val weaknesses: List<Weakness> = emptyList(),
    val abilities: List<Ability> = emptyList(),
    val resistances: List<Resistance> = emptyList()
)

data class Attack(
    val convertedEnergyCost: Int,
    val cost: List<String> = emptyList(),
    val damage: String,
    val name: String,
    val text: String
)

data class Weakness(
    val type: String,
    val value: String
)

data class Ability(
    val name: String,
    val text: String,
    val type: String
)

data class Resistance(
    val type: String,
    val value: String
)