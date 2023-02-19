package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import top.defaults.colorpicker.ColorPickerPopup;

public class ShopActivity extends AppCompatActivity {
    private int mDefaultColor=0;
    private ImageButton btn_choose_color1,btn_choose_color2,btn_choose_color3;
    private ConstraintLayout[] constraintLayout;
    private ImageView [] images;
    private MaterialButton btn_back;
    private int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initViews();


        btn_choose_color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                addColor();

            }
        });
        btn_choose_color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=2;
                addColor();

            }
        });
        btn_choose_color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=3;
                addColor();

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this,HomeActivity.class);
                intent.putExtra("color", mDefaultColor);
                intent.putExtra("choose",c);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViews() {
        btn_choose_color1= findViewById(R.id.btn_choose_color);
        btn_choose_color2= findViewById(R.id.btn_choose_color2);
        btn_choose_color3= findViewById(R.id.btn_choose_color3);

        constraintLayout = new ConstraintLayout[]{findViewById(R.id.ConstraintLayout1),findViewById(R.id.ConstraintLayout2),findViewById(R.id.ConstraintLayout3)};
    images= new ImageView[] {findViewById(R.id.iv_shop1),findViewById(R.id.iv_shop2),findViewById(R.id.iv_shop3),findViewById(R.id.iv_shop4)};
    btn_back = findViewById(R.id.btn_back);

    }

    private void addColor() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_color,null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        final Button mSetColorButton =myView.findViewById(R.id.set_color_button);
        final Button mPickColorButton=myView.findViewById(R.id.pick_color_button);
        final Button back =  myView.findViewById(R.id.back);
        final View mColorPreview=myView.findViewById(R.id.preview_selected_color);

           mDefaultColor=0;
        mPickColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new ColorPickerPopup.Builder(ShopActivity.this).initialColor(
                                        Color.RED) // set initial color
                                // of the color
                                // picker dialog
                                .enableBrightness(
                                        true) // enable color brightness
                                // slider or not
                                .enableAlpha(
                                        true) // enable color alpha
                                // changer on slider or
                                // not
                                .okTitle(
                                        "Choose") // this is top right
                                // Choose button
                                .cancelTitle(
                                        "Cancel") // this is top left
                                // Cancel button which
                                // closes the
                                .showIndicator(
                                        true) // this is the small box
                                // which shows the chosen
                                // color by user at the
                                // bottom of the cancel
                                // button
                                .showValue(
                                        true) // this is the value which
                                // shows the selected
                                // color hex code
                                // the above all values can be made
                                // false to disable them on the
                                // color picker dialog.
                                .build()
                                .show(
                                        v,
                                        new ColorPickerPopup.ColorPickerObserver() {
                                            @Override
                                            public void
                                            onColorPicked(int color) {
                                                // set the color
                                                // which is returned
                                                // by the color
                                                // picker
                                                mDefaultColor = color;

                                                // now as soon as
                                                // the dialog closes
                                                // set the preview
                                                // box to returned
                                                // color
                                                mColorPreview.setBackgroundColor(mDefaultColor);
                                            }
                                        });
                    }
                });

        // handling the Set Color button to set the selected
        // color for the GFG text.
        mSetColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // now change the value of the GFG text
                        // as well.

                        btn_choose_color1.setBackgroundColor(mDefaultColor);
                        btn_choose_color2.setBackgroundColor(mDefaultColor);
                        btn_choose_color3.setBackgroundColor(mDefaultColor);
                        btn_back.setBackgroundColor(mDefaultColor);
                        back.setBackgroundColor(mDefaultColor);
                        mPickColorButton.setBackgroundColor(mDefaultColor);
                        mSetColorButton.setBackgroundColor(mDefaultColor);
                        for (int i = 0; i <constraintLayout.length ; i++) {
                            constraintLayout[i].setBackgroundColor(mDefaultColor);
                        }
                        for (int i=0;i<images.length;i++){
                            images[i].setColorFilter(mDefaultColor);

                        }
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });









        dialog.show();

    }
}