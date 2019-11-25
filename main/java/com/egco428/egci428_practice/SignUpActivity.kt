package com.egco428.egci428_practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egco428.egci428_practice.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {
    lateinit var dataReference: DatabaseReference

    //lateinit var usrList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // connect to firebase
        dataReference = FirebaseDatabase.getInstance().getReference("userProfile")

        randomBtn.setOnClickListener {
            latText.setText(randomLocation(true).toString())
            lonText.setText(randomLocation(false).toString())
        }
        addBtn.setOnClickListener {
            saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun randomLocation(type: Boolean): Double {
        val r = Random()
        var result: Double
        //true: latitude, false: longitude
        if (type) {
            result = (r.nextInt(170) - 85).toDouble()
        } else {
            result = (r.nextInt(360) - 180).toDouble()
        }
        return result
    }

    private fun saveData() {
        val usr = userSignText.text.toString()
        val pwd = passSignText.text.toString()
        val lat = latText.text.toString().toDouble()
        val lon = lonText.text.toString().toDouble()
        Log.d("Debug", usr)
        if (usr.isEmpty()) {
            userSignText.error = "Please enter a username"
            return
        }
        val userID = usr
//        userData = object of user
        val userData = User(usr,pwd,lat,lon)
//        add data into firebase
        dataReference.child(userID).setValue(userData)
            .addOnCompleteListener {
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_signup, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
