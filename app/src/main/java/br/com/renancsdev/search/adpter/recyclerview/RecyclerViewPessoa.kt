package br.com.renancsdev.search.adpter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renancsdev.search.adpter.holder.ViewHolderPessoa
import br.com.renancsdev.search.databinding.ActivityMainBinding
import br.com.renancsdev.search.databinding.ItemPessoaBinding
import br.com.renancsdev.search.model.Pessoa
import java.util.logging.Filter

class RecyclerViewPessoa(var pessoa: List<Pessoa>): RecyclerView.Adapter<ViewHolderPessoa>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPessoa {
        return ViewHolderPessoa(ItemPessoaBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }
    override fun getItemCount() = pessoa.size
    override fun onBindViewHolder(holder: ViewHolderPessoa, position: Int) = holder.bind(pessoa[position])

}