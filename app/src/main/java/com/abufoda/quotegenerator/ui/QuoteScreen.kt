package com.abufoda.quotegenerator.ui

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abufoda.quotegenerator.R
import com.abufoda.quotegenerator.model.remote.ServiceApi
import com.abufoda.quotegenerator.model.entity.MyDataItem
import com.abufoda.quotegenerator.databinding.FragmentQuoteScreenBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class QuoteScreen : Fragment() {

    private lateinit var binding : FragmentQuoteScreenBinding
    lateinit var viewModel: QuoteViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quote_screen,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(QuoteViewModel::class.java)
        getMyData()
        getCount()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCount()
        binding.goToFavorite.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_quoteScreen_to_favoriteScreen)
        )

        binding.generateBtn.setOnClickListener{
            binding.progressBar.visibility =View.VISIBLE
            getMyData()
            binding.progressBar.visibility = View.GONE
        }

        binding.favoriteBtn.setOnClickListener {
            insertQuote()
            Toast.makeText(context,"The Quote is add to Favorite",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_quoteScreen_to_favoriteScreen)
        }
    }

    private fun getMyData(){
        viewModel.getQuotesApi()
        viewModel.quoteApiLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.content.text = it[0].content
                binding.author.text = it[0].author
            }
        })
    }

    private fun insertQuote(){
        viewModel.getQuotesApi()
        viewModel.quoteApiLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.insertQuote(MyDataItem(it[0]._id,it[0].author,it[0].authorSlug,it[0].content
            ,it[0].dateAdded,it[0].dateModified,it[0].length))
        })
    }

    private fun getCount(){
        viewModel.getCount()
        viewModel.quoteLiveData.observe(viewLifecycleOwner, Observer {
            binding.numberOfQuotes.text = it.size.toString()
        })
    }



}