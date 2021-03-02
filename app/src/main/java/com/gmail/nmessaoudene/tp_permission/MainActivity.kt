package com.gmail.nmessaoudene.tp_permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gmail.nmessaoudene.tp_permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupPermissions()

        binding.btnAuth.setOnClickListener {
            Log.v("TAG", "clique")
            makeRequest()
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Log.v("TAG", "Permission GOOD")

                    binding.txtAuth.text = "Permission Accepter"

                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }else{
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Nous avons besoin de votre accord pour afficher votre position GPS")
                            .setTitle("Autorisation")

                        builder.setPositiveButton("OK"
                        ) { dialog, id ->
                            Log.i("TAG", "Clicked")
                            makeRequest()
                        }

                        val dialog = builder.create()
                        dialog.show()
                    } else {
                        makeRequest()
                    }
                }

            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission to location denied")
            binding.txtAuth.text = "Bienvenue sur notre application, pour avoir votre position cliquer sur le bouton 'Autoriser l'accès"
        }
    }


}
