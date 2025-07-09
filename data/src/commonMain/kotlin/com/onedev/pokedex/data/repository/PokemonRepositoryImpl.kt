import com.onedev.pokedex.data.api.PokemonApi
import com.onedev.pokedex.data.core.getProfileImageUrl
import com.onedev.pokedex.domain.model.Pokemon
import com.onedev.pokedex.domain.model.PokemonAbility
import com.onedev.pokedex.domain.model.PokemonDetail
import com.onedev.pokedex.domain.model.PokemonStat
import com.onedev.pokedex.domain.model.PokemonType
import com.onedev.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val api: PokemonApi
): PokemonRepository {
    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        val response = api.getPokemonList(offset, limit)
        return response.results?.map {
            val id = it?.url?.trimEnd('/')?.split("/")?.last()?.toInt()
            Pokemon(
                id = id,
                name = it?.name?.replaceFirstChar { c -> c.uppercaseChar() },
                imageUrl = "${getProfileImageUrl()}/${id}.png"
            )
        } ?: emptyList()
    }

    override suspend fun getPokemonDetail(pokemonName: String): PokemonDetail {
        val response = api.getPokemonDetail(pokemonName)
        return PokemonDetail(
            id = response.id,
            name = response.name?.replaceFirstChar { it.uppercaseChar() },
            height = response.height,
            weight = response.weight,
            order = response.order,
            types = response.types?.mapNotNull { wrapper ->
                wrapper?.type?.name?.let { PokemonType(it) }
            },
            stats = response.stats?.mapNotNull { wrapper ->
                val name = wrapper?.stat?.name
                val baseStat = wrapper?.baseStat
                if (name != null) PokemonStat(name, baseStat) else null
            },
            abilities = response.abilities?.mapNotNull { wrapper ->
                val name = wrapper?.ability?.name
                if (name != null) PokemonAbility(name) else null
            }
        )
    }


}