package com.example.android.persistence.codelab.step5_solution.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.android.support.lifecycle.AndroidViewModel;
import com.android.support.lifecycle.LiveData;
import com.example.android.persistence.codelab.step5_solution.business.MyAppLogic;
import com.example.android.persistence.codelab.step5_solution.db.MyDatabase;
import com.example.android.persistence.codelab.step5_solution.entity.MyComment;
import com.example.android.persistence.codelab.step5_solution.entity.MyProduct;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<MyProduct> mProduct = new LiveData<>();

    private LiveData<List<MyComment>> mComments = new LiveData<>();

    private int productId;

    public ProductViewModel(Application application) {
        super(application);
    }

    public void setProductId(int productId) {
        if (this.productId == productId) {
            return;
        }
        this.productId = productId;
        MyAppLogic.getInstance(this.getApplication()).fetchComments(productId);
        MyDatabase db = MyDatabase.getInstance(this.getApplication());
        this.mProduct = db.productDao().loadProduct(productId);
        this.mComments = db.commentDao().loadComments(productId);
    }

    @NonNull
    public LiveData<MyProduct> getProduct() {
        return mProduct;
    }

    @NonNull
    public LiveData<List<MyComment>> getComments() {
        return mComments;
    }
}
