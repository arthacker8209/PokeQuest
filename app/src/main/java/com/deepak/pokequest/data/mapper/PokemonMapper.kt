package com.deepak.pokequest.data.mapper

import com.deepak.pokequest.data.db.entity.AbilityEntity
import com.deepak.pokequest.data.db.entity.AttackEntity
import com.deepak.pokequest.data.db.entity.PokemonEntity
import com.deepak.pokequest.data.db.entity.ResistanceEntity
import com.deepak.pokequest.data.db.entity.WeaknessEntity
import com.deepak.pokequest.data.remote.response.PokemonResponse
import com.deepak.pokequest.domain.model.Ability
import com.deepak.pokequest.domain.model.Attack
import com.deepak.pokequest.domain.model.Pokemon
import com.deepak.pokequest.domain.model.Resistance
import com.deepak.pokequest.domain.model.Weakness
import kotlin.concurrent.thread

object PokemonMapper {

    fun PokemonResponse.Data.toEntity(): PokemonEntity {
        return PokemonEntity(
            id = this.id!!,
            smallImage = this.images?.small,
            largeImage = this.images?.large,
            name = this.name ?: "",
            types = this.types ?: emptyList(),
            subTypes = this.subtypes ?: emptyList(),
            level = this.level ?: "",
            hp = this.hp ?: "",
            attacks = this.attacks?.map { mapAttackToEntity(it) } ?: emptyList(),
            weaknesses = this.weaknesses?.map { mapWeaknessToEntity(it) } ?: emptyList(),
            abilities = this.abilities?.map { mapAbilityToEntity(it) } ?: emptyList(),
            resistances = this.resistances?.map { mapResistanceToEntity(it) } ?: emptyList()
        )
    }

    private fun mapAttackToEntity(attack: PokemonResponse.Attack?): AttackEntity {
        return AttackEntity(
            convertedEnergyCost = attack?.convertedEnergyCost ?: 0,
            cost = attack?.cost ?: emptyList(),
            damage = attack?.damage ?: "",
            name = attack?.name ?: "",
            text = attack?.text ?: ""
        )
    }

    private fun mapWeaknessToEntity(weakness: PokemonResponse.Weakness?): WeaknessEntity {
        return WeaknessEntity(
            type = weakness?.type ?: "",
            value = weakness?.value ?: ""
        )
    }

    private fun mapAbilityToEntity(ability: PokemonResponse.Ability?): AbilityEntity {
        return AbilityEntity(
            name = ability?.name ?: "",
            text = ability?.text ?: "",
            type = ability?.type ?: ""
        )
    }

    private fun mapResistanceToEntity(resistance: PokemonResponse.Resistance?): ResistanceEntity {
        return ResistanceEntity(
            type = resistance?.type ?: "",
            value = resistance?.value ?: ""
        )
    }

    fun PokemonEntity.toDomain(): Pokemon{
        return Pokemon(
            id = this.id,
            smallImage = this.smallImage ?: "",
            largeImage = this.largeImage ?: "",
            name = this.name,
            types = this.types,
            subTypes = this.subTypes,
            level = this.level,
            hp = this.hp,
            attacks = this.attacks.map { mapAttackToPokemon(it) },
            weaknesses = this.weaknesses.map { mapWeaknessToPokemon(it) },
            abilities = this.abilities.map { mapAbilityToPokemon(it) },
            resistances = this.resistances.map { mapResistanceToPokemon(it) }
        )
    }

    private fun mapAttackToPokemon(attack: AttackEntity?): Attack {
        return Attack(
            convertedEnergyCost = attack?.convertedEnergyCost ?: 0,
            cost = attack?.cost ?: emptyList(),
            damage = attack?.damage ?: "",
            name = attack?.name ?: "",
            text = attack?.text ?: ""
        )
    }

    private fun mapWeaknessToPokemon(weakness: WeaknessEntity?): Weakness {
        return Weakness(
            type = weakness?.type ?: "",
            value = weakness?.value ?: ""
        )
    }

    private fun mapAbilityToPokemon(ability: AbilityEntity?): Ability {
        return Ability(
            name = ability?.name ?: "",
            text = ability?.text ?: "",
            type = ability?.type ?: ""
        )
    }

    private fun mapResistanceToPokemon(resistance: ResistanceEntity?): Resistance {
        return Resistance(
            type = resistance?.type ?: "",
            value = resistance?.value ?: ""
        )
    }

}
