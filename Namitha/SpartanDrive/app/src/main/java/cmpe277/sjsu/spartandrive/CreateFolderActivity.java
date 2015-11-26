package cmpe277.sjsu.spartandrive;

import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveFolder.DriveFolderResult;
import com.google.android.gms.drive.MetadataChangeSet;

/**
 * Created by namithashetty on 11/23/15.
 */
public class CreateFolderActivity extends ConnectionActivity {

    private static final String TAG = "PickFolderWithOpenerActivity";

    private static final int REQUEST_CODE_OPENER = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder().setTitle("Namitha1").build();
        Drive.DriveApi.getRootFolder(getGoogleApiClient()).createFolder(
                getGoogleApiClient(), changeSet).setResultCallback(callback);
    }

    final ResultCallback<DriveFolderResult> callback = new ResultCallback<DriveFolderResult>() {
        @Override
        public void onResult(DriveFolderResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Error while trying to create the folder");
                return;
            }
            showMessage("Created a folder: " + result.getDriveFolder().getDriveId());
        }
    };
}
