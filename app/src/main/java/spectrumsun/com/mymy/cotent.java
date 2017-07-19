package spectrumsun.com.mymy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class cotent extends AppCompatActivity {
    TextView textView, profile;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotent);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        profile = (TextView) findViewById(R.id.profile);
        button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();

        final String name = intent.getStringExtra("userName");
        final String links = intent.getStringExtra("page");
        final String image = intent.getStringExtra("images");

        Picasso.with(this).load(image).into(imageView);

        textView.setText(name);
        profile.setText(links);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(links));
                startActivity(websiteIntent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer@" + name + " "+ links);
                startActivity(intent);
            }
        });
    }
}
