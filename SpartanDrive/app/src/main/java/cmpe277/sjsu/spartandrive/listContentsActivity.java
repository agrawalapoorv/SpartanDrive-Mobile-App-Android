package cmpe277.sjsu.spartandrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveFolder;
import android.util.Log;
/**
 * Created by namithashetty on 11/26/15.
 */
public class listContentsActivity extends ConnectionActivity {

    private GridView mResultsListView;
    private ViewAdapter mResultsAdapter;
    private GridView mListViewSamples;
    private static final String TAG = "ConnectionDriveActivity";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mResultsListView = (GridView) findViewById(R.id.listItems);
        mResultsAdapter = new ViewAdapter(this);
        mResultsListView.setAdapter(mResultsAdapter);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),  CustomFolderNameActivity.class);
                startActivity(intent);
            }
        });*/

        //Code to handle onClick for CreateFolder button
       /* final Button button = (Button) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(),  CreateFolderInFolderActivity.class);
                startActivity(intent);
            }
        });
*/
        /*mListViewSamples = (ListView) findViewById(R.id.listItems);
        mListViewSamples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
                Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3TVh6bnJ0eUxJYlU")
                      .setResultCallback(idCallback);

            }
        });*/
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);
        String driveId = getIntent().getExtras().getString("driveId");
        Log.i(TAG, "Selected item:" + driveId);
        DriveId drive = DriveId.decodeFromString(driveId);
        DriveFolder folder = drive.asDriveFolder();
        folder.listChildren(getGoogleApiClient())
                .setResultCallback(metadataResult);
        //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
          //      .setResultCallback(idCallback);
    }

    /*final private ResultCallback<DriveApi.DriveContentsResult> idCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
        @Override
        public void onResult(DriveApi.DriveContentsResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveContents driveContent = result.getDriveContents();
            Log.i(TAG, "Request code" + driveContent);

            DriveId driveId = driveContent.getDriveId();
            Log.i(TAG, "Request code" + driveId);
            DriveFolder folder = driveId.asDriveFolder();
            folder.listChildren(getGoogleApiClient())
                    .setResultCallback(metadataResult);
        }
    };

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving files");
                        return;
                    }
                    mResultsAdapter.clear();
                    mResultsAdapter.append(result.getMetadataBuffer());
                    showMessage("Successfully listed files.");
                }
            };*/

    /*final private ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            folder.listChildren(getGoogleApiClient())
                    .setResultCallback(metadataResult);
        }
    };*/

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while retrieving files");
                        return;
                    }
                    mResultsAdapter.clear();
                    mResultsAdapter.append(result.getMetadataBuffer());
                    showMessage("Successfully listed files.");
                }
            };
}
