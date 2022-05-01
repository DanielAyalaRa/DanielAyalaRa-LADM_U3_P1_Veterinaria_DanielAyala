package mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.ui.consultapropietario

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.R
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.clases.Propietario
import mx.edu.ittepic.daar.ladm_u3_practica1_veterianaria_danielayala.databinding.ConsultaPropietarioFragmentBinding

class ConsultaPropietarioFragment : Fragment() {

    private var _binding: ConsultaPropietarioFragmentBinding? = null

    private val binding get() = _binding!!

    var listaIDs = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(ConsultaPropietarioViewModel::class.java)

        _binding = ConsultaPropietarioFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.SpConsultasPropietario
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.buscarProPor,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        mostrarDatosEnListView()

        binding.btnBuscar.setOnClickListener {
            var busqueda = binding.txtbuscar.text.toString()

            if (busqueda == "") {
                mostrarDatosEnListView()
            } else {
                mostrarPropietariosFiltro(busqueda,binding.SpConsultasPropietario.selectedItem.toString())
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mostrarDatosEnListView() {
        var listaFiltro = Propietario(requireContext()).mostrarTodos()
        var listaData = ArrayList<String>()
        var DataPropietario = Propietario(requireContext())

        listaIDs.clear()
        (0..listaFiltro.size-1).forEach {
            val persona = listaFiltro.get(it)
            DataPropietario.curp = persona.curp
            DataPropietario.nombre = persona.nombre
            DataPropietario.telefono = persona.telefono
            DataPropietario.edad = persona.edad

            listaData.add(DataPropietario.contenido())
            listaIDs.add(persona.curp)
        }

        binding.listaConsultaPropietario.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,listaData)
    }

    fun mostrarPropietariosFiltro(busqueda:String, filtro:String) {
        var listaFiltro = Propietario(requireContext()).mostrarFiltro(busqueda,filtro)
        var listaData = ArrayList<String>()
        var DataPropietario = Propietario(requireContext())

        listaIDs.clear()
        (0..listaFiltro.size-1).forEach {
            val persona = listaFiltro.get(it)
            DataPropietario.curp = persona.curp
            DataPropietario.nombre = persona.nombre
            DataPropietario.telefono = persona.telefono
            DataPropietario.edad = persona.edad

            listaData.add(DataPropietario.contenido())
            listaIDs.add(persona.curp)
        }

        binding.listaConsultaPropietario.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,listaData)
    }

}