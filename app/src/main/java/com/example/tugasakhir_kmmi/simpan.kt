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
import com.example.tugasakhir_kmmi.databinding.ActivitySimpanBinding
import org.json.JSONObject
import java.util.*

class simpan : AppCompatActivity() , DatePickerDialog.OnDateSetListener {
    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    private var kh: String? = "H"
    private lateinit var binding: ActivitySimpanBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pickDate()

        binding.rgKehadiran.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == binding.rbHadir.id) {
                kh = "H"
            } else if(checkedId == binding.rbAlfa.id){
                kh = "A"
            }else if(checkedId == binding.rbIzin.id){
                kh = "I"
            }else{
                kh = "S"
            }
        }

        binding.btSimpan.setOnClickListener {
            val Nama = binding.etNama.text.toString()
            val nim = binding.etNIM.text.toString()
            val kelas = binding.etKelas.text.toString()
            val tanggal = binding.tvTextime.text.toString()
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
                //simpan data
                AndroidNetworking.post("http://192.168.42.142/Tugas_Akhir_KMMI/simpan.php")
                    .addBodyParameter("Nama", Nama)
                    .addBodyParameter("NIM", nim)
                    .addBodyParameter("Kelas", kelas)
                    .addBodyParameter("Tanggal", tanggal)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(
                                    this@simpan, response.getString("pesan"), Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@simpan, response.getString("pesan"), Toast.LENGTH_SHORT).show()

                            }
                        }
                        override fun onError(error: ANError) {
                            Toast.makeText(this@simpan, error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
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