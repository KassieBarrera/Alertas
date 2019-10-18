package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class MainActivity : AppCompatActivity() {

  //  val saludo = "Hola desde ActivityMain"
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*--------------------      FORZAR ICONO EN ACTION BAR  ------------------------------------------*/
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_launcher)

       // btnCalcular.text = "Calcula tu edad"

      toast(getString(R.string.main_lngtst_inicial))
        btnCalcular.setOnClickListener {
            //            Toast.makeText(this, "BotonCalcular", Toast.LENGTH_SHORT).show()
            val anacimiento: Int = edtFecha.text.toString().toInt()
            val anioActual = Calendar.getInstance().get(Calendar.YEAR)
            val miEdad = anioActual - anacimiento
            //txtedad.text = "Tu edad es $miEdad"
            startActivity<SecondActivity>("edad" to  miEdad)
        }
      btnSimpleAlert.onClick {
          alert(getString(R.string.main_simple_alert), getString(R.string.main_alert_title)){
              yesButton { longToast(getString(R.string.main_positivebtn)) }
              noButton { toast(getString(R.string.main_negativebnt)) }
          }.show()
      }

      btnListAlert.onClick {
          val paises = listOf("Guatemala", "España", "Argentina", "Peru", "Volivia", "Chile")
          selector(getString(R.string.main_alert_lista), paises, { dialogInterface, i ->
              longToast("Genial!, entonces vives en ${paises[i]}, cierto?") })
      }
       btnProgresDialog.onClick {
           val dialog = progressDialog (message = getString(R.string.main_progres_dialog), title = getString(
                          R.string.main_titleDialog))
       }
       /* btnSiguiente.setOnClickListener{
            startActivity(this,  SecondActivity::class.java)
        }*/
    }
   /* fun startActivity(activity: Activity,   nextActivity: Class<*>){
        val intent = Intent(activity, nextActivity)
        intent.putExtra("saludo", saludo)
        activity.startActivity(intent)
    }*/
}

