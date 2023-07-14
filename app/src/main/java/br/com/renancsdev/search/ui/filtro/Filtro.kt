package br.com.renancsdev.search.ui.filtro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renancsdev.search.R
import br.com.renancsdev.search.adpter.recyclerview.RecyclerViewFiltroPessoa
import br.com.renancsdev.search.databinding.ActivityFiltroBinding
import br.com.renancsdev.search.databinding.ActivityMainBinding
import br.com.renancsdev.search.model.Pessoa
import java.util.ArrayList

class Filtro : AppCompatActivity() {

    private lateinit var binding: ActivityFiltroBinding
    private var context     = this@Filtro
    private var layoutFiltro  = R.layout.activity_filtro

    private var usuarioEncontrado: ArrayList<Pessoa> = arrayListOf()
    var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setarConfiguracaoInicial()

        initFilter()
    }

    // Configuracao Inicial
    private fun setarConfiguracaoInicial(){
        setarLayout()
        setarBinding()
    }
    private fun setarLayout(){
        setContentView(layoutFiltro)
    }
    private fun setarBinding(){
        binding = DataBindingUtil.setContentView(context , layoutFiltro)
    }

    // Filtro
    private fun initFilter(){
        initAdapterFiltro()
        procurarPessoaFiltro()
    }
    private fun initAdapterFiltro(){
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this.context)
            //Highlight
            adapter = RecyclerViewFiltroPessoa(popularListPessoaFiltro() , query)
        }
    }
    private fun procurarPessoaFiltro(){
        binding.searchProcurar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                procurarNaListaFiltro(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                procurarNaListaFiltro(newText)
                return true
            }
        })
    }
    private fun procurarNaListaFiltro(text: String?) {
        usuarioEncontrado = arrayListOf()

        popularListPessoaFiltro().forEach { item ->
            if(text?.let { item.nome.contains(it , true) } == true){

                //Highlight
                query = text
                usuarioEncontrado.add(item)
            }
        }
        //Highlight
        RecyclerViewFiltroPessoa(popularListPessoaFiltro(),query).filter.filter(text)
        updateFiltro()
    }
    private fun updateFiltro(){
        binding.rvSearch.apply {
            //Highlight
            adapter = RecyclerViewFiltroPessoa(usuarioEncontrado , query)
        }
    }
    private fun popularListPessoaFiltro(): List<Pessoa> {
        return listOf(
            Pessoa("Renan1", R.drawable.games),
            Pessoa("Foeba", R.drawable.games),
            Pessoa("Cayti", R.drawable.games),
            Pessoa("Nuro", R.drawable.games),
            Pessoa("Buynis", R.drawable.games),
            Pessoa("Rauoll", R.drawable.games),
            Pessoa("Bezon", R.drawable.games),
            Pessoa("Dakil", R.drawable.games),
            Pessoa("Foise", R.drawable.games),
            Pessoa("Voyn", R.drawable.games),
            Pessoa("Vucubas", R.drawable.games),
            Pessoa("Daewi", R.drawable.games),
            Pessoa("Tuenn", R.drawable.games),
            Pessoa("Gaze", R.drawable.games),

            )
    }
}