package com.example.tugasakhir_kmmi

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.tugasakhir_kmmi.databinding.CustomTampilBinding
import org.json.JSONObject

class adapter(private val context: Context, private val results : ArrayList<model>): RecyclerView.Adapter<adapter.MyHolder>() {
    private var Items = ArrayList<model>()

    init {
        this.Items = results
    }

    inner class MyHolder(val binding: CustomTampilBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            CustomTampilBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val result = Items[position]
        with(holder) {
            binding.tvNama.text = result.Nama
            binding.tvNIM.text = result.NIM
            binding.tvKelas.text = result.Kelas
            binding.tvTanggal.text = result.Tanggal
            if (result.Kehadiran == "H") {
                binding.tvKehadiran.text = "Hadir"
            } else if(result.Kehadiran == "A"){
                binding.tvKehadiran.text = "Alfa"
            } else if(result.Kehadiran == "I"){
                binding.tvKehadiran.text = "Izin" 
            }else{
                binding.tvKehadiran.text = "Sakit"
            }
            binding.root.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Pilih Pengaturan")
                val pilihan = arrayOf("Edit", "Delete", "Cancel")
                builder.setItems(pilihan) { dialog, which ->
                    when (which) {
                        0 -> {
                            val a = Intent(context, edit::class.java)
                            a.putExtra("Id", result.Id)
                            context.startActivity(a)
                        }

                        1 -> {
                            hapus(result.Id)
                        }

                        2 -> {
                            dialog.dismiss()
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }

    fun hapus(Id: String) {
        val loading = ProgressDialog(context)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post("http://192.168.42.142/Tugas_Akhir_KMMI/hapus.php")
            .addBodyParameter("Id", Id)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getInt("success") == 1) {
                        Toast.makeText(context, response.getString("pesan"), Toast.LENGTH_SHORT).show()
                        (context as Activity).finish()
                    } else {
                        Toast.makeText(context, response.getString("pesan"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(error: ANError) {
                    loading.dismiss()
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}