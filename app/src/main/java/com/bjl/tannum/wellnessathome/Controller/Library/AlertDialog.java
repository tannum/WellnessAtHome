package com.bjl.tannum.wellnessathome.Controller.Library;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Tannum on 03/02/17.
 */

public class AlertDialog {


    //Mask: Implement Interface
    public interface OnDialogConfirmListener{
        public void onDialogConfirm();
    }


    public static void ShowSuccessDialog(Context context){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }
    public static void ShowSuccessDialog(Context context ,final OnDialogConfirmListener listener){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if(listener != null){
                            sweetAlertDialog.dismiss();
                            listener.onDialogConfirm();
                        }
                    }
                });
        pDialog.show();
    }



    public static void ShowWaringDialog(Context context,final OnDialogConfirmListener listener){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if(listener != null){
                            sweetAlertDialog.dismiss();
                            listener.onDialogConfirm();
                        }

                    }
                });
        pDialog.show();
    }

    public static void ShowWaringDialog(Context context){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }


    public static void ShowErrorDialog(Context context){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }

    public static void ShowErrorDialog(Context context,final OnDialogConfirmListener listener ){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if(listener != null){
                            sweetAlertDialog.dismiss();
                            listener.onDialogConfirm();
                        }

                    }
                });
        pDialog.show();
    }


}
