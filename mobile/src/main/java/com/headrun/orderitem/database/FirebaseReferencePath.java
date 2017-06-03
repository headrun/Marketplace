package com.headrun.orderitem.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.config.Constants;

/**
 * Created by sujith on 23/5/17.
 */

public class FirebaseReferencePath {


    public static DatabaseReference getReference() {
        return AppApplication.getFirebaseDataBase().getReference();
    }

    public static StorageReference getStoregereference(String file_name) {
        return AppApplication.getmFirebaseStorage().getReference().child(Constants.FirebaseNames.IMAGES + "/" + file_name);
    }

    public static DatabaseReference getUserReference() {
        return AppApplication.getFirebaseDataBase().getReference().child(Constants.FirebaseNames.USERS);
    }

    public static DatabaseReference getItems() {
        return AppApplication.getFirebaseDataBase().getReference().child(Constants.FirebaseNames.ITEMS);
    }

    public static DatabaseReference getUserItems() {
        return AppApplication.getFirebaseDataBase().getReference().child(Constants.FirebaseNames.USER_ITEMS);
    }

    public static DatabaseReference getOrders() {
        return AppApplication.getFirebaseDataBase().getReference().child(Constants.FirebaseNames.ORDERS);
    }

    public static DatabaseReference getOderItems() {
        return AppApplication.getFirebaseDataBase().getReference().child(Constants.FirebaseNames.ORDERS_ITEMS);
    }


}
