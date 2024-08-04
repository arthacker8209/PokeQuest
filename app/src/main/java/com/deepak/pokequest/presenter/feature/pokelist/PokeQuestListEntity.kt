package com.deepak.pokequest.presenter.feature.pokelist

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Keep
@Entity("pokemon")
@SuppressLint("DefaultLocale")
@Parcelize
data class PokeQuestListEntity(
    @PrimaryKey
    val id: String = "0",
    val name: String? = null,
    val smallImage: String? =  null,
    val types: List<String>? = null,
    val level: String? = null,
    val hp: String? = null
) : Parcelable