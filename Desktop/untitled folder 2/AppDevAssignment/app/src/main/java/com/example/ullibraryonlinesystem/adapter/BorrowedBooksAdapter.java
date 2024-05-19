package com.example.ullibraryonlinesystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ullibraryonlinesystem.BorrowedBook;
import com.example.ullibraryonlinesystem.R;

import java.util.List;

public class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.BookViewHolder> {
    private List<BorrowedBook> borrowedBooksList;

    public BorrowedBooksAdapter(List<BorrowedBook> borrowedBooksList) {
        this.borrowedBooksList = borrowedBooksList;
    }

    @NonNull
    @Override
    public BorrowedBooksAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // need to create and return a new BookViewHolder instance
        // involves loading the layout file and passing it to the ViewHolder constructor
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_my_borrowed_books, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBooksAdapter.BookViewHolder holder, int position) {
        // need to bind the data to the ViewHolder view
        BorrowedBook book = borrowedBooksList.get(position);
        holder.titleTextView.setText(book.getTitle());
        // bind other data...
    }

    @Override
    public int getItemCount() {
        return borrowedBooksList.size();
    }

    // inner class defines a ViewHolder
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView; // Suppose have a title view

        public BookViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView); // The ID defined in item_book.xml is used here
            // Initialize other views...
        }
    }
}


