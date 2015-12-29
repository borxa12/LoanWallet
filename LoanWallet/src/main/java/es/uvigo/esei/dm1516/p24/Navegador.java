package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Borxa on 29/12/2015.
 */
public class Navegador extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.webview);

        String busqueda = (String) this.getIntent().getExtras().get("URL");
        String busquedaFormateada = busqueda.replace(" ","%20");
        Log.d("URL de BUSQUEDA", busquedaFormateada);

        WebView webView = (WebView) this.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.es/search?q=" + busquedaFormateada);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.edicion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mainMenuAtras:
                this.finish();
                break;
        }
        return true;
    }

}
