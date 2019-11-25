package com.egco428.egci428_practice


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.egco428.egci428_practice.Helper.HTTPHelper
import com.egco428.egci428_practice.model.User
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //Update your firebase database URL here
    var url = "https://egci428-aae44.firebaseio.com/userProfile/"
    var uname: String? = null
    var pname: String? = null
    var userprofile: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            uname = userText.text.toString()
            pname = passText.text.toString()
            if (!uname.isNullOrEmpty() && !pname.isNullOrEmpty()) {
                var asyncTask = object : AsyncTask<String, String, String>() {

                    override fun onPreExecute() {
                        Toast.makeText(this@MainActivity, "Please wait...", Toast.LENGTH_SHORT).show()
                    }

                    override fun doInBackground(vararg p0: String?): String {
                        val helper = HTTPHelper()
                        // req http
                        return helper.getHTTPData(url+uname+".json")
                    }

                    override fun onPostExecute(result: String?) {
                        //result from http req return here
                        if (result != "null") {
                            //change json form into User object
                            userprofile = Gson().fromJson(result,User::class.java)
                            if (pname == userprofile!!.password){
                                val intent = Intent(this@MainActivity, UserListActivity::class.java)
                                startActivity(intent)
                            }else {
                                Toast.makeText(this@MainActivity, "Password is not matched", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Username or Password is not matched", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                asyncTask.execute()
            }
        }
        cancelBtn.setOnClickListener {
            // cancel = clear data
            userText.text = null
            passText.text = null
        }
    }

}
