package ru.solandme.washwait.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.solandme.washwait.R
import android.content.Intent
import ru.solandme.washwait.ui.settings.SettingsFragment


class ChooseCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)
        closeActivity()
    }

    private fun closeActivity() {
        val intent = Intent()
        intent.putExtra(SettingsFragment.KEY_CITY_NAME, "Cheboksary") //TODO реализовать выбор города на карте или выбор из списка
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
