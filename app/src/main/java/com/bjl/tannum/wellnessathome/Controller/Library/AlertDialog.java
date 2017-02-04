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


    public static void ShowSuccessDialog(Context context,String title,String content){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }
    public static void ShowSuccessDialog(Context context ,String title,String content,final OnDialogConfirmListener listener){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content)
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



    public static void ShowWaringDialog(Context context,String title,String content,String confirm_text,final OnDialogConfirmListener listener){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirm_text)
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

    public static void ShowWaringDialog(Context context ,String title,String content,String confirm_text){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirm_text)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }


    public static void ShowErrorDialog(Context context,String title,String content){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();
    }

    public static void ShowErrorDialog(Context context,String title,String content,final OnDialogConfirmListener listener ){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
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
