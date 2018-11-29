package ca.lambton.allan.xlambton.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.lambton.allan.xlambton.R;

public class SendPhotoAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> photoList;

    public SendPhotoAdapter(Context context, List<String> photoList) {
        super(context, R.layout.grid_photo, photoList);
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // set grid item
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.grid_photo, parent, false);
        }

        // title
        TextView title = view.findViewById(R.id.item_title);
        title.setText("Photo " + (position + 1));

        // get image
        ImageView image = view.findViewById(R.id.item_photo);
        String photoPath = photoList.get(position);
        if (!photoPath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            Bitmap lowdefbitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

            image.setImageBitmap(lowdefbitmap);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;

    }
}
