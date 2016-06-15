package com.example.user.cameracapture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Administrator on 2016-04-12.
 */
public class KeyPadActivity extends Activity {
    EditText edit1;
    AlertDialog dialog;

    //키패드
    RelativeLayout keyPad1, keyPad2, keyPad3, keyPad4;
    //키패드1 버튼들
    ImageButton[] key1Button = new ImageButton[30];
    //키패드2 버튼들
    ImageButton[] key2Button = new ImageButton[30];
    //키패드3 버튼들
    ImageButton[] key3Button = new ImageButton[30];
    //키패드4 버튼들
    ImageButton[] key4Button = new ImageButton[30];
    ImageButton cameraImage, keyPadImage;
    //키패드 아이디
    Integer[] key1ButtonID = {
            R.id.key1Button1, R.id.key1Button2, R.id.key1Button3, R.id.key1Button4, R.id.key1Button5, R.id.key1Button6,
            R.id.key1Button7, R.id.key1Button8, R.id.key1Button9, R.id.key1Button10, R.id.key1Button11, R.id.key1Button12,
            R.id.key1Button13, R.id.key1Button14, R.id.key1Button15, R.id.key1Button16, R.id.key1Button17, R.id.key1Button18,
            R.id.key1Button19, R.id.key1Button20, R.id.key1Button21, R.id.key1Button22, R.id.key1Button23, R.id.key1Button24,
            R.id.key1Button25, R.id.key1Button26, R.id.key1Button27, R.id.key1Button28, R.id.key1Button29, R.id.key1Button30
    };

    Integer[] key2ButtonID = {
            R.id.key2Button1, R.id.key2Button2, R.id.key2Button3, R.id.key2Button4, R.id.key2Button5, R.id.key2Button6,
            R.id.key2Button7, R.id.key2Button8, R.id.key2Button9, R.id.key2Button10, R.id.key2Button11, R.id.key2Button12,
            R.id.key2Button13, R.id.key2Button14, R.id.key2Button15, R.id.key2Button16, R.id.key2Button17, R.id.key2Button18,
            R.id.key2Button19, R.id.key2Button20, R.id.key2Button21, R.id.key2Button22, R.id.key2Button23, R.id.key2Button24,
            R.id.key2Button25, R.id.key2Button26, R.id.key2Button27, R.id.key2Button28, R.id.key2Button29, R.id.key2Button30
    };

    Integer[] key3ButtonID = {
            R.id.key3Button1, R.id.key3Button2, R.id.key3Button3, R.id.key3Button4, R.id.key3Button5, R.id.key3Button6,
            R.id.key3Button7, R.id.key3Button8, R.id.key3Button9, R.id.key3Button10, R.id.key3Button11, R.id.key3Button12,
            R.id.key3Button13, R.id.key3Button14, R.id.key3Button15, R.id.key3Button16, R.id.key3Button17, R.id.key3Button18,
            R.id.key3Button19, R.id.key3Button20, R.id.key3Button21, R.id.key3Button22, R.id.key3Button23, R.id.key3Button24,
            R.id.key3Button25, R.id.key3Button26, R.id.key3Button27, R.id.key3Button28, R.id.key3Button29, R.id.key3Button30
    };

    Integer[] key4ButtonID = {
            R.id.key4Button1, R.id.key4Button2, R.id.key4Button3, R.id.key4Button4, R.id.key4Button5, R.id.key4Button6,
            R.id.key4Button7, R.id.key4Button8, R.id.key4Button9, R.id.key4Button10, R.id.key4Button11, R.id.key4Button12,
            R.id.key4Button13, R.id.key4Button14, R.id.key4Button15, R.id.key4Button16, R.id.key4Button17, R.id.key4Button18,
            R.id.key4Button19, R.id.key4Button20, R.id.key4Button21, R.id.key4Button22, R.id.key4Button23, R.id.key4Button24,
            R.id.key4Button25, R.id.key4Button26, R.id.key4Button27, R.id.key4Button28, R.id.key4Button29, R.id.key4Button30
    };

    int i;

    String result;

    WebView webTv;
    TextView tv;

    String send;

    class CookWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keypad);


