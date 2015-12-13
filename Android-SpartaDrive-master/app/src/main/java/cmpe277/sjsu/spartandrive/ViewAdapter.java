package cmpe277.sjsu.spartandrive;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.widget.DataBufferAdapter;

/**
 * Created by namithashetty on 11/23/15.
 */
public class ViewAdapter extends DataBufferAdapter<Metadata> {
    private static final String TAG = "ConnectionDriveActivity";

    int id = 0;
    String fileExt;
    public ViewAdapter(Context context) {
        super(context, R.layout.grid_view);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(),
                    R.layout.grid_view, null);
        }

        //View rowView = convertView.inflate(getContext(),R.layout.grid_view, parent);
        Metadata metadata = getItem(position);
        //Log.i(TAG, "Request code" + position);

        if (metadata.isFolder()) {
            TextView textView = (TextView) convertView.findViewById(R.id.label);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
            textView.setText(metadata.getTitle());
            imageView.setImageResource(R.drawable.ic_action_name1);
        }
        if (!metadata.isFolder()) {
            fileExt = metadata.getFileExtension();
            Log.i(TAG, "Request code" + fileExt);
            if(fileExt.equals("txt")) {
                TextView textView = (TextView) convertView.findViewById(R.id.label);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
                textView.setText(metadata.getTitle());
                imageView.setImageResource(R.drawable.txt_file);
            }
            else if(fileExt.equals("pdf")) {
                TextView textView = (TextView) convertView.findViewById(R.id.label);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
                textView.setText(metadata.getTitle());
                imageView.setImageResource(R.drawable.pdf_file);
            }
            else {
                TextView textView = (TextView) convertView.findViewById(R.id.label);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
                textView.setText(metadata.getTitle());
                imageView.setImageResource(R.drawable.default_file);
            }
        }
            return convertView;
    }
}
