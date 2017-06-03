package com.headrun.orderitem.suplier.addItemView;

/**
 * Created by sujith on 26/5/17.
 */

public interface AddItemView {


    public void setNameError(String msg_err);

    public void setDescError(String msg_err);

    public void setActualPirceError(String msg_err);

    public void setOfferPriceError(String msg_err);

    public void setImageError(String msg_err);

    public void clearFields();

}
