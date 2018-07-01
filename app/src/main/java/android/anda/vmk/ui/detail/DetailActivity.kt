package android.anda.vmk.ui.detail

import android.anda.view_model_kotlin.R
import android.anda.vmk.ui.main.HomeActivity
import android.anda.vmk.utilities.InjectorUtil
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : LifecycleActivity() {

    companion object {
        const val TAG = "DetailActivity"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent!!.getStringExtra(HomeActivity.EXTRA_ID)
        initViewModel(id)
    }

    private fun initViewModel(id:String) {
        val factory = InjectorUtil.provideDetailViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)

        viewModel.loadData(id)

        viewModel.errorLiveData.observe(this, Observer { message ->
            Toast.makeText(applicationContext, "failed load movie info", Toast.LENGTH_SHORT).show()
            Log.d(TAG, message)
            progress_bar.visibility= View.GONE
            finish()
        })

        viewModel.movieDetailLiveData.observe(this, Observer{ movie->
            progress_bar.visibility= View.GONE
            title_movie.text= movie!!.title
            plot_movie.text=movie!!.plot
            Picasso.with(poster_movie.context)
                    .load(movie.poster).fit()
                    .into(poster_movie)
            genre_movie.text  = "${movie.genre}"
            imdb_ratting.text = "IMDB : ${movie.imdbRating}"
            metascore.text = "Metascroe : ${movie.metascore}"
            actors_movie.text = movie.actors
        })
    }

}
