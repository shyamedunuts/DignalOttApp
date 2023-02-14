package com.dignalott.adapter


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dignalott.R
import com.dignalott.models.Movies


class MovieListAdapter(val movieList: Movies?,val context: Context,val listener:PagingListener) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    var movies=movieList?.page?.`content-items`?.content
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val movieName = view.findViewById<TextView>(R.id.id_movie_name)
        val movieImage = view.findViewById<ImageView>(R.id.id_movie_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(context).inflate(
            R.layout.item_card,
            parent,
            false
        )
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieName.text= movies!![position].name

        Glide.with(context)
            .load(Uri.parse("file:///android_asset/${movies!![position].`poster-image`}"))
            .into(holder.movieImage)
        if(position+1==movies?.size)
        {
            listener.startPaging()
        }
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    fun updateList(movieList: Movies) {
        movies=movieList.page.`content-items`.content
    }



}
//
interface PagingListener
{
    fun startPaging()

}