package com.example.phanv.camera.View.SearchView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.SearchModel.GetCountSearchTask;
import com.example.phanv.camera.Model.SearchModel.GetListSearchProductTask;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ProductView.ListProductAdapter;

import java.util.List;

public class SearchProductActivity extends AppCompatActivity implements SearchInterface{
    private ListProductAdapter adapter;
    private String value;
    private TextView tvKey;
    private TextView tvSearchResult;
    private int number=10;
    private int countSearch;
    private GetCountSearchTask taskCount;
    private GetListSearchProductTask taskListSearch;
    private RecyclerView rcvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        tvKey=findViewById(R.id.tvViewKeySearch);
        tvSearchResult=findViewById(R.id.tvViewResult);
        rcvSearch = findViewById(R.id.rcvSearchResult);
        getKey();
        taskCount = new GetCountSearchTask(this);
        taskCount.execute(value);
        rcvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<=0)
                {
                    if(number<countSearch)
                    {
                        if(number+5<countSearch)
                        {
                            getListSearchProduct(number+5);
                            number=number+5;
                            tvSearchResult.setText("Hiện thị "+(number+5)+"/"+countSearch+" kết quả");
                        }
                        else {
                            getListSearchProduct(countSearchi);
                            tvSearchResult.setText("Hiện thị "+countSearch+"/"+countSearch+" kết quả");
                        }
                    }
                }
            }
        });
    }

    private void getKey() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        value=bundle.getString("value");
        tvKey.setText("Kết quả tìm kiếm [ "+value+" ]" );
    }

    @Override
    public void getCountSearchSuccess(int count) {
        countSearch=count;
        if(count<number){
            tvSearchResult.setText("Hiện thị "+count+"/"+count+" kết quả");
            getListSearchProduct(count);
        }
        else {
            tvSearchResult.setText("Hiện thị "+number+"/"+count+" kết quả");
            getListSearchProduct(number);
        }
    }

    private void getListSearchProduct(int count) {
        taskListSearch = new GetListSearchProductTask(this,this);
        taskListSearch.execute(count+"",value);
    }

    @Override
    public void getListSearchSuccess(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 1, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvSearch.setLayoutManager(layoutManager);
        rcvSearch.setAdapter(adapter);
    }
}
