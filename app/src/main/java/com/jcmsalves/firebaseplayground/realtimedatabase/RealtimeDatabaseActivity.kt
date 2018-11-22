package com.jcmsalves.firebaseplayground.realtimedatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.*
import com.jcmsalves.firebaseplayground.R
import kotlinx.android.synthetic.main.activity_realtime_database.*

class RealtimeDatabaseActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_database)

        databaseReference = FirebaseDatabase.getInstance().reference
        gamesAdapter = GamesAdapter()

        recycler_view_games.layoutManager = LinearLayoutManager(this)
        recycler_view_games.adapter = gamesAdapter
        val itemDecor = DividerItemDecoration(this, VERTICAL)
        recycler_view_games.addItemDecoration(itemDecor)

        button_submit_data.setOnClickListener {

            if (invalidForm()) {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val key = databaseReference.child("games").push().key
            val game = Game(
                name = edit_text_name.text.toString(),
                releaseYear = edit_text_release_year.text.toString().toInt(),
                platform = edit_text_platform.text.toString(),
                score = edit_text_score.text.toString().toInt()
            )
            key?.let {
                game.uuid = key
                databaseReference.child("games").child(key).setValue(game)
            }
            gamesAdapter.addGame(game)
        }

        databaseReference.child("games").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@RealtimeDatabaseActivity,
                    "Database error: $databaseError.message", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gamesList = dataSnapshot.children.mapNotNull { it.getValue<Game>(Game::class.java) }
                gamesAdapter.setGames(gamesList)
            }
        })
    }

    private fun invalidForm() =
        edit_text_name.text.isNullOrBlank()
            || edit_text_platform.text.isNullOrBlank()
            || edit_text_release_year.text.isNullOrBlank()
            || edit_text_score.text.isNullOrBlank()
}
