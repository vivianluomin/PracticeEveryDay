package vivian.com.urltest;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by asus1 on 2017/8/6.
 */

public class XMLRequest extends Request<XmlPullParser> {
    private  final Response.Listener<XmlPullParser> mListener;

    public XMLRequest(int method, String url, Response.Listener mListener,Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
    }

    public XMLRequest(String url, Response.Listener mlistener,Response.ErrorListener listener) {
        this(Method.GET,url, mlistener,listener);
    }


    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String xmlString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser,HttpHeaderParser.parseCacheHeaders(networkResponse));

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }catch (XmlPullParserException e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser xmlPullParser) {
        mListener.onResponse(xmlPullParser);
    }

}
