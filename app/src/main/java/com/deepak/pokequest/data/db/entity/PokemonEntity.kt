package com.deepak.pokequest.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.deepak.pokequest.data.db.convertors.Converters


@Entity(tableName = "pokemon")
@TypeConverters(Converters::class)
data class PokemonEntity(
    @PrimaryKey val id: String,
    val smallImage: String? = null,
    val largeImage: String? = null,
    val name: String,
    val types: List<String> = emptyList(),
    val subTypes: List<String> = emptyList(),
    val level: String,
    val hp: String,
    val attacks: List<AttackEntity> = emptyList(),
    val weaknesses: List<WeaknessEntity> = emptyList(),
    val abilities: List<AbilityEntity> = emptyList(),
    val resistances: List<ResistanceEntity> = emptyList()
)

data class AttackEntity(
    val convertedEnergyCost: Int,
    val cost: List<String> = emptyList(),
    val damage: String,
    val name: String,
    val text: String
)

data class WeaknessEntity(
    val type: String,
    val value: String
)

data class AbilityEntity(
    val name: String,
    val text: String,
    val type: String
)

data class ResistanceEntity(
    val type: String,
    val value: String
)
