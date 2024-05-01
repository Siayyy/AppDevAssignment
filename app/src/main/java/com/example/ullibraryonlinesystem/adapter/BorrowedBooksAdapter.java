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
        // 这里需要创建并返回一个新的 BookViewHolder 实例
        // 通常包括加载布局文件并传递给 ViewHolder 构造函数
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_my_borrowed_books, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBooksAdapter.BookViewHolder holder, int position) {
        // 这里需要绑定数据到 ViewHolder 的视图
        BorrowedBook book = borrowedBooksList.get(position);
        holder.titleTextView.setText(book.getTitle());
        // 绑定其他数据...
    }

    @Override
    public int getItemCount() {
        return borrowedBooksList.size();
    }

    // 内部类定义 ViewHolder
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView; // 假设您有一个标题视图

        public BookViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView); // 这里使用您 item_book.xml 中定义的 ID
            // 初始化其他视图...
        }
    }
}


