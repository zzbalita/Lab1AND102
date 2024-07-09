package phcom.phlynk.lab1and102.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import phcom.phlynk.lab1and102.Product;
import phcom.phlynk.lab1and102.R;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;
    private OnProductClickListener onProductClickListener;

    public ProductRecycleViewAdapter(Context context, List<Product> productList, OnProductClickListener onProductClickListener) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chi_tiet_san_pham, parent, false);
        return new ViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName, productPrice, productQuantity;
        Button editButton, deleteButton;
        OnProductClickListener onProductClickListener;

        public ViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnDelete);

            this.onProductClickListener = onProductClickListener;
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnEdit) {
                onProductClickListener.onEditClick(getAdapterPosition());
            } else if (v.getId() == R.id.btnDelete) {
                onProductClickListener.onDeleteClick(getAdapterPosition());
            }
        }
    }

    public interface OnProductClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
