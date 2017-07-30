package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.adapter.CommentAdapter;
import android.bemodel.com.bemodel.db.CommentInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private List<CommentInfo> commentInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initReview();
        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, R.layout.comment_item, commentInfoList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(commentAdapter);
    }

    private void initReview() {

    }
}
