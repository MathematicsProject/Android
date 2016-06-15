package com.example.user.cameracapture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

    String ocr;


    //    Button mShutter;     //캡쳐
    ImageButton mShutter;
    MyCameraSurface mSurface; //카메라
    String mRootPath; // 파일 path
    static final String PICFOLDER = "CameraTest";
    CropImageView mCropImageView; //크롭이미지
    ImageView iv;
    AlertDialog dialog;

    ImageButton cameraImage, keyPadImage;

    Intent intent;

    public static class CropParam {
        public int mAspectX = 0;
        public int mAspectY = 0;
        public int mOutputX = 0;
        public int mOutputY = 0;
        public int mMaxOutputX = 0;
        public int mMaxOutputY = 0;
    }

    public static CropParam getCropParam(Intent intent) {
        CropParam params = new CropParam();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(CropIntent.ASPECT_X) && extras.containsKey(CropIntent.ASPECT_Y)) {
                params.mAspectX = extras.getInt(CropIntent.ASPECT_X);
                params.mAspectY = extras.getInt(CropIntent.ASPECT_Y);
            }
            if (extras.containsKey(CropIntent.OUTPUT_X) && extras.containsKey(CropIntent.OUTPUT_Y)) {
                params.mOutputX = extras.getInt(CropIntent.OUTPUT_X);
                params.mOutputY = extras.getInt(CropIntent.OUTPUT_Y);
            }
            if (extras.containsKey(CropIntent.MAX_OUTPUT_X) && extras.containsKey(CropIntent.MAX_OUTPUT_Y)) {
                params.mMaxOutputX = extras.getInt(CropIntent.MAX_OUTPUT_X);
                params.mMaxOutputY = extras.getInt(CropIntent.MAX_OUTPUT_Y);
            }
        }
        return params;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCropImageView = (CropImageView) findViewById(R.id.CropWindow);
        mSurface = (MyCameraSurface) findViewById(R.id.previewFrame);
        iv = (ImageView) findViewById(R.id.iv);

        intent = new Intent(getApplicationContext(), KeyPadActivity.class);


        cameraImage = (ImageButton) findViewById(R.id.cameraImage_cam);
        keyPadImage = (ImageButton) findViewById(R.id.keyPadImage_cam);

        cameraImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.cameraImage_cam:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                        }
                        break;
                }
                return false;
            }
        });

        keyPadImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.keyPadImage_cam:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            Intent intent = new Intent(getApplicationContext(), KeyPadActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });


//        mShutter = (Button) findViewById(R.id.button1);

        mShutter = (ImageButton) findViewById(R.id.button1);
        mSurface.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSurface.mCamera.autoFocus(mAutoFocus);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mShutter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // 대화상자 띄움
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("이미지 전송중 입니다.")
                        .setMessage("잠시만 기다려 주세요.");
                dialog = builder.create();
                dialog.show();
                mShutter.setEnabled(false);

                mSurface.mCamera.takePicture(null, null, mPicture);
            }
        });

        //파일 path 지정
        mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + PICFOLDER;

        //파일 생성
        File fRoot = new File(mRootPath);
        if (fRoot.exists() == false) {
            if (fRoot.mkdir() == false) {
                Toast.makeText(this, "������ ������ ������ �����ϴ�.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }

        //화면 해상도
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        Integer width = dm.widthPixels;
        Integer height = dm.heightPixels;

        Log.i("sample", width.toString() + "+" + height.toString() + " 화면해상도");

        mCropImageView.initialize(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));

    }


    // ��Ŀ�� �����ϸ� �Կ� �㰡
    Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            mShutter.setEnabled(success);
        }
    };


    //캡쳐 버튼 클릭
    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {


            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap bm1;
//            Intent intent2 = new Intent(MainActivity.this, ResultActivity.class);

            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

            Integer width = dm.widthPixels;
            Integer height = dm.heightPixels;

            Integer w = bitmap.getWidth();
            Integer h = bitmap.getHeight();
            Log.i("sample", w.toString() + "+" + h.toString() + "크롭 전 비트맵");

            //해상도 조절
            Float rateW = width.floatValue() / h.floatValue();
            Float rateH = height.floatValue() / w.floatValue();

            Log.i("sample", rateW.toString() + "+" + rateH.toString() + "rateW(), rateH()");

            MainActivity.this.mCropImageView.getmCropWindow().setRateW(rateW);
            MainActivity.this.mCropImageView.getmCropWindow().setRateH(rateH);
            bitmap = MainActivity.this.mCropImageView.crop(new RotateBitmap(bitmap, 90));


//            Bitmap photo = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bytearray = stream.toByteArray();
//            String s = Base64.encodeToString(bytearray, Base64.DEFAULT);
//            //photo는 Bitmap변수
//            requestGetonNotice(s);
//                            Log.i("send", "보낸 스트링 사이즈: " + s.length()+ "\n");
//                Log.i("send", "보낸 바이트 사이즈 : " + bytearray.length );


            //임시저장
            try {

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                Log.i("send", "path" + path);
                File file = new File(path + "/test.jpg");

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                Log.i("send", "비트맵 임시 저장");

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                Uri uri = Uri.parse("file:" + path + "/test.jpg");
                intent.setData(uri);
                sendBroadcast(intent);
            } catch (Exception e) {
                Log.i("send", "비트맵 임시 저장 실패");
            }
//
//
//            //불러오기
            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                //리사이징
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;

//                Bitmap bm = BitmapFactory.decodeFile(path + "/test.jpg", options);
                bm1 = BitmapFactory.decodeFile(path + "/test.jpg", options);


                uploadPhoto(bm1);


//                Log.i("send", ocr + "ocr 결과");

//                Intent intent = new Intent(getApplicationContext(), KeyPadActivity.class);
//                intent.putExtra("result", ocr);
//                finish();
//                startActivity(intent);


//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                bm1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] bytearray = stream.toByteArray();
//                String s = Base64.encodeToString(bytearray, Base64.DEFAULT);
//
//                requestGetonNotice(s);
//
////                Log.i("send", "보낸 스트링: " + s + "\n");
//                Log.i("send", "보낸 스트링 사이즈: " + s.length()+ "\n");
//                Log.i("send", "보낸 바이트 사이즈 : " + bytearray.length );
            } catch (Exception e) {
                Log.i("send", "비트맵 임시 불러오기 실패");
            }


