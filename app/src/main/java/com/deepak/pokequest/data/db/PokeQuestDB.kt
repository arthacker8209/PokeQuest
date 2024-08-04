package com.deepak.pokequest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepak.pokequest.data.db.convertors.Converters
import com.deepak.pokequest.data.db.dao.PokeQuestDao
import com.deepak.pokequest.data.db.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokeQuestDB : RoomDatabase() {
    abstract fun pokeQuestDao(): PokeQuestDao

    companion object {
        @Volatile
        private var instance: PokeQuestDB? = null

        fun getInstance(context: Context): PokeQuestDB {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PokeQuestDB::class.java,
                        "pokemon-database.db"
                    ).build()
                }
            }
            return instance!!
        }
    }
}