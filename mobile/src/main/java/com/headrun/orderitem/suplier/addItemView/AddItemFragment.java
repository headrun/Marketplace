package com.headrun.orderitem.suplier.addItemView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.headrun.orderitem.R;

import java.io.IOException;

import static com.headrun.orderitem.R.id.img_item;

/**
 * Created by sujith on 25/5/17.
 */

public class AddItemFragment extends Fragment implements AddItemView {


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int GALLLERY_REQ = 2;


    String TAG = AddItemFragment.class.getSimpleName();

    ImageView mItemImage;
    EditText edt_name, edt_actual_price, edt_offer_price;
    TextView edt_desc;
    Button btn_additem;

    AddItemPresenter mAddItemPresenter;
    PopupMenu mPopupmenu;
    Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_additem, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        mAddItemPresenter = new AddItemPresenter(getActivity(), this);
    }

    public void init() {
        mItemImage = (ImageView) getActivity().findViewById(img_item);
        edt_name = (EditText) getActivity().findViewById(R.id.edt_name);
        edt_desc = (TextView) getActivity().findViewById(R.id.edt_desc);
        edt_actual_price = (EditText) getActivity().findViewById(R.id.edt_actual_price);
        edt_offer_price = (EditText) getActivity().findViewById(R.id.edt_offer_price);
        btn_additem = (Button) getActivity().findViewById(R.id.btn_additem);

        mPopupmenu = new PopupMenu(getActivity(), mItemImage);
        mPopupmenu.getMenuInflater().inflate(R.menu.gallery_option, mPopupmenu.getMenu());

        btn_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAddItemPresenter.checkItems(edt_name.getText().toString().trim(),
                        edt_desc.getText().toString().trim(),
                        edt_actual_price.getText().toString().trim(),
                        edt_offer_price.getText().toString().trim(),
                        bitmap);
            }
        });

        mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mPopupmenu.show();
            }
        });

        mPopupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.take_photo) {
                    Log.wtf(TAG, "sel title is " + item.getTitle());
                    takeaphoto();
                } else if (item.getItemId() == R.id.gallery) {
                    Log.wtf(TAG, "sel title is " + item.getTitle());
                    getGallery();
                }

                return false;
            }
        });
    }

    @Override
    public void setNameError(String msg_err) {
        edt_name.setError(msg_err);
    }

    @Override
    public void setDescError(String msg_err) {
        edt_desc.setError(msg_err);
    }

    @Override
    public void setActualPirceError(String msg_err) {
        edt_actual_price.setError(msg_err);
    }

    @Override
    public void setOfferPriceError(String msg_err) {
        edt_offer_price.setError(msg_err);
    }

    @Override
    public void setImageError(String msg_err) {

    }

    @Override
    public void clearFields() {
        edt_name.setText("");
        edt_desc.setText("");
        edt_actual_price.setText("");
        edt_offer_price.setText("");
        mItemImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_nav_profile_active));

    }

    public void takeaphoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), GALLLERY_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLLERY_REQ) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    mItemImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {

                try {
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    mItemImage.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.i("TAG", "Some exception " + e);
                }

            }
        }
    }
}
