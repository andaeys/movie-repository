package android.anda.vmk.ui.main

import android.anda.view_model_kotlin.R
import android.anda.vmk.data.model.MovieItem
import android.anda.vmk.ui.detail.DetailActivity
import android.anda.vmk.utilities.EndlessScrollListener
import android.anda.vmk.utilities.InjectorUtil
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_content.view.*

class HomeActivity : LifecycleActivity(), SearchView.OnQueryTextListener {
    companion object {
        const val INITIAL_QUERY = "dawn"
        const val EXTRA_ID = "imdb_id"
    }
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: MovieAdapter

    private var query = INITIAL_QUERY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerview.layoutManager = layoutManager
        adapter = MovieAdapter(ArrayList())
        recyclerview.adapter = adapter

        search_bar.setOnQueryTextListener(this)

        val scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadData(query, page+1)
            }
        }
        recyclerview.addOnScrollListener(scrollListener)

        initViewModel()
    }

    private fun initViewModel() {
        val factory = InjectorUtil.provideHomeViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.loadData(query, 1)

        viewModel.listItemLiveData.observe(this, Observer{list ->
            tv_data.visibility = View.GONE
            adapter!!.movieList.addAll(list!!)
            adapter!!.notifyDataSetChanged()
            progress_bar.visibility = View.GONE
        })

        viewModel.messageLiveData.observe(this, Observer { message ->
            if(adapter.movieList!!.isEmpty()){
                tv_data.visibility = View.VISIBLE
                tv_data.text = "Not Found"
            }else{
                tv_data.visibility = View.GONE
            }
            progress_bar.visibility = View.GONE
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        progress_bar.visibility = View.VISIBLE
        tv_data.visibility = View.GONE
        this.query = query!!
        adapter.movieList.clear()
        adapter.notifyDataSetChanged()
        viewModel.loadData(this.query, 1)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private inner class MovieAdapter(
            var movieList : MutableList<MovieItem>) :
            RecyclerView.Adapter<MovieAdapter.ViewHolder> () {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.item_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val movie = movieList[position]
            holder.title.text = movie.title
            holder.year.text = movie.year
            Picasso.with(holder.img.context)
                    .load(movie.posterUrl).fit()
                    .placeholder(R.mipmap.ic_movie)
                    .into(holder.img)
            
        }

        override fun getItemCount(): Int {
            return movieList.size
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var title = itemView.card_title!!
            var year = itemView.card_year!!
            var img = itemView.card_image!!
            init {
                itemView.setOnClickListener {
                    openDetailItem(adapterPosition)
                }
            }
        }

        private fun openDetailItem(position: Int) {
            val movie = movieList[position]
            val intentMovie = Intent(applicationContext, DetailActivity::class.java)
            intentMovie.putExtra(EXTRA_ID, movie.imdbID)
            startActivity(intentMovie)
        }

    }

}
