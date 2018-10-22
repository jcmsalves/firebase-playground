package com.jcmsalves.firebaseplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.jcmsalves.firebaseplayground.R.id.button_custom_event
import com.jcmsalves.firebaseplayground.R.id.button_firebase_event
import kotlinx.android.synthetic.main.activity_analytics.*

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        firebaseAnalytics.setUserProperty("my_test_user_property",
//            "my test user property value")

        // Log Firebase Event
        button_firebase_event.setOnClickListener {
            firebaseAnalytics.logEvent(
                FirebaseAnalytics.Event.SELECT_CONTENT,
                Bundle().apply {
                    putString(FirebaseAnalytics.Param.ITEM_ID, "button_firebase_event")
                })
        }

        // Log Custom Event
        button_custom_event.setOnClickListener {
            firebaseAnalytics.logEvent(
                "SAMPLE_CUSTOM_EVENT_CLICKED",
                Bundle().apply {
                    putString(FirebaseAnalytics.Param.ITEM_ID, "button_custom_event")
                })
        }
    }
}
