package mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.ui.registropropietario

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.clases.Propietario
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.databinding.FragmentRegistroPropietarioBinding
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.ui.actualizarpropietario.ActualizarMainActivity

class RegistroPropietarioFragment : Fragment() {

    private var _binding: FragmentRegistroPropietarioBinding? = null
    var listaIDs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(RegistroPropietarioViewModel::class.java)

        _binding = FragmentRegistroPropietarioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mostrarDatosEnListView()
        binding.insertar.setOnClickListener {
            val c0 = binding.txtcurp.text.toString()
            val c1 = binding.txtnombrePropietario.text.toString()
            val c2 = binding.txttelefono.text.toString()
            val c3 = binding.txtedadPropietario.text.toString()

            val regex = "^[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1}$".toRegex()

            if (!(c0 == "" || c1 == "" || c2 == "" || c3 == "")) {
                if (!regex.containsMatchIn(c0)) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("CURP")
                        .setMessage("NO CUMPLE CON LOS PARAMETROS DE UNA CURP")
                        .setNeutralButton("ACEPTAR") {d,i -> }
                        .show()
                } else if(!(c2.length == 10)) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("TELEFONO")
                        .setMessage("EL NÚMERO DEBEN SER 10 DIGITOS")
                        .setNeutralButton("ACEPTAR") {d,i -> }
                        .show()
                } else {
                    var propietario = Propietario(requireContext())

                    propietario.curp = binding.txtcurp.text.toString()
                    propietario.nombre = binding.txtnombrePropietario.text.toString()
                    propietario.telefono = binding.txttelefono.text.toString()
                    propietario.edad = binding.txtedadPropietario.text.toString().toInt()

                    var resultado = propietario.insertar()
                    if(resultado) {
                        Toast.makeText(requireContext(),"SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                            .show()
                        mostrarDatosEnListView()
                        limpiarCampos()
                    } else {
                        AlertDialog.Builder(requireContext())
                            .setTitle("ERROR")
                            .setMessage("NO SE PUDO INSERTAR")
                            .show()
                    }
                }
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("ATENCIÓN")
                    .setMessage("HAY CAMPOS VACIOS")
                    .show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mostrarDatosEnListView() {
        var listaPropietarios = Propietario(requireContext()).mostrarTodos()
        var nombrePropietarios = ArrayList<String>()

        listaIDs.clear()
        (0..listaPropietarios.size-1).forEach {
            val al = listaPropietarios.get(it)
            nombrePropietarios.add(al.nombre)
            listaIDs.add(al.curp)
        }

        binding.listaPropietario.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,nombrePropietarios)
        binding.listaPropietario.setOnItemClickListener { adapterView, view, indice, l ->
            val curpLista = listaIDs.get(indice)
            val propietario = Propietario(requireContext()).mostrarPropietario(curpLista)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCIÓN")
                .setMessage("¿Qué deseas hacer con ${propietario.nombre}, \nTelefono: ${propietario.telefono}, \n" +
                        "Edad: ${propietario.edad}?")
                .setNegativeButton("Eliminar") {d,i ->
                        propietario.eliminar()
                        mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar") {d,i ->
                    val otraVentana = Intent(requireActivity(), ActualizarMainActivity::class.java)
                    otraVentana.putExtra("propietarioActualizar", propietario.curp)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar") {d,i -> }
                .show()
        }
    }

    fun limpiarCampos() {
        binding.txtcurp.setText("")
        binding.txtnombrePropietario.setText("")
        binding.txttelefono.setText("")
        binding.txtedadPropietario.setText("")
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosEnListView()
    }
}