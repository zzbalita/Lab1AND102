package phcom.phlynk.lab1and102.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import phcom.phlynk.lab1and102.Product;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "AND102";
    private static final String TABLE_NAME = "SANPHAM";
    private static final int DB_VERSION = 1;
    private static final String ID_COL = "id";
    private static final String NAME_COL = "ten";
    private static final String PRICE_COL = "gia";
    private static final String QUATITY_COL = "soluong";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + PRICE_COL + " REAL, "
                + QUATITY_COL + " INTEGER)";
        db.execSQL(query);
    }

    public void addProduct(String productName, Float productPrice, Integer productQuatity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, productName);
        values.put(PRICE_COL, productPrice);
        values.put(QUATITY_COL, productQuatity);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setPrice(cursor.getFloat(2));
                product.setQuantity(cursor.getInt(3));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
