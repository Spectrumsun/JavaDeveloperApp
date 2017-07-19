package spectrumsun.com.mymy.adapter;

/**
 * Created by Spectrum Sun on 07/16/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import spectrumsun.com.mymy.R;
import spectrumsun.com.mymy.app.AppController;
import spectrumsun.com.mymy.model.Gitapi;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Gitapi> gitItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Gitapi> gitItems) {
        this.activity = activity;
        this.gitItems = gitItems;
    }

    @Override
    public int getCount() {
        return gitItems.size();
    }

    @Override
    public Object getItem(int location) {
        return gitItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView user_link = (TextView) convertView.findViewById(R.id.user_link);
        TextView score = (TextView) convertView.findViewById(R.id.score);


        // getting movie data for the row
        Gitapi m = gitItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        //html link
        user_link.setText(m.getUserUrl());

        //score
        score.setText("Score  " + m.getScore());



        return convertView;
    }

}
