package com.abufoda.quotegenerator.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abufoda.quotegenerator.R
import com.abufoda.quotegenerator.adapter.FavoriteQuoteRecyclerView
import com.abufoda.quotegenerator.adapter.OnClick
import com.abufoda.quotegenerator.databinding.FragmentFavoriteScreenBinding
import com.abufoda.quotegenerator.model.entity.MyDataItem
import com.abufoda.quotegenerator.model.local.Database
import com.abufoda.quotegenerator.model.local.LocalRepositoryImp
import kotlinx.coroutines.*
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList


class FavoriteScreen : Fragment(), OnClick {
    private lateinit var binding : FragmentFavoriteScreenBinding
    lateinit var quoteList : List<MyDataItem>

    val favoriteQuoteRecyclerView: FavoriteQuoteRecyclerView by lazy {
        FavoriteQuoteRecyclerView()
    }

    lateinit var viewModel: QuoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_favorite_screen,
                container,
                false
            )

            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(QuoteViewModel::class.java)

        binding.recycler.adapter = favoriteQuoteRecyclerView
        getQuotes()

        binding.backToHome.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_favoriteScreen_to_quoteScreen)
        )
        favoriteQuoteRecyclerView.onClick = this

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getFilterQuote(newText)
                return true
            }
        })


    }

     private fun getQuotes(){
         viewModel.getQuotes()
         viewModel.quoteLiveData.observe(viewLifecycleOwner, Observer {
                 favoriteQuoteRecyclerView.setList(it)
         })
     }


    override fun onClick(quote: MyDataItem) {
        viewModel.deleteQuote(quote)
        Toast.makeText(context,"The Quote is remove from favorite", Toast.LENGTH_SHORT).show()
        getQuotes()
    }


    private fun getFilterQuote(query: String?){
        viewModel.getQuotes()
        viewModel.quoteLiveData.observe(viewLifecycleOwner, Observer {
          if (query != null){
              val filteredList = ArrayList<MyDataItem>()
            for (i in it){
                if (i.content.lowercase(Locale.ROOT).contains(query) || i.content.uppercase(Locale.ROOT).contains(query) ){
                    filteredList.add(i)
                    favoriteQuoteRecyclerView.setFilteredList(filteredList)
                }else if (i.author.lowercase(Locale.ROOT).contains(query) || i.author.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                    favoriteQuoteRecyclerView.setFilteredList(filteredList)
                } else{
                    favoriteQuoteRecyclerView.setFilteredList(filteredList)
                }
            }
          }
        })
    }

}
