package edu.uwp.appfactory.racinezoo.InfoScreen;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.uwp.appfactory.racinezoo.R;

import static edu.uwp.appfactory.racinezoo.Util.Config.*;

public class InfoDetailActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        int type = intent.getIntExtra("type",0);
        Log.d("TYPE", "Type is: "+type);
        switch (type){
            case INFO_TYPE_HOUR:
                setContentView(R.layout.info_hour_text);
                TextView tv = (TextView)findViewById(R.id.membership_link);
                clickLink(tv,"https://www.racinezoo.org/categories-benefits");
                TextView tv2 = (TextView)findViewById(R.id.grouprate_link);
                clickLink(tv2,"https://www.racinezoo.org/animal-programs");
                TextView tv3 = (TextView)findViewById(R.id.zoomanners_link);
                clickLink(tv3,"https://www.racinezoo.org/zoo-manners");
                break;
            case INFO_TYPE_DIRECT:
                setContentView(R.layout.info_dirct_text);
                break;
            case INFO_TYPE_SHOW:
                setContentView(R.layout.info_show_text);
                TextView tv4 = (TextView)findViewById(R.id.discovery);
                tv4.setMovementMethod(LinkMovementMethod.getInstance());
                tv4 = (TextView)findViewById(R.id.giraffe);
                tv4.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case INFO_TYPE_ABOUT:
                setContentView(R.layout.info_about_text);
                TextView tv5 = (TextView)findViewById(R.id.association);
                tv5.setMovementMethod(LinkMovementMethod.getInstance());
                tv5 = (TextView)findViewById(R.id.mission);
                tv5.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case INFO_TYPE_PRIVACY:
                setContentView(R.layout.info_privacy_text);
                break;
            case INFO_TYPE_ADMIN:
                setContentView(R.layout.info_admin_text);
                break;
            case INFO_TYPE_FOOD:
                setContentView(R.layout.info_food_text);
                TextView tv6 = (TextView)findViewById(R.id.gift);
                tv6.setMovementMethod(LinkMovementMethod.getInstance());
                tv6 = (TextView)findViewById(R.id.click_here);
                tv6.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case INFO_TYPE_CONTACT:
                setContentView(R.layout.info_contact_text);
                TextView tv7 = (TextView)findViewById(R.id.contact_us);
                tv7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.info_admin_text);
                    }
                });
                break;
            default:
                setContentView(R.layout.activity_info_detail);

                break;
        }
        //Toast.makeText( "Here is the info for directions", Toast.LENGTH_SHORT).show();

    }

    public void clickLink(TextView textView, final String url){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent membershilLink = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(membershilLink);
            }
        });
    }
}
