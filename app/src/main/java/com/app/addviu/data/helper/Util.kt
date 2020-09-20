package com.app.naxtre.mvvmfinal.data.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.DIRECTORY_NAME
import com.app.addviu.view.viewInterface.AlertDialogListener
import com.app.addviu.view.viewInterface.YesClick
import kotlinx.android.synthetic.main.select_image_dialog.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

open class Util {

    companion object {
        private var progressDialog: Dialog? = null
        val osVersion: Int
            get() = Build.VERSION.SDK_INT

        var fileUri: Uri? = null
        private val PICK_FROM_CAMERA = 1
        private val SELECT_PICTURE = 2
        var imageFilePath = ""
        private var alertDialog: AlertDialog? = null

        fun showProgressDialog(c: Activity) {
            progressDialog = Dialog(c, R.style.theme_Dialog)
            progressDialog?.setContentView(R.layout.custom_progress_dialog)
            progressDialog?.setCancelable(false)

            if (!c.isFinishing) {
                progressDialog?.show()
            }
        }

        fun dismissDialog() {
            if (progressDialog?.isShowing!!) {
                progressDialog?.dismiss()
            }
        }

        // TODO: Consider calling
        @SuppressLint("HardwareIds")
        fun deviceId(context: Context): String {
            val deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            return deviceId ?: "12345"

        }

        fun getBuildVersion(context: Context): String {
            var currentVersion = ""
            try {
                currentVersion = context.packageManager
                    .getPackageInfo(context.packageName, 0).versionName
            } catch (e: NameNotFoundException) {
                e.printStackTrace()
            }

            return currentVersion
        }

        fun fullScreen(context: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                context.window.statusBarColor = Color.TRANSPARENT

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            } else {
                context.window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
        }

        fun showImageAlertDialog(context: Activity) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dialogView: View? = null
            dialogView = inflater.inflate(R.layout.select_image_dialog, null)
            alertDialog = showAlertDialog(
                context,
                "", "", "", object : AlertDialogListener {
                    internal val cameraIntents: List<Intent> = ArrayList()

                    override fun positiveBtnPressed() {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        var photoFile: File? = null
                        try {
                            photoFile = createImageFile(context)
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                        }

                        if (photoFile != null) {

                            fileUri = FileProvider.getUriForFile(
                                context,
                                context.getString(R.string.file_provider_authority), photoFile
                            )
                            intent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                fileUri
                            )
                        }
                        context.startActivityForResult(intent, PICK_FROM_CAMERA)
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    override fun negativeBtnPressed() {
                        val intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        context.startActivityForResult(intent, SELECT_PICTURE)
                    }
                }, dialogView
            )
            alertDialog!!.show()
        }

        fun showAlertDialog(
            context: Context, textHeading: String, positiveText: String, negativeText: String,
            alertDialogListener: AlertDialogListener, dialogView: View?
        ): AlertDialog {
            val dialogBuilder = AlertDialog.Builder(context, R.style.alertDialog)

            dialogBuilder.setView(dialogView)

            if (!textHeading.isEmpty()) {
                dialogView!!.txt_change.text = textHeading
            }

            if (!positiveText.isEmpty()) {
                dialogView!!.btn_yes.text = positiveText
            }

            if (!negativeText.isEmpty()) {
                dialogView!!.btn_no.text = negativeText
            }

            dialogView!!.btn_yes.setOnClickListener {
                alertDialogListener.positiveBtnPressed()
                alertDialog!!.dismiss()
            }

            dialogView.btn_no.setOnClickListener {
                alertDialogListener.negativeBtnPressed()
                alertDialog!!.dismiss()
            }

            alertDialog = dialogBuilder.create()
            alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

            return alertDialog!!
        }

        @Throws(IOException::class)
        private fun createImageFile(context: Activity): File {
            val timeStamp = SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date())
            val imageFileName = "IMG_" + timeStamp + "_"

            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
            )

            imageFilePath = image.absolutePath
            return image
        }

        public fun comingSoonDialog(context: Context, title:String) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val alertLayout = inflater.inflate(R.layout.coming_soon_dialog, null)
            val msgAlert = alertLayout.findViewById<TextView>(R.id.msgAlert)
            val okButton = alertLayout.findViewById<TextView>(R.id.okBtnAlert)
//            val oprnAlert = alertLayout.findViewById<TextView>(R.id.oprnAlert)

            val alert = android.app.AlertDialog.Builder(context)
            val a = alert.create()
            //        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            a.setView(alertLayout, 0, 0, 0, 0)
            a.setCancelable(false)
            a.show()
            msgAlert.text = title
            okButton.text = "Ok"
//            oprnAlert.text = "YES"
            okButton.setOnClickListener { a.dismiss() }

