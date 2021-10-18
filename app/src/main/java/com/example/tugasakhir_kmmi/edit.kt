package com.example.tugasakhir_kmmi

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.tugasakhir_kmmi.databinding.ActivityEditBinding
import org.json.JSONObject
import java.util.*

class edit : AppCompatActivity() , DatePickerDialog.OnDateSetListener{
    private lateinit var binding: ActivityEditBinding
    var kh = "H"
    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pickDate()
        cari_data(intent.getStringExtra("Id").toString())

        binding.btEdit.setOnClickListener {
            val Nama = binding.etNama.text.toString()
            val nim = binding.etNIM.text.toString()
            val kelas= binding.etKelas.text.toString()
            val tanggal= binding.tvTextime.text.toString()
            if (Nama.isEmpty()) {
                binding.etNama.error = "Kosong"
                binding.etNama.requestFocus()
            } else if (nim.isEmpty()) {
                binding.etNIM.error = "Kosong"
                binding.etNIM.requestFocus()
            } else if (kelas.isEmpty()) {
                binding.etKelas.error = "Kosong"
                binding.etKelas.requestFocus()
            } else {
                AndroidNetworking.post("http://192.168.42.142/Tugas_Akhir_KMMI/edit.php")
                    .addBodyParameter("Id", intent.getStringExtra("Id"))
                    .addBodyParameter("Nama", Nama)
                    .addBodyParameter("NIM", nim)
                    .addBodyParameter("Kelas", kelas)
                    .addBodyParameter("Kehadiran", kh)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(this@edit, response.getString("pesan"), Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@edit, response.getString("pesan"), Toast.LENGTH_SHORT).show()

                            }
                        }
                        override fun onError(error: ANError) {
                            Toast.makeText(this@edit, error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        binding.rgKehadiran.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.rbHadir.id) {
                kh = "H"
            }else if (checkedId == binding.rbHadir.id){
                kh = "A"
            }else if (checkedId == binding.rbIzin.id){
                kh = "I"
            } else {
                kh = "S"
            }
        }
    }

    fun cari_data(Id: String) {
        AndroidNetworking.get("http://192.168.42.142/Tugas_Akhir_KMMI/cari.php")
            .addQueryParameter("Id", Id)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getInt("success") == 1) {
                        val jsonobjcet = response.optJSONObject("data")
                        binding.etNama.setText(jsonobjcet.getString("Nama"))
                        binding.etNIM.setText(jsonobjcet.getString("NIM"))
                        binding.etKelas.setText(jsonobjcet.getString("Kelas"))   
                        binding.tvTextime.setText(jsonobjcet.getString("Tanggal"))
                        if (jsonobjcet.getString("Kehadiran") == "H") {
                            binding.rbHadir.isChecked = true
                            kh = "H"
                        }else if (jsonobjcet.getString("Kehadiran") == "A") {
                            binding.rbAlfa.isChecked = true
                            kh = "A"
                        }else if (jsonobjcet.getString("Kehadiran") == "I"){
                            binding.rbIzin.isChecked = true
                            kh = "I"
                        } else {
                            binding.rbSakit.isChecked = true
                            kh = "S"
                        }
                    } else {
                        Toast.makeText(this@edit,response.getString("pesan"), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onError(error: ANError) {
                    Toast.makeText(this@edit, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        binding.btnTimepicker.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }



    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        binding.tvTextime.text = "$savedDay-$savedMonth-$savedYear\n"

    }
}