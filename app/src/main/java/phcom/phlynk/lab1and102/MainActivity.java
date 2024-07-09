package phcom.phlynk.lab1and102;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import phcom.phlynk.lab1and102.database.DbHandler;

public class MainActivity extends AppCompatActivity implements phcom.phlynk.lab1and102.ProductRecyclerViewAdapter.OnProductClickListener {
    private EditText productNameEdt, productPriceEdt, productQuatityEdt;
    private Button addProductBtn;
    private RecyclerView recyclerView;
    private DbHandler dbHandler;
    private phcom.phlynk.lab1and102.ProductRecyclerViewAdapter productAdapter;
    private List<Product> productList;
    private int selectedProductId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEdt = findViewById(R.id.edtName);
        productPriceEdt = findViewById(R.id.edtPrice);
        productQuatityEdt = findViewById(R.id.edtQuatity);
        addProductBtn = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        dbHandler = new DbHandler(this);
        productList = dbHandler.getAllProducts();
        productAdapter = new phcom.phlynk.lab1and102.ProductRecyclerViewAdapter(this, productList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productQuatity = productQuatityEdt.getText().toString();

                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productPrice) || TextUtils.isEmpty(productQuatity)) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedProductId == -1) {
                    dbHandler.addProduct(productName, Float.valueOf(productPrice), Integer.valueOf(productQuatity));
                } else {
                    dbHandler.updateProduct(selectedProductId, productName, Float.valueOf(productPrice), Integer.valueOf(productQuatity));
                    selectedProductId = -1;
                    addProductBtn.setText("Add Product");
                }

                loadProducts();
            }
        });
    }

    private void loadProducts() {
        productList.clear();
        productList.addAll(dbHandler.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(int position) {
        Product product = productList.get(position);
        productNameEdt.setText(product.getName());
        productPriceEdt.setText(String.valueOf(product.getPrice()));
        productQuatityEdt.setText(String.valueOf(product.getQuantity()));
        selectedProductId = product.getId();
        addProductBtn.setText("Update Product");
    }

    @Override
    public void onDeleteClick(int position) {
        Product product = productList.get(position);
        dbHandler.deleteProduct(product.getId());
        loadProducts();
    }
}