//        tv = (TextView) findViewById(R.id.tv);
        //레이아웃 아이디 얻어오기
        edit1 = (EditText) findViewById(R.id.edit1);


        cameraImage = (ImageButton) findViewById(R.id.cameraImage_key);
        keyPadImage = (ImageButton) findViewById(R.id.keyPadImage_key);


        cameraImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.cameraImage_key:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            finish();
                            startActivity(intent);
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
                    case R.id.keyPadImage_key:
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

        keyPad1 = (RelativeLayout) findViewById(R.id.keyPad1);
        keyPad2 = (RelativeLayout) findViewById(R.id.keyPad2);
        keyPad3 = (RelativeLayout) findViewById(R.id.keyPad3);
        keyPad4 = (RelativeLayout) findViewById(R.id.keyPad4);

        webTv = (WebView) findViewById(R.id.webTv);
        webTv.setWebViewClient(new CookWebviewClient());

        WebSettings webSet = webTv.getSettings();
        webSet.setBuiltInZoomControls(true);


        for (i = 0; i < key1ButtonID.length; i++) {
            key1Button[i] = (ImageButton) findViewById(key1ButtonID[i]);
            key2Button[i] = (ImageButton) findViewById(key2ButtonID[i]);
            key3Button[i] = (ImageButton) findViewById(key3ButtonID[i]);
            key4Button[i] = (ImageButton) findViewById(key4ButtonID[i]);
        }

        Intent intent = getIntent();
        String setEdit = intent.getStringExtra("result");
        Log.i("send", "\" "+ setEdit + " \"ocr에서 넘어온 결과");
        if(setEdit != null)  {
//            edit1.setText("2+2");
            edit1.setText(setEdit);
            edit1.setSelection(edit1.length());
        }

        //1번째 키패드 리스너
        key1Button[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button1:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, " ");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button2:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = "";
                            edit1.setText(result);
                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button3:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            if (result.equals("")) return false;
                            int selection = edit1.getSelectionStart();
                            if(selection == 0) return false;
                            String result1 = result.substring(0, selection-1);
                            result = result1 + result.substring(selection, result.length());
                            edit1.setText(result);
                            edit1.setSelection(selection - 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button4:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });

        key1Button[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button5:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);

                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, ")");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result += ")";
//                            edit1.setSelection(edit1.length());
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button6:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);

                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "+");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "+";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button7:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.VISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button8:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "%");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button9:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "7");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button10:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "8");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button11:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "9");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button12:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "-");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "-";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button13:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.VISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button14:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "^(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 2);

//                            result = edit1.getText().toString();
//                            result += "x^y";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button15:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "4");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "4";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button16:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "5");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "5";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button17:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "6");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "6";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button18:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "*");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "*";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button19:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.VISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button20:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "√(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 2);

//                            result = edit1.getText().toString();
//                            result += "√(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button21:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "1");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "1";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button22:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "2");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
//                            result = edit1.getText().toString();
//                            result += "2";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button23:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "3");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "3";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button24:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "/");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "/";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button25:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button26:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "!");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "!";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[26].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button27:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "0");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "0";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[27].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button28:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, ".");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
//
//                            result = edit1.getText().toString();
//                            result += ".";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[28].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button29:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "±");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "±";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key1Button[29].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key1Button30:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(KeyPadActivity.this);
                            builder.setTitle("계산중 입니다.")
                                    .setMessage("잠시만 기다려 주세요.");
                            dialog = builder.create();
                            dialog.show();
                            requestGetonNotice(result);
                        }
                        break;
                }
                return false;
            }
        });

//        key1Button[29].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                result = edit1.getText().toString();
//
//////                사진은 Post로 보내야함
////                Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
////                byte[] bytearray = stream.toByteArray();
////                String s = Base64.encodeToString(bytearray, Base64.DEFAULT);
////                //photo는 Bitmap변수
////
////                requestGetonNotice(s);
//                requestGetonNotice(result);

//
//            }
//        });
        // 2번째 키패드 리스너
        key2Button[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button1:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, " ");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button2:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = "";
                            edit1.setText(result);
                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button3:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            if (result.equals("")) return false;
                            int selection = edit1.getSelectionStart();
                            if(selection == 0) return false;
                            String result1 = result.substring(0, selection-1);
                            result = result1 + result.substring(selection, result.length());
                            edit1.setText(result);
                            edit1.setSelection(selection-1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button4:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button5:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, ")");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button6:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "+");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button7:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.VISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button8:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "ln(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 3);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button9:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "mod");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 3);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button10:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "∫");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "∫";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button11:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "abs(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);

