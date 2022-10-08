package com.example.auditoriafirst.components.negocio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.MainActivity
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Database.AuditoriaDb
import kotlinx.coroutines.launch

class NegocioResetDialog : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = AuditoriaDb(requireContext())

        var rootView : View = inflater.inflate(R.layout.dialog_negocio_reset, container,false)
        val login_activity = Intent(requireContext(),MainActivity::class.java)

        var btnDiaResetAceptar = rootView.findViewById<Button>(R.id.btnDiaResetAceptar)
        var btnDiaResetCancelar = rootView.findViewById<Button>(R.id.btnDiaResetCancelar)

        btnDiaResetAceptar.setOnClickListener {
            lifecycleScope.launch {
                db.ProductoDao().borrarAllAll()
                db.DistritoDao().borrarTodo()
                db.CanalDao().borrarTodo()
                db.ZonaDao().borrarTodo()
                db.ProductoMasterDao().borrarAllAll()
                db.NegocioDao().borrarTodo()
                db.UsuarioDao().borrarTodo()
                db.CategoriaDao().borrarTodo()


                startActivity(login_activity)
            }
        }

        btnDiaResetCancelar.setOnClickListener {
            dismiss()
        }



        return rootView
    }
}