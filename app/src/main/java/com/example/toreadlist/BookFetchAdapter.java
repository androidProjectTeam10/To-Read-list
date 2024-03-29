package com.example.toreadlist;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

import static com.example.toreadlist.BookValues.*;

public class BookFetchAdapter extends RecyclerView.Adapter<BookFetchViewHolder>{

    private ArrayList<BookFetch> bookFetchArrayList;
    private Context appContext;

    public BookFetchAdapter(ArrayList<BookFetch> bookFetchArrayList, Context appContext) {
        this.bookFetchArrayList = bookFetchArrayList;
        this.appContext = appContext;
    }

    @NonNull
    @Override
    public BookFetchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fetch_item, parent, false);
        return new BookFetchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookFetchViewHolder holder, int position) {


        BookFetch fetchedBook = bookFetchArrayList.get(position);
        holder.titleTV.setText(fetchedBook.getTitle());
        holder.publisherTV.setText(fetchedBook.getPublisher());
        holder.pageCountTV.setText("# of Pages : " + fetchedBook.getPageCount());
        holder.averageRatingTV.setText("Average rating: " + fetchedBook.getAverageRating().toString() + "/5");
        if(Double.isNaN(fetchedBook.getAverageRating())) {
            holder.averageRatingTV.setText("Average rating: Not rated");
        }
        holder.dateTV.setText(fetchedBook.getPublishedDate());

        try{
            holder.languageTV.setText(language_codes.get(fetchedBook.getLanguage()));
        }
        catch(Exception ex)
        {
            holder.languageTV.setText("Undefined");
        }


        if(fetchedBook.getImageUrl() != null && fetchedBook.getImageUrl() != "")
            Picasso.get().load(fetchedBook.getImageUrl()).into(holder.bookIV);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(appContext, BookFetchDetails.class);
                i.putExtra("title", fetchedBook.getTitle());
                i.putExtra("authors", fetchedBook.getAuthors());

                try{
                    i.putExtra("language", language_codes.get(fetchedBook.getLanguage()));
                }
                catch(Exception ex)
                {
                    i.putExtra("language", "Undefined.");
                }


                i.putExtra("averageRating", fetchedBook.getAverageRating().toString());
                i.putExtra("publisher", fetchedBook.getPublisher());
                i.putExtra("publishedDate", fetchedBook.getPublishedDate());
                i.putExtra("description", fetchedBook.getDescription());
                i.putExtra("pageCount", fetchedBook.getPageCount());
                i.putExtra("imageUrl", fetchedBook.getImageUrl());
                i.putExtra("bookDetailsLink", fetchedBook.getBookDetailsLink());
                i.putExtra("buyLink", fetchedBook.getBuyLink());

                i.putExtra("averageRating","Average rating: " + fetchedBook.getAverageRating().toString() + "/5");
                if(Double.isNaN(fetchedBook.getAverageRating())) {
                    i.putExtra("averageRating","Average rating: Not rated");
                }


                appContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookFetchArrayList.size();
    }
}

