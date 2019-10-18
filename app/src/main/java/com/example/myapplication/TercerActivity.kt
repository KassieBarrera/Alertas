package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_tercer.*
import java.net.URI
import android.Manifest
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TercerActivity : AppCompatActivity() {

    private val PHONE_NUMBER = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tercer)

/*--------------------------------      FLECHA PARA REGRESAR    ----------------------------------------------*/
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

/*--------------------------------          BOTON PARA LLAMADA          -------------------------------------*/
        imgbtnTelefono!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val numero = edtPhone!!.text.toString()
                if (!numero.isEmpty()) {
/*--------------------------------          COMPROBAR VERSION ACTUAL CON LA DE MARSHMALLOW  ------------------*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
/*--------------------------------          COMPROBAR EL PERMISO        --------------------------------------*/
                        doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
                            //Pregunta si la version del dispositivo es mayor o igual a a la del codigo
                            if (verPermiso(Manifest.permission.CALL_PHONE)) {
                                /*--------------------------------          SI HA HACEPTADO EL PERMISO EN EL MANIFEST  ----------------------*/
//                                    Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero))
                                if (ActivityCompat.checkSelfPermission(
                                        this@TercerActivity,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    return
                                }
                                // startActivity(intetAceptado)
                                makeCall(numero)
                            } else {
/*---------------------------------     PREGUNTARLE POR EL PERMISO      -----------------------------------------*/
                                if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                    requestPermissions(
                                        arrayOf(Manifest.permission.CALL_PHONE),
                                        PHONE_NUMBER
                                    )
                                } else {
/*----------------------    SI YA DENEGO EL PERMISO Y QUIERE ACCEDER NUEVAMENTE  LO DIRIJIMOS
                            A LOS AJUSTES PARA QUE EL PERMISO-------------------------------------------------*/
                                    //Toast.makeText(this@TercerActivity,"Permisos denegados",Toast.LENGTH_LONG).show()
                                    longToast(getString(R.string.main3_deneid_permisses))
                                    val intentSettings =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                    intentSettings.data = Uri.parse("package:" + packageName)
                                    intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                    intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                    startActivity(intentSettings)
                                }
                            }
                        }
                        doIfSdk(Build.VERSION_CODES.LOLLIPOP) {
                            versionAntigua(numero)
                        }

                    }/*else {
                        versionAntigua(numero)
                    }*/
                } else {
                    /*Toast.makeText(this@TercerActivity, "Debes marcar un numero", Toast.LENGTH_LONG)
                        .show()*/
                    longToast(getString(R.string.main3_insert_number))
                }
            }

            fun versionAntigua(phoneNumber: String) {
                //val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber))
                if (verPermiso(Manifest.permission.CALL_PHONE)) {
                    if (ActivityCompat.checkSelfPermission(
                            this@TercerActivity, Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    //startActivity(intentCall)
                    makeCall(phoneNumber)
                }
            }
        })

/*--------------------------------      BUSCADOR WEB                    -------------------------------------*/
        imgbtnWeb!!.onClick {
            val URL = edtWeb.text.toString()
            browse("http://$URL")
            /*val intentWeb = Intent()
            intentWeb.action = Intent.ACTION_VIEW
            intentWeb.data = Uri.parse("http://" + URL)
            startActivity(intentWeb)*/
        }

/*--------------------------------      BOTON PARA ENVIAR E-MAIL         -------------------------------------*/
        btnEmail!!.onClick {
            val email = "algunemail@gmail.com"   //pasamos el correo

            email(email, getString(R.string.main3_mail_title), getString(R.string.main3_mail_body))
            /*val intentCorreo = Intent(Intent.ACTION_SEND, Uri.parse(email))
            intentCorreo.type = "plain/text"
            intentCorreo.putExtra(Intent.EXTRA_SUBJECT, "Titulo")
            intentCorreo.putExtra(Intent.EXTRA_TEXT, "El texto del correo")
            intentCorreo.putExtra(Intent.EXTRA_EMAIL, arrayOf("elcorreodealguien@gmail.com", "anotherone@gmail.com" ))
            startActivity(Intent.createChooser(intentCorreo, "Elige uno"))*/
        }

/*-------------------------------       BOTON PARA LLAMADA SIN PERMISOS --------------------------------------*/
        btnContactPhone!!.onClick {
            /*val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:51190023"))
            startActivity(intentCall)*/
            makeCall(getString(R.string.main3_contactCall_number))
        }
/*-------------------------------       BOTON PARA CAMARA               --------------------------------------*/
        imgbtnCamara!!.onClick {
            val intentCamara = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intentCamara)
        }

    }

    /*------------------------------        PARA HABILITAR EL MENU          --------------------------------------*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_contactos -> {
                val intentContactos =
                    Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
                startActivity(intentContactos)
            }
            R.id.menu_compartir -> {
                share(getString(R.string.main3_share), getString(R.string.main3_share_subject))
            }
            R.id.menu_mensaje -> {
                sendSMS(getString(R.string.main3_sms_number), getString(R.string.main3_sms_message))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*------------------------------------  Metodo para compobrar permisos  --------------------------------------*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PHONE_NUMBER -> {
                val permiso = permissions[0]
                val resultados = grantResults[0]

                if (permiso == Manifest.permission.CALL_PHONE) {
/*------------------------------------  COMPROBAR SI HA SIDO ACEPTADO O DENEGADO LA PETICION DE PERSMISO -------*/
                    if (resultados == PackageManager.PERMISSION_GRANTED) {
/*------------------------------------  CONCEDIO SU PERMISO     ------------------------------------------------*/
                        val phoneNumber = edtPhone.text.toString()
                        val intentCall = Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel" + phoneNumber)
                        )  //Uri indicando que rol va a jugar en el protocolo
/*---------------------------------     VERIFICAR SI EXISTE EL PERMISO EN EL MANIFEST EXPLICITAMENTE
                       * YA QUE EL USUARIO PUEDE DENEGARLO      --------------------------------------------------*/
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                        startActivity(intentCall)
                    } else {
/*--------------------------------      SE DENEGO EL PERMISO    -------------------------------------------------*/
                        Toast.makeText(
                            this,
                            getString(R.string.main3_deneidpermision_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun verPermiso(permission: String): Boolean {
        val result = this.checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}