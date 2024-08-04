package com.deepak.pokequest.data.db.convertors

import androidx.room.TypeConverter
import com.deepak.pokequest.data.db.entity.AbilityEntity
import com.deepak.pokequest.data.db.entity.AttackEntity
import com.deepak.pokequest.data.db.entity.ResistanceEntity
import com.deepak.pokequest.data.db.entity.WeaknessEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromAttackEntityList(value: String?): List<AttackEntity> {
        val listType = object : TypeToken<List<AttackEntity>>() {}.type
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toAttackEntityList(list: List<AttackEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromWeaknessEntityList(value: String?): List<WeaknessEntity> {
        val listType = object : TypeToken<List<WeaknessEntity>>() {}.type
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toWeaknessEntityList(list: List<WeaknessEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromAbilityEntityList(value: String?): List<AbilityEntity> {
        val listType = object : TypeToken<List<AbilityEntity>>() {}.type
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toAbilityEntityList(list: List<AbilityEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromResistanceEntityList(value: String?): List<ResistanceEntity> {
        val listType = object : TypeToken<List<ResistanceEntity>>() {}.type
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toResistanceEntityList(list: List<ResistanceEntity>?): String {
        return gson.toJson(list)
    }
}

