package phcom.phlynk.lab1and102;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import phcom.phlynk.lab1and102.database.DbHandler;

public class MainActivity extends AppCompatActivity {
    private EditText productNameEdt, productPriceEdt, productQuatityEdt;
    private ListView lvProduct;
    private Button addProduct;
    private DbHandler dbHandler;
    private phcom.phlynk.lab1and102.ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEdt = findViewById(R.id.edtName);
        productPriceEdt = findViewById(R.id.edtPrice);
        productQuatityEdt = findViewById(R.id.edtQuatity);
        addProduct = findViewById(R.id.btnAdd);
        lvProduct = findViewById(R.id.lvProducts);

        dbHandler = new DbHandler(MainActivity.this);
        loadProducts();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productQuatity = productQuatityEdt.getText().toString();

                if (productName.isEmpty() && productPrice.isEmpty() && productQuatity.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nhap day du cac truong du lieu", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHandler.addProduct(productName, Float.valueOf(productPrice), Integer.valueOf(productQuatity));
                loadProducts();
            }
        });
    }
    private void loadProducts() {
        List<Product> products = dbHandler.getAllProducts();
        productAdapter = new phcom.phlynk.lab1and102.ProductAdapter(this, products);
        lvProduct.setAdapter(productAdapter);
    }


}