package br.com.renancsdev.search.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renancsdev.search.R
import br.com.renancsdev.search.adpter.recyclerview.RecyclerViewFiltroPessoa
import br.com.renancsdev.search.adpter.recyclerview.RecyclerViewPessoa
import br.com.renancsdev.search.databinding.ActivityMainBinding
import br.com.renancsdev.search.model.Pessoa
import br.com.renancsdev.search.ui.filtro.Filtro
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var context     = this@MainActivity
    private var layoutMain  = R.layout.activity_main

    private var usuarioEncontrado: ArrayList<Pessoa> = arrayListOf()
    private var queryEncontrado: String = ""

    private var pessoa: ArrayList<Pessoa> = arrayListOf()
    private var pessoaAdapter: RecyclerViewPessoa = RecyclerViewPessoa(pessoa)
    var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setarConfiguracaoInicial()

        init2()
        eventoBotoes()
    }

    // Configuracao Inicial
    private fun setarConfiguracaoInicial(){
        setarLayout()
        setarBinding()
    }
    private fun setarLayout(){
        setContentView(R.layout.activity_main)
    }
    private fun setarBinding(){
        binding = DataBindingUtil.setContentView(context , layoutMain)
    }
    fun eventoBotoes(){
       binding.btnComFiltro.setOnClickListener {
          startActivity(Intent(this@MainActivity , Filtro::class.java))
       }
    }

    private fun popularListPessoa(): List<Pessoa> {
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


    // Jeito 1
    private fun init1(){
        iniciarAdapter()
        procurarPessoa1()
    }
    private fun iniciarAdapter(){
        val layoutManager = LinearLayoutManager(this)
        pessoaAdapter = RecyclerViewPessoa(popularListPessoa()).also {
            binding.rvSearch.layoutManager = layoutManager
            binding.rvSearch.adapter = it
            binding.rvSearch.adapter!!.notifyDataSetChanged()
        }
    }
    private fun procurarPessoa1(){

        binding.searchProcurar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.

                filter(newText!!)
                return true
            }

        })
    }
    fun filter(nome: String){
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Pessoa> = ArrayList()

        // running a for loop to compare elements.
        for (item in popularListPessoa()) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.nome.lowercase().contains(nome.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.

                filteredlist.add(item)
            }
        }

        updateRecyclerView(filteredlist)
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        }
        updateRecyclerView(filteredlist)

    }
    private fun updateRecyclerView(pessoaLista: ArrayList<Pessoa>) {
        binding.rvSearch.apply {
            pessoaAdapter.pessoa = pessoaLista
            pessoaAdapter.notifyDataSetChanged()
        }
    }


    // Jeito 2
    private fun init2(){
        initAdapter()
        procurarPessoa2()
    }
    private fun initAdapter(){
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = RecyclerViewPessoa(popularListPessoa())
        }
    }
    private fun procurarPessoa2(){
        binding.searchProcurar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                procurarNaLista(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                procurarNaLista(newText)
                return true
            }

        })
    }
    private fun procurarNaLista(text: String?) {
        usuarioEncontrado = arrayListOf()

        popularListPessoa().forEach { item ->
            if(text?.let { item.nome.contains(it , true) } == true){

                usuarioEncontrado.add(item)
            }
        }
        update()
    }
    private fun update(){
        binding.rvSearch.apply {
            adapter = RecyclerViewPessoa(usuarioEncontrado)
        }
    }


    // Jeito 3
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