package com.example.phanv.camera.Model.ProductModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by phanv on 18-Dec-17.
 */

public class GetLastProductAddedTask extends AsyncTask<Void, Product, Product> {
    ProductPresenter presenter = new ProductPresenter();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    protected Product doInBackground(Void... voids) {
        Product product = presenter.getLastProductAdded();
        return product;
    }

    @Override
    protected void onPostExecute(Product product) {
        if (product != null) {
            reference.child("camera").child("product").setValue(product);
        }
        super.onPostExecute(product);
    }
}
