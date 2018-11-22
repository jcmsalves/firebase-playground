package com.jcmsalves.firebaseplayground.realtimedatabase

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jcmsalves.firebaseplayground.R
import kotlinx.android.synthetic.main.item_game.view.*

class GamesAdapter : RecyclerView.Adapter<GameViewHolder>() {

    private val games = ArrayList<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false))

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }

    fun setGames(gamesList: List<Game>) {
        games.clear()
        games.addAll(gamesList)
        notifyDataSetChanged()
    }

    fun addGame(game: Game) {
        games.add(game)
        notifyDataSetChanged()
    }

}

class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(game: Game) {
        itemView.text_view_game_name.text = game.name
        itemView.text_view_release_year.text = game.releaseYear.toString()
        itemView.text_view_platform.text = game.platform
    }
}
