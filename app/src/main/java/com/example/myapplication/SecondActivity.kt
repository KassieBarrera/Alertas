package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.startActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

/*------------------------  FLECHA PARA REGRESAR    ----------------------------------------------*/
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)


/*------------------------  IR A LA ACTIVIDAD TRES  ----------------------------------------------*/
        btnTercerActiviad.setOnClickListener {
       //     startActivity(this, TercerActivity::class.java)
        startActivity<TercerActivity>()
        }
        val txtIntent = findViewById<TextView>(R.id.txtIntent)
        val bundle = intent.extras
        val edad = bundle!!.getInt(getString(R.string.main2_key_edad))
        txtIntent.text = edad.toString()

        /*if (bundle != null && bundle.getString("saludo") != null) {
            val saludo = bundle.getString("saludo")
            txtIntent.text = saludo
        } else {
            Toast.makeText(this, "Vacio", Toast.LENGTH_SHORT).show()
        }*/
    }
    /*fun startActivity(activity: Activity,   nextActivity: Class<*>){
        val intent = Intent(activity, nextActivity)
        activity.startActivity(intent)
    }*/
}
