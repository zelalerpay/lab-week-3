package msku.ceng.madlab.week3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE = 1;
    private ImageView img;
    private EditText txtMsg;
    private ImageButton btnOk, btnCancel;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Görsel bileşenlerin tanımlanması
        img = (ImageView) findViewById(R.id.imageView);
        txtMsg = (EditText) findViewById(R.id.txtMessage);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);

        // ImageView tıklama olayını dinleyiciye bağlama (Kamera açma)
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kamera uygulamasını başlatan intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAPTURE_IMAGE);
                }
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("msg", txtMsg.getText());


                if (img.getDrawable() != null) {
                    bundle.putParcelable("bitmap", ((BitmapDrawable) img.getDrawable()).getBitmap());
                }

                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                imageBitmap = (Bitmap) bundle.get("data");

                img.setImageBitmap(imageBitmap);
            }
        }
    }
}
