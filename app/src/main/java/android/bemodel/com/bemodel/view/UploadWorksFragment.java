package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.activity.MainActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v4.media.RatingCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import cn.bmob.v3.listener.FindListener;

import static android.app.Activity.RESULT_OK;

public class UploadWorksFragment extends Fragment implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Context context;
    private View view;

    private ImageView ivUpload;
    private EditText etContent;
    private Button btnAddVoice;
    private TextView tvLocation;
    private Switch swSelectLocation;

    private Button btnLeft;
    private Button btnRight;
    private TextView tvTitle;

    private Button btnSelectPhoto;

    private Uri imageUri;

    public LocationClient mLocationClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_works, container, false);

        context = getContext();

        initViews();

        return view;
    }

    private void initViews() {

        ivUpload = (ImageView) view.findViewById(R.id.iv_upload);
        etContent = (EditText)view.findViewById(R.id.et_content_uw);
        btnAddVoice = (Button)view.findViewById(R.id.bt_add_voice_uw);
        tvLocation = (TextView)view.findViewById(R.id.tv_my_location);
        swSelectLocation = (Switch)view.findViewById(R.id.sw_show_location);

        btnLeft = (Button)view.findViewById(R.id.left_btn);
        btnRight = (Button)view.findViewById(R.id.right_btn);
        tvTitle = (TextView)view.findViewById(R.id.title_text);

        btnSelectPhoto = (Button)view.findViewById(R.id.btn_select_photo);

        btnLeft.setVisibility(View.GONE);
        tvTitle.setText("上传作品");
        btnRight.setText("上传");

        swSelectLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    List<String> permissionList = new ArrayList<>();
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
                    }
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    if (!permissionList.isEmpty()) {
                        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                        //调用ActivityCompat.requestPermissions()方法一次性申请
                        ActivityCompat.requestPermissions(context, permissions, 1);
                    } else {
                        requestLocation();
                    }


                } else {

                }
            }


        });

//        ivUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File outputImage = new File(getExternalCachDir(), "output_image.jpg");
//                try {
//                    if (outputImage.exists()) {
//                        outputImage.delete();
//                    }
//                    outputImage.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (Build.VERSION.SDK_INT >= 24) {
//                    imageUri = FileProvider.getUriForFile(context, "com.bemodel.fileprovider", outputImage);
//                } else {
//                    imageUri = Uri.fromFile(outputImage);
//                }
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, TAKE_PHOTO);
//
//            }
//        });
//
//        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                } else {
//                    openAlbum();
//                }
//            }
//        });
//        btnAddVoice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //设置位置更新时间为5秒
        option.setScanSpan(5000);
        //获取当前位置详细的地址信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuffer currentPosition = new StringBuffer();

            currentPosition.append(bdLocation.getCity());   //获取所在市
            currentPosition.append(bdLocation.getDistrict());   //获取所在区

            tvLocation.setText(currentPosition);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_upload:
                File outputImage = new File(getExternalCachDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(context, "com.bemodel.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;

            case R.id.btn_select_photo:
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;

            case R.id.bt_add_voice_uw:

                break;

            case R.id.right_btn:
                break;

        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        ivUpload.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if(Build.VERSION.SDK_INT >= 19) {   //判断手机系统版本号
                        handleImageOnKitKat(data);  //4.4及以上系统用这个方法处理图片
                    } else {
                        handleImageBeforeKitKat(data);  //4.4以下系统用这个方法处理图片
                    }
                }

            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAlbum();
//                } else {
//                    Toast.makeText(context, "You denied the permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 2:
                if (grantResults.length > 0) {
                    for (int result :grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(context, "必须同意所有的权限才能使用该功能", Toast.LENGTH_SHORT).show();
//                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(context, "发生未知错误", Toast.LENGTH_SHORT).show();
//                    finish();
                }
                break;

            default:
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivUpload.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri contentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(contentUri, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