//            oprnAlert.setOnClickListener {
//                a.dismiss()
//            }
        }

        public fun showDeleteDialog(context: Context, title:String, openClick: YesClick) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val alertLayout = inflater.inflate(R.layout.download_dialog, null)
            val msgAlert = alertLayout.findViewById<TextView>(R.id.msgAlert)
            val okButton = alertLayout.findViewById<TextView>(R.id.okBtnAlert)
            val oprnAlert = alertLayout.findViewById<TextView>(R.id.oprnAlert)

            val alert = android.app.AlertDialog.Builder(context)
            val a = alert.create()
            //        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            a.setView(alertLayout, 0, 0, 0, 0)
            a.show()
            msgAlert.text = title
            okButton.text = "NO"
            oprnAlert.text = "YES"
            okButton.setOnClickListener { a.dismiss() }

            oprnAlert.setOnClickListener {
                openClick.yesClick()
                a.dismiss()
            }
        }

        fun checkEmpty(edit: EditText, editName: String, context: Activity): Boolean {
            val str = edit.text.toString().trim { it <= ' ' }
            if (str.isEmpty()) {
                showToast(
                    editName
                            + " "
                            + context.resources.getString(R.string.should_not_be_empty), context
                )
                return true
            }
            return false
        }

        fun saveBitmapToFile(file: File): File? {
            return try {
                // BitmapFactory options to downsize the image
                val o = BitmapFactory.Options()
                o.inJustDecodeBounds = true
                o.inSampleSize = 6
                // factor of downsizing the image
                var inputStream = FileInputStream(file)
                //Bitmap selectedBitmap = null;
                BitmapFactory.decodeStream(inputStream, null, o)
                inputStream.close()

                // The new size we want to scale to
                val REQUIRED_SIZE = 75

                // Find the correct scale value. It should be the power of 2.
                var scale = 1
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE
                ) {
                    scale *= 2
                }
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                inputStream = FileInputStream(file)
                val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
                inputStream.close()

                // here i override the original image file
                file.createNewFile()
                val outputStream = FileOutputStream(file)
                selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                file
            } catch (e: java.lang.Exception) {
                null
            }
        }

        fun isEmailValid(email: String): Boolean {
            var isValid = false

            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        fun isPassValid(
            context: Activity,
            passwordhere: String
        ): Boolean {

//        val specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
            val upperCasePatten = Pattern.compile("[A-Z ]")
            val lowerCasePatten = Pattern.compile("[a-z ]")
            val digitCasePatten = Pattern.compile("[0-9 ]")

            if (passwordhere.length < 8 || !upperCasePatten.matcher(passwordhere)
                    .find() || !lowerCasePatten.matcher(
                    passwordhere
                ).find() || !digitCasePatten.matcher(passwordhere).find()
            ) {
                showToast(
                    "Password must be at least 8 characters, and contain at least one upper case letter, one lower case letter, and one number.",
                    context
                )
                return false
            }
            return true

        }

        fun getRootDirPath(context: Context): String {

            val folder = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ).toString() + "/" + DIRECTORY_NAME
            )
            if (!folder.exists()) {
                folder.mkdirs()
            }
            return folder.absolutePath
        }

        fun selectDate(editText: EditText, context: Context) {
            val calendar = Calendar.getInstance()
            val year1 = calendar.get(Calendar.YEAR)
            val month2 = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    var month1 = month
                    var mDay = day.toString()
                    month1 += 1
                    var mnth = month1.toString()
                    if (mDay.length == 1) {
                        mDay = "0$mDay"
                    }
                    if (mnth.length == 1) {
                        mnth = "0$mnth"
                    }
                    editText.setText("$mDay/$mnth/$year")

                }, year1, month2, dayOfMonth
            )
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        fun showDatePickerDialog(context: Context, dateEditText: EditText, ageEditText: EditText) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year_, monthOfYear, dayOfMonth ->

                    val monthF = monthOfYear + 1
                    val age = getAgeFromDob(year_, monthF, dayOfMonth, context)

                    if (age!!.isNotBlank()) {
                        ageEditText.setText(age)

                        val days = String.format("%02d", dayOfMonth)
                        val months = String.format("%02d", monthF)
                        dateEditText.setText(
                            year_.toString().plus("-").plus(months).plus("-").plus(days)
                        )
                    }

                },
                year,
                month,
                day
            )

            dpd.show()
        }

        private fun getAgeFromDob(year: Int, month: Int, day: Int, context: Context): String? {
            val dob = Calendar.getInstance()
            val today = Calendar.getInstance()
            dob[year, month] = day
            var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
            if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                age--
            }
            val ageInt = age
            if (ageInt < 13) {
                showToast(
                    "You are not authorised to register this app as you are under 13.",
                    context
                )
                return ""
            }
            return ageInt.toString()
        }


        fun showToast(msg: String, _context: Context) {
            try {
                (_context as AppCompatActivity).runOnUiThread(object : Runnable {

                    private var toast: Toast? = null

                    override fun run() {

                        toast = Toast.makeText(_context, msg, Toast.LENGTH_LONG)
                        toast!!.show()
                        val handler = Handler()
                        handler.postDelayed({ toast!!.cancel() }, 2000)

                    }
                })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return activeNetworkInfo.isConnected
            } else {
                showToast("Check your internet connection", context)
                return false
            }
        }

        fun showError(context: Context?) {
            if (context == null) {
                showToast(
                    "Something went wrong with your internet connection",
                    AppController.instance!!
                )
            } else {
                showToast("Something went wrong with your internet connection", context)
            }
        }

    }
}