package com.example.tugasakhir_kmmi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.tugasakhir_kmmi.databinding.ActivityTampilBinding
import org.json.JSONObject

class tampil : AppCompatActivity() {
    private lateinit var binding : ActivityTampilBinding
    val result = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTampil.setHasFixedSize(true)
        binding.rvTampil.layoutManager = LinearLayoutManager(this)
    }

    override fun onPostResume() {
        super.onPostResume()
        tampil_data()
    }

    fun tampil_data() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.get("http://192.168.42.142/Tugas_Akhir_KMMI/tampil.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject){
                    loading.dismiss()
                    result.clear()
                    if (response.getInt("success")==1){
                        val jsonArray = response.optJSONArray("data")

                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.optJSONObject(i)
                            result.add(
                                model(
                                    jsonObject.getString("Id"),
                                    jsonObject.getString("Nama"),
                                    jsonObject.getString("NIM"),
                                    jsonObject.getString("Kelas"),
                                    jsonObject.getString("Tanggal"),
                                    jsonObject.getString("Kehadiran")
                                )
                            )
                        }
                        val  adapter = adapter(this@tampil,result)
                        binding.rvTampil.adapter = adapter
                    }else{
                        Toast.makeText(this@tampil,response.getString("pesan"), Toast.LENGTH_LONG).show()

                    }
                }

                override fun onError(error: ANError) {
                    loading.dismiss()
                    Log.d("tampil", error.toString())
                }
            })
    }
}