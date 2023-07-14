package br.com.renancsdev.search.adpter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.com.renancsdev.search.adpter.holder.ViewHolderFiltroPessoa
import br.com.renancsdev.search.databinding.ItemPessoaBinding
import br.com.renancsdev.search.model.Pessoa
import java.util.ArrayList

class RecyclerViewFiltroPessoa(var pessoa: List<Pessoa>, var query: String): RecyclerView.Adapter<ViewHolderFiltroPessoa>(),
    Filterable {

    var pessoaFilterList = pessoa

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFiltroPessoa {
        return ViewHolderFiltroPessoa(ItemPessoaBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }
    override fun getItemCount() = pessoaFilterList.size
    override fun onBindViewHolder(holder: ViewHolderFiltroPessoa, position: Int){
        holder.bind(pessoaFilterList[position],query)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                if(charSequence!!.isEmpty()){
                    pessoaFilterList = pessoa
                }
                else{
                    val resultList = ArrayList<Pessoa>()
                    pessoa.forEach {
                        if (it.nome.lowercase().contains(charSequence.toString().lowercase())) {
                            resultList.add(it)
                        }
                    }
                    pessoaFilterList = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = pessoaFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {

                if(results != null && results.count > 0){
                    pessoaFilterList = results.values as ArrayList<Pessoa>
                    notifyDataSetChanged()
                }

            }
        }
    }

}