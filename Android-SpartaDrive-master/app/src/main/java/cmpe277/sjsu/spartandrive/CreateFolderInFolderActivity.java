package cmpe277.sjsu.spartandrive;
//DELETE THIS
/**
 * Created by namithashetty on 11/25/15.
 */

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

public class CreateFolderInFolderActivity extends ConnectionActivity {
    private static final String TAG = "PickFolderWithOpenerActivity";


    @Override
    public void onConnected(Bundle connectionHint) {
        //super.onConnected(connectionHint);
        Log.i(TAG, "Request code" + getIntent().getExtras().getString("folderName"));
        //Drive.DriveApi.fetchDriveId(getGoogleApiClient(), "0B8m6vocGpnl3VGVzelctREUxYlk")
          //      .setResultCallback(idCallback);
        String folderName = getIntent().getExtras().getString("folderName");
        DriveId driveId = Drive.DriveApi.getRootFolder(getGoogleApiClient()).getDriveId();
        DriveFolder folder = driveId.asDriveFolder();
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(folderName).build();
        folder.createFolder(getGoogleApiClient(), changeSet)
                .setResultCallback(createFolderCallback);
    }

    final ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            String folderName = getIntent().getExtras().getString("folderName");
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                    .setTitle(folderName).build();
            folder.createFolder(getGoogleApiClient(), changeSet)
                    .setResultCallback(createFolderCallback);
        }
    };

    final ResultCallback<DriveFolder.DriveFolderResult> createFolderCallback = new
            ResultCallback<DriveFolder.DriveFolderResult>() {

                @Override
                public void onResult(DriveFolder.DriveFolderResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while trying to create a folder");
                        return;
                    }
                    showMessage("Folder successfully created");
                }
            };
}
