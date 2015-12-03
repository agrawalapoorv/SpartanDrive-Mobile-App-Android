package cmpe277.sjsu.spartandrive;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import android.os.AsyncTask.Status;
import android.widget.GridView;

import com.google.android.gms.common.api.PendingResult;

/**
 * Created by namithashetty on 12/2/15.
 */
public class DeleteFolderActivity extends ConnectionActivity {

    private static final String TAG = "ConnectionDriveActivity";

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        String driveId = getIntent().getExtras().getString("driveId");
        Log.i(TAG, "Selected item:" + driveId);
        DriveId drive = DriveId.decodeFromString(driveId);
        DriveFolder folder = drive.asDriveFolder();
        folder.delete(getGoogleApiClient());

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        //Drive.DriveApi.getRootFolder(getGoogleApiClient()).listChildren(getGoogleApiClient()).setResultCallback(metadataResult);

        //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
                //.setResultCallback(idCallback1);

    }
       /* DriveFolder driveFolder = Drive.DriveApi.getFolder(getGoogleApiClient(),
                DriveId.decodeFromString("DriveId:0B8m6vocGpnl3SUZ0OU5JM08tdjg"));
        // Call to delete folder.
        driveFolder.delete(getGoogleApiClient());
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
                .setResultCallback(idCallback);
    }*/


   /* final private ResultCallback<DriveApi.DriveIdResult> idCallback1 = new ResultCallback<DriveApi.DriveIdResult>() {
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

    /*final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
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
}
