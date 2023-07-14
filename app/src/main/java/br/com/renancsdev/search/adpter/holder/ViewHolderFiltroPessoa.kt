package br.com.renancsdev.search.adpter.holder

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.recyclerview.widget.RecyclerView
import br.com.renancsdev.search.databinding.ItemPessoaBinding
import br.com.renancsdev.search.model.Pessoa


class ViewHolderFiltroPessoa(private var binding: ItemPessoaBinding): RecyclerView.ViewHolder(binding.root){

    private var COLORMAIN = Color.parseColor("#5B00FD")

    fun bind(pessoa: Pessoa , queryText: String) {
        iniciarHolder(pessoa , queryText)
    }

    private fun textoParaSpannable(texto: String, posicaoInicial: Int, posicaoFinal: Int): SpannableStringBuilder {
        //SPAN_EXCLUSIVE - https://blog.mindorks.com/spannable-string-text-styling-with-spans/
        // Tipos - https://www.thiengo.com.br/como-utilizar-spannable-no-android-para-customizar-strings

        // Span to make text bold
        val corTexto      = setarColorPadraoSpannable()
        val fonteNegrito  = StyleSpan(Typeface.BOLD)
        val textoSize     = RelativeSizeSpan(1.2f)

        val sb = SpannableStringBuilder(texto)
        sb.setSpan(corTexto     , posicaoInicial, posicaoFinal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(fonteNegrito , posicaoInicial, posicaoFinal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(textoSize    , posicaoInicial, posicaoFinal, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        return sb
    }
    private fun setarColorPadraoSpannable(): ForegroundColorSpan {
        return ForegroundColorSpan(COLORMAIN)
    }
    private fun textoParaHtml(texto: String): Spanned {

        val text = "<span style=\"color: #5B00FD; \">${texto}</span>"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(text)
        }
    }

    //init
    private fun iniciarHolder(pessoa: Pessoa , queryText: String){
        checarSeQueryPesquisaExiste(queryText , pessoa)

        val startPos = pegarPosicaoInicialQuery(pessoa.nome , queryText)

        val endPos = pegarPosicaoFinalQuery(startPos , queryText)

        verificasePosicaoQueryMenos1(startPos , pessoa)

        destacarAte3PrimeirosCaract(startPos , endPos , pessoa)

        binding.imgPessoa.setImageResource(pessoa.imagem)
    }

    // validacao
    private fun checarSeQueryPesquisaExiste(queryText: String , pessoa: Pessoa): Boolean{
        return if(queryText.isNotEmpty()) {
            true
        }else{
            binding.tvNomePessoa.text = pessoa.nome
            false
        }
    }
    private fun verificasePosicaoQueryMenos1(startPos: Int, pessoa: Pessoa): Boolean{
        return if (startPos != -1)  {
            true
        }else{
            binding.tvNomePessoa.text = pessoa.nome
            false
        }
    }
    private fun destacarAte3PrimeirosCaract(startPos: Int, endPos: Int, pessoa: Pessoa): Boolean {
        return if (endPos < 3)  {
            binding.tvNomePessoa.text = textoParaSpannable(pessoa.nome , startPos , endPos)
            true
        }else{
            binding.tvNomePessoa.text = pessoa.nome
            false
        }
    }

    // posicoes
    private fun pegarPosicaoInicialQuery(nomePessoa: String , queryText: String) = nomePessoa.lowercase().indexOf(queryText.lowercase())
    fun pegarPosicaoFinalQuery(startPos: Int , queryPesquisa: String) = startPos + queryPesquisa.length

}