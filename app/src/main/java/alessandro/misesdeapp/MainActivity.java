package alessandro.misesdeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        webview =(WebView)findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url)
            {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int Dheight = displayMetrics.heightPixels;
                int Dwidth = displayMetrics.widthPixels; //->1080
                if (String.valueOf(url).length() != 23) {
                    webview.loadUrl("javascript:(function() { " +
                            //killt alle images falls nicht auf der home seite (23 Zeichen lang)
                            "var images = document.getElementsByTagName('img'); " +
                            "for (var i = 0; i< images.length; i++) document.getElementsByTagName('img')[i].style.display='none';" +
                            "})()");
                }
                /*else {
                    webview.loadUrl("javascript:(function() { " +
                            //killt alle images falls nicht auf der home seite (23 Zeichen lang)
                            "var images = document.getElementsByTagName('img'); " +
                            "for (var i = 0; i< images.length; i++){" +
                                "var imgwidth = document.getElementsByTagName('img')[i].style.width" +
                                "document.getElementsByTagName('img')[i].style.width=(imgwidth/2+'px');" +
                                "}" +
                            "})()");
                }
                *///change image width on main page doesnt work
                webview.loadUrl("javascript:(function() { " +
                    "document.getElementById('nav').style.display='none'; " +
                    "document.getElementById('header').style.display='none'; " +
                    "document.getElementsByClassName('aside main-aside sidebar')[0].style.display='none'; " +
                    "document.getElementsByClassName('aside main-aside sidebar')[1].style.display='none'; " +
                    "document.getElementById('footer').style.display='none'; " +
                        "document.getElementById('wrapper').style.width=(" + Dwidth/3.1 + " + 'px'); " +
                        "document.getElementById('content').style.width=(" + Dwidth/3.1 + " + 'px'); " +
                        "document.getElementById('container').style.width=(" + Dwidth + " + 'px'); " +
                       "document.getElementById('main').style.width=(" + Dwidth + " + 'px'); " +

                        //title page showbox
                        "document.getElementsByClassName('featured clearfix')[0].style.display='none'; " +
                        //cookies
                        "document.getElementById('cookie-law-info-bar').style.display='none'; " +
                        "document.getElementById('cookie-law-info-again').style.display='none'; " +
                        //iwelche zwei leerre boxen
                        "document.getElementById('bottom-content-1').style.display='none'; " +
                        "document.getElementById('bottom-content-0').style.display='none'; " +

                        "document.getElementsByClassName('hfeed posts-quick clearfix')[0].style.width=(" + Dwidth/3.1 + " + 'px');" +
                    "})()");

                //lade seite nommal
                webview.setVisibility(View.VISIBLE);
          //      super.onPageFinished(view, url);

            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

        webview.setVisibility(View.INVISIBLE);
        webview.loadUrl("http://www.misesde.org");
        // disable horizontal scroll

        webview.setHorizontalScrollBarEnabled(false);
        webview.setOnTouchListener(new View.OnTouchListener() {
            float m_downX;
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                        break;
                    }

                }
                return false;
            }
        });


    }
    @Override
    //overriding back arrow
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