////            임시파일 삭제
//            try {
////                Thread.sleep(1000);
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg";
//                File file = new File(path);
//                if(file.exists()) {
//                    file.delete();
//                    Log.i("send", "비트맵 임시 파일 삭제");
//                }
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "파일 삭제 실패 ", Toast.LENGTH_SHORT).show();
//            }

//            Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bytearray = stream.toByteArray();
//            String s = Base64.encodeToString(bytearray, Base64.DEFAULT);
//            requestGetonNotice(s);
            //성공


//            iv.setImageBitmap(bitmap);


//            w = bitmap.getWidth();
//            h = bitmap.getHeight();
//
//            Log.i("sample", w.toString() + "+" + h.toString() + "크롭 후 비트맵");
//            iv.setBackgroundColor(Color.parseColor("#000000"));
        }
    };


    public void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath,
                                      String filename) {

        File file = new File(strFilePath);

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

        File fileCacheItem = new File(strFilePath + filename);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadPhoto(final Bitmap bitmap) {

        Thread thread = new Thread(new Runnable() {

            public void run() {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("string", ba1));
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://113.198.80.212:80/PS/test");
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = client.execute(post);
                    HttpEntity resEntity = response.getEntity();

                    ocr = EntityUtils.toString(resEntity);

                    intent.putExtra("result", ocr);
                    finish();
                    startActivity(intent);
                    Log.i("send", ocr);
//                    String result = EntityUtils.toString(resEntity);
//                    Log.i("send", resEntity.toString() + " entity");

//                    Log.i("send", EntityUtils.toString(resEntity) + " entity");
//                    intent = new Intent(getApplicationContext(), KeyPadActivity.class);
//                    intent.putExtra("result", EntityUtils.toString(resEntity) );
//                    finish();
//                    startActivity(intent);


                    client.getConnectionManager().shutdown();

                    //HttpEntity entity = response.getEntity();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


//    public void requestGetonNotice(final String image) {
//        // TODO Auto-generated method stub
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Retrofit.ServerIP).setLogLevel(RestAdapter.LogLevel.BASIC).build();
//                    Retrofit retrofit = restAdapter.create(Retrofit.class);
//                    retrofit.sendString(image, new Callback<String>() {
//                        @Override
//                        public void success(String result, Response arg1) {
//                            // TODO Auto-generated method stub
//
//
////                            tv.setText(result);
//                            Log.i("sample", result + "result");
//                            Log.i("sample", arg1.getUrl() + "Url");
//                            Log.i("send", "성공" + result + "\n");
////                            dialog.dismiss();
//
//                            Intent intent = new Intent(getApplicationContext(), KeyPadActivity.class);
//                            intent.putExtra("result", result);
//                            finish();
//                            startActivity(intent);
//
////                            webTv.loadUrl(result);
////                            m.dismiss();
//
//                            //dialog delete
////                            aa.dismiss();
//
//                        }
//
//                        @Override
//                        public void failure(RetrofitError arg0) {
//                            // TODO Auto-generated method stub
////                            tv.setText("N");
//                            Log.i("send", "실패" + "N" + "\n");
//                            Log.i("send", arg0.toString() + " error");
//                            Log.i("send",arg0.getBody() + " Body");
//                            Log.i("send", arg0.getMessage() + " Message");
////                            dialog.dismiss();
//                            Intent intent = new Intent(getApplicationContext(), KeyPadActivity.class);
//                            intent.putExtra("result", "");
//                            finish();
//                            startActivity(intent);
//
////                            aa.dismiss();
////                            m.dismiss();
//                        }
//                    });
//
//                } catch (Throwable ex) {
////                    tv.setText("E");
//                    Log.i("send", "실패" + "N" + "\n");
//                    dialog.dismiss();
////                    m.dismiss();
//                }
//            }
//        }).start();
//
//    }


}// 메인 액티비티 끝

class MyCameraSurface extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mHolder;
    Camera mCamera;

    public MyCameraSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // ǥ�� ������ ī�޶� �����ϰ� �̸����� ����
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);

        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }


    // ǥ�� �ı��� ī�޶� �ı��Ѵ�.
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

        }
    }

    // ǥ���� ũ�Ⱑ ������ �� ������ �̸����� ũ�⸦ ���� �����Ѵ�.
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> arSize = params.getSupportedPreviewSizes();
        if (arSize == null) {
            params.setPreviewSize(width, height);
        } else {
            int diff = 10000;
            Camera.Size opti = null;
            for (Camera.Size s : arSize) {
                if (Math.abs(s.height - height) < diff) {
                    diff = Math.abs(s.height - height);
                    opti = s;

                }
            }
            params.setPreviewSize(opti.width, opti.height);
        }
        mCamera.setParameters(params);
        mCamera.startPreview();
    }
}