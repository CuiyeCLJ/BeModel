package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.adapter.ReviewAdapter;
import android.bemodel.com.bemodel.db.CommentInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private List<CommentInfo> reviewInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initReview();
        ReviewAdapter reviewAdapter = new ReviewAdapter(ReviewActivity.this, R.layout.review_item, reviewInfoList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(reviewAdapter);
    }

    private void initReview() {

    }
}
