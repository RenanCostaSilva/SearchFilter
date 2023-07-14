package br.com.renancsdev.search.adpter.holder

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.renancsdev.search.databinding.ItemPessoaBinding
import br.com.renancsdev.search.model.Pessoa


class ViewHolderPessoa(private var binding: ItemPessoaBinding): ViewHolder(binding.root) {

    fun bind(pessoa: Pessoa) {

        binding.tvNomePessoa.text = pessoa.nome
        binding.imgPessoa.setImageResource(pessoa.imagem)

    }


}