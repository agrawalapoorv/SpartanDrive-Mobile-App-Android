package cmpe277.sjsu.spartandrive;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

/**
 * Created by namithashetty on 12/2/15.
 */
public class SearchFileBasedOnTitleActivity extends ConnectionActivity {
    private GridView mViewListView;
    private ViewAdapter mViewAdapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        mViewListView = (GridView) findViewById(R.id.listItems);
        mViewAdapter = new ViewAdapter(this);
        mViewListView.setAdapter(mViewAdapter);
    }

    /**
     * Clears the result buffer to avoid memory leaks as soon as the activity is no longer
     * visible by the user.
     */
    @Override
    protected void onStop() {
        super.onStop();
        mViewAdapter.clear();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Query query = new Query.Builder()
                .addFilter(Filters.contains(SearchableField.TITLE, "a"))
                .build();
        Drive.DriveApi.query(getGoogleApiClient(), query)
                .setResultCallback(metadataCallback);
    }

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataCallback =
            new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving results");
                        return;
                    }
                    mViewAdapter.clear();
                    mViewAdapter.append(result.getMetadataBuffer());
                }
            };

}