//                            result = edit1.getText().toString();
//                            result += "abs(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button12:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "-");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });

//        key2Button[12].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                keyPad1.setVisibility(View.INVISIBLE);
//                keyPad2.setVisibility(View.VISIBLE);
//                keyPad3.setVisibility(View.INVISIBLE);
//                keyPad4.setVisibility(View.INVISIBLE);
//            }
//        });

        key2Button[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button13:
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
        key2Button[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button14:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "log10(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 6);

//                            result = edit1.getText().toString();
//                            result += "log10(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button15:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "p");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

//                            result = edit1.getText().toString();
//                            result += "*10^";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button16:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "d/dx");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);

//                            result = edit1.getText().toString();
//                            result += "∑";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button17:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "ceil(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 5);

//                            result = edit1.getText().toString();
//                            result += "ceil";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button18:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "*");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button19:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.VISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button20:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "log2(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 5);

//                            result = edit1.getText().toString();
//                            result += "log2(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button21:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "c");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button22:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "∑");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button23:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "floor(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 6);

//                            result = edit1.getText().toString();
//                            result += "floor";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button24:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "/");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button25:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button26:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "e");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[26].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button27:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "∞");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[27].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button28:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "lim");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 3);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[28].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button29:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, " to ");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);
                        }
                        break;
                }
                return false;
            }
        });
        key2Button[29].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key2Button30:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(KeyPadActivity.this);
                            builder.setTitle("계산중 입니다.")
                                    .setMessage("잠시만 기다려 주세요.");
                            dialog = builder.create();
                            dialog.show();
                            requestGetonNotice(result);
                        }
                        break;
                }
                return false;
            }
        });

        //3번째 키패드 리스너
        key3Button[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button1:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, " ");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button2:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = "";
                            edit1.setText(result);
                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button3:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            if (result.equals("")) return false;
                            int selection = edit1.getSelectionStart();
                            if(selection == 0) return false;
                            String result1 = result.substring(0, selection-1);
                            result = result1 + result.substring(selection, result.length());
                            edit1.setText(result);
                            edit1.setSelection(selection-1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button4:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button5:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, ")");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button6:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "+");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button7:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.VISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button8:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "sin(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);

//                            result += "sin(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button9:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arcsin(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 7);

//                            result += "arcsin(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button10:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "sinh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 5);

//                            result += "sinh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button11:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arcsinh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 8);

//                            result += "arcsinh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button12:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "-");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button13:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.VISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button14:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "cos(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);

//                            result += "cos(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button15:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arccos(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 7);

//                            result += "arccos(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button16:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "cosh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 5);

//                            result += "cosh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button17:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arccosh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 8);

//                            result += "arccosh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button18:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "*");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button19:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.VISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button20:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "tan(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 4);

//                            result += "tan(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button21:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arctan(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 7);

//                            result += "arctan(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button22:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "tanh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 5);

//                            result += "tanh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button23:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "arctanh(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 8);

//                            result += "arctanh(";
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button24:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "/");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button25:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button26:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "x");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[26].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button27:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "y");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[27].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button28:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "z");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[28].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button29:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "π");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key3Button[29].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key3Button30:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(KeyPadActivity.this);
                            builder.setTitle("계산중 입니다.")
                                    .setMessage("잠시만 기다려 주세요.");
                            dialog = builder.create();
                            dialog.show();
                            requestGetonNotice(result);
                        }
                        break;
                }
                return false;
            }
        });


        //키패드 4번째 리스너
        key4Button[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button1:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, " ");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button2:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = "";
                            edit1.setText(result);
                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button3:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            if (result.equals("")) return false;
                            int selection = edit1.getSelectionStart();
                            if(selection == 0) return false;
                            String result1 = result.substring(0, selection-1);
                            result = result1 + result.substring(selection, result.length());
                            edit1.setText(result);
                            edit1.setSelection(selection - 1);
