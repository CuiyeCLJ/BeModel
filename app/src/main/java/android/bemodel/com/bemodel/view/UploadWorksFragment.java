package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.activity.MainActivity;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.bemodel.com.bemodel.db.UserInfo;
import android.bemodel.com.bemodel.util.PermissionListener;
import android.bemodel.com.bemodel.util.PermissionManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.app.Activity.RESULT_OK;
import static android.bemodel.com.bemodel.util.QiniuUtil.getUploadToken;
import static android.bemodel.com.bemodel.util.Utility.Bitmap2Bytes;
import static android.bemodel.com.bemodel.util.Utility.getRandomFileName;

public class UploadWorksFragment extends Fragment implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int REQUEST_CODE_LOCATION = 3;

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

    private Uri imageUri;

    public LocationClient mLocationClient;

    private UserInfo user;

    private PermissionManager helper;

    private MainActivity activity;

    private String key;

    private double latitude;
    private double longitude;

    private BmobGeoPoint geoPoint;

    public static String TAG = "BeModel";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_works, container, false);
        this.context = inflater.getContext();
        activity = (MainActivity)getActivity();
        user = (UserInfo) BmobUser.getCurrentUser();

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

        btnLeft.setVisibility(View.GONE);
        tvTitle.setText("上传作品");
        btnRight.setText("上传");

        mLocationClient = new LocationClient(getContext());

        swSelectLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //注册一个定位监听器，当获取到位置信息时，就会回调这个定位监听器
                    mLocationClient.registerLocationListener(new MyLocationListener());

                    helper = PermissionManager.with(UploadWorksFragment.this)
                            .addRequestCode(UploadWorksFragment.REQUEST_CODE_LOCATION)
                            .permissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
                            .permissions(android.Manifest.permission.READ_PHONE_STATE)
                            .permissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .setPermissionsListener(new PermissionListener() {
                                @Override
                                public void onGranted() {
                                    //当权限被授予时调用
                                    requestLocation();

                                }

                                @Override
                                public void onDenied() {
                                    //用户拒绝该权限时调用
                                    geoPoint = new BmobGeoPoint();
                                }

                                @Override
                                public void onShowRationale(String[] permissions) {
                                    //当用户拒绝某权限时并点击“不再提醒”的按钮时，下次应用再请求该权限时，
                                    // 需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）

                                }
                            }).request();

                }
            }
        });

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

            //获得经度
            latitude = bdLocation.getLatitude();
            //获得纬度
            longitude = bdLocation.getLongitude();

            geoPoint = new BmobGeoPoint(longitude, latitude);
            user.setGeo(geoPoint);

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
            case R.id.iv_upload:    //创建照片选择方式的对话框
                openDialog();
                break;

            case R.id.bt_add_voice_uw:  //添加语音

                break;

            case R.id.right_btn:        //上传作品
                if (user != null) {
                    uploadData();
                } else {
                    gotoLoginActivity();
                }
                break;

        }
    }

    String[] items = new String[] {"拍摄", "相册"};

    /**
     * 创建对话框及注册点击事件
     */
    private void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("图片选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:     //调用摄像头拍照
                                //创建File对象，用于存储拍照后的图片
                                File outputImage = new File(activity.getExternalCacheDir(), "output_image.jpg");
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

                            case 1:     //从相册选取
                                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                } else {
                                    openAlbum();
                                }
                                break;
                        }
                    }
                }).create();
        alertDialog.show();
    }

    //上传数据到服务器
    private void uploadData() {

        uploadImg();
        ModelCircleInfo modelCircleInfo = new ModelCircleInfo();
        if (ivUpload.getDrawable() == null) {
            Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String text = etContent.getText().toString();
        if (text.equals("")) {
            Toast.makeText(context, "描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        modelCircleInfo.setBmiddlePic(key);
        modelCircleInfo.setText(text);
        modelCircleInfo.setUser(user);
        modelCircleInfo.setGeo(geoPoint);
        modelCircleInfo.setAddress(tvLocation.getText().toString());

        modelCircleInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e(TAG, "错误码: " + e.getMessage());
                }
            }
        });
    }

    /**
     * 上传图片到七牛
     */
    private void uploadImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = getUploadToken("bemodel-demo");    //上传凭证
                Bitmap imageBitmap = ((BitmapDrawable)ivUpload.getDrawable()).getBitmap();
                byte[] imageData = Bitmap2Bytes(imageBitmap);   //上传的数据

                if (token != null) {
                    key = getRandomFileName();    //上传数据保存的文件名
                    UploadManager uploadManager = new UploadManager();
                    uploadManager.put(imageData, key, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {

                        }
                    }, null);
                } else {

                }
            }
        }).start();
    }

    //启动登录活动
    private void gotoLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //打开相册程序选择照片
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
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
        Cursor cursor = activity.getContentResolver().query(contentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
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

}
