package com.headrun.orderitem.suplier.addItemView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.R;
import com.headrun.orderitem.database.FirebaseReferencePath;
import com.headrun.orderitem.model.Item;
import com.headrun.orderitem.model.UserItems;
import com.headrun.orderitem.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujith on 26/5/17.
 */

public class AddItemPresenter {

    AddItemView mAddItemView;
    Context mContext;
    Utils mUtils;

    public AddItemPresenter(Context mContext, AddItemView mAddItemView) {
        this.mAddItemView = mAddItemView;
        this.mContext = mContext;
        mUtils = new Utils(mContext);
    }


    public void checkItems(String name, String desc, String actual_price, String offer_price, Bitmap mBitmap) {
        boolean val_check = true;
        if (TextUtils.isEmpty(name)) {
            val_check = false;
            mAddItemView.setNameError(mContext.getString(R.string.error_item_name));
        }
        if (TextUtils.isEmpty(desc)) {
            val_check = false;
            mAddItemView.setNameError(mContext.getString(R.string.error_item_desc));
        }
        if (TextUtils.isEmpty(actual_price)) {
            val_check = false;
            mAddItemView.setNameError(mContext.getString(R.string.error_item_act_price));
        }
        if (TextUtils.isEmpty(offer_price)) {
            val_check = false;
            mAddItemView.setNameError(mContext.getString(R.string.error_item_offer_price));
        }

        if (val_check && Integer.parseInt(actual_price) < Integer.parseInt(offer_price)) {
            val_check = false;
            mAddItemView.setNameError(mContext.getString(R.string.error_price_more_error));
        }

        if (mBitmap == null) {
            val_check = false;
            Toast.makeText(mContext, "Select the image", Toast.LENGTH_SHORT).show();
        }
        if (val_check) {
            Item data_item = new Item(name, actual_price, offer_price, desc);
            uploadImage(data_item, bimaptoByte(mBitmap));
        }
    }

    public byte[] bimaptoByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }


    public void uploadImage(final Item mItem, byte[] data) {
        String date = new SimpleDateFormat("_yyyyMMdd_HHmmss").format(new Date());
        String file_name = mItem.name + date;
        StorageReference mStorageRef = FirebaseReferencePath.getStoregereference(file_name);

        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mUtils.firebaseException(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mItem.image_url = downloadUrl.toString();

                setFirbaseItem(mItem);
            }
        });
    }

    public void setFirbaseItem(Item mItem) {

        final String userId = mUtils.loginCheck().getUid();
        final String key = FirebaseReferencePath.getItems().push().getKey();

        final UserItems muser_item = new UserItems(key, true);
        mItem.user_id = userId;
        FirebaseReferencePath.getItems().child(key).setValue(mItem).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setUserItems(userId, muser_item);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mUtils.firebaseException(e);
            }
        });

    }

    public void setUserItems(String user_id, UserItems mUserItems) {

        FirebaseReferencePath.getUserItems().child(user_id).setValue(mUserItems).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "" + mContext.getString(R.string.item_added), Toast.LENGTH_SHORT).show();
                        mAddItemView.clearFields();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mUtils.firebaseException(e);
            }
        });

    }

}
