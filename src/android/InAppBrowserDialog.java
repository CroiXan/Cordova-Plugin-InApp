/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.inappbrowser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Oliver on 22/11/2013.
 */
public class InAppBrowserDialog extends Dialog {
    Context context;
    InAppBrowser inAppBrowser = null;

    public InAppBrowserDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public void setInAppBroswer(InAppBrowser browser) {
        this.inAppBrowser = browser;
    }

    public void onBackPressed () {
        if (this.inAppBrowser == null) {
            this.dismiss();
        } else {
            // better to go through the in inAppBrowser
            // because it does a clean up
            if (this.inAppBrowser.hardwareBack() && this.inAppBrowser.canGoBack()) {
                if(this.inAppBrowser.minimizeApp()){
                    new AlertDialog.Builder(context)
                      .setTitle(this.inAppBrowser.getDialogTittle())
                      .setMessage(this.inAppBrowser.getDialogMessage())
                      .setNegativeButton(this.inAppBrowser.getDialogNegative(),null)
                      .setPositiveButton(this.inAppBrowser.getDialogPositive(), new OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(startMain);
                        }
                      }).show();

                }else{
                  this.inAppBrowser.goBack();
                }
            }  else {
                this.inAppBrowser.closeDialog();
            }
        }
    }
}