//                            result = edit1.getText().toString();
//                            if (result.equals("")) return false;
//                            result = result.substring(0, result.length() - 1);
//                            edit1.setText(result);
//                            edit1.setSelection(edit1.length());
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button4:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "(");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button5:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, ")");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button6:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "+");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button7:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.VISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });

        key4Button[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button8:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "x");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

                        }
                        break;
                }
                return false;
            }
        });

        key4Button[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button9:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "＜");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button10:
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
        key4Button[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button11:
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
        key4Button[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button12:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "-");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button13:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.VISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button14:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "y");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button15:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "＞");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button16:
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
        key4Button[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button17:
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
        key4Button[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button18:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "*");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button19:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.VISIBLE);
                            keyPad4.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button20:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "z");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

                        }
                        break;
                }
                return false;
            }
        });
        key4Button[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button21:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "≤");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

                        }
                        break;
                }
                return false;
            }
        });
        key4Button[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button22:
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
        key4Button[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button23:
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
        key4Button[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button24:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "/");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);
                        }
                        break;
                }
                return false;
            }
        });
        key4Button[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button25:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            keyPad1.setVisibility(View.INVISIBLE);
                            keyPad2.setVisibility(View.INVISIBLE);
                            keyPad3.setVisibility(View.INVISIBLE);
                            keyPad4.setVisibility(View.VISIBLE);

                        }
                        break;
                }
                return false;
            }
        });
        key4Button[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button26:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "=");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

                        }
                        break;
                }
                return false;
            }
        });

        key4Button[26].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button27:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            StringBuffer sb = new StringBuffer(result);
                            int selection = edit1.getSelectionStart();
                            sb.insert(selection, "≥");
                            edit1.setText(sb);
                            edit1.setSelection(selection + 1);

                        }
                        break;
                }
                return false;
            }
        });

        key4Button[27].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button28:
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

        key4Button[28].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button29:
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

        key4Button[29].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                switch (v.getId()) {
                    case R.id.key4Button30:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            image.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                            result = edit1.getText().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(KeyPadActivity.this);
                            builder.setTitle("계산중 입니다.")
                                    .setMessage("잠시만 기다려 주세요.");
                            dialog = builder.create();
                            dialog.show();
                            requestGetonNotice(result);

                        }
                        break;
                }
                return false;
            }
        });

        webTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.key4Button30:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(webTv.getWindowToken(), 0);

                        }
                        break;
                }
                return false;
            }
        });

        webTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        webTv.setFocusable(false);

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 감추기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit1.getWindowToken(), 0);
            }
        });

//        webTv.loadUrl("http://113.198.80.233:80/PS/results");


        //dialog create

        //사진은 Post로 보내야함
//        Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.math);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bytearray = stream.toByteArray();
//        String s = Base64.encodeToString(bytearray, Base64.DEFAULT);
//        //photo는 Bitmap변수
//        requestGetonNotice("Sum[k^2, {k,1,11}]");

//        Integrate[Cos[x], x] , Sum[k^2, {k,1,11}]
    } //OnCreate 끝

    //
    public void requestGetonNotice(final String image) { //1+1
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Retrofit.ServerIP).build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class);
                    retrofit.sendResult(image, new Callback<String>() {

                        @Override
                        public void success(String result, Response arg1) {
                            // TODO Auto-generated method stub
//                            webTv.loadUrl(arg1.getUrl());
                            webTv.loadUrl(arg1.getUrl());
//                            try {
//                                Thread.sleep(4000);
//                            }catch (Exception e) {}
                            Log.i("sample", result + "result");
                            Log.i("sample", arg1.getUrl() + "Url");
//                            webTv.loadUrl(result);

                            dialog.dismiss();
                        }

                        @Override
                        public void failure(RetrofitError arg0) {
                            // TODO Auto-generated method stub
//                            tv.setText("N");
                            Log.i("sample", arg0.toString() + "error");
//                            tv.setText(arg0.getUrl());
                            webTv.loadUrl(arg0.getUrl());
//                            try {
//                                Thread.sleep(4000);
//                            }catch (Exception e) {}
                            Log.i("sample", arg0.getUrl() + "url");
                            Log.i("sample", arg0.getMessage() + "Message");
                            dialog.dismiss();
                        }
                    });

                } catch (Throwable ex) {
//                    tv.setText("E");
                }
            }
        }).start();

    }
}
