package com.itv.spider.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.mozilla.universalchardet.UniversalDetector;

public class EntityCharsetUtils{
	public static String toString(
            final HttpEntity entity, final Charset defaultCharset) throws IOException, ParseException {
        boolean cha=false;
		if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int)entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            Charset charset = null;
            try {
                ContentType contentType = ContentType.get(entity);
                if (contentType != null) {
                    charset = contentType.getCharset();
                }
            } catch (final UnsupportedCharsetException ex) {
                throw new UnsupportedEncodingException(ex.getMessage());
            }
            UniversalDetector detector=null;
            if (charset == null) {
            	detector = new UniversalDetector(null);
            }
            ByteArrayBuffer bytebuffer=new ByteArrayBuffer(i);
            byte [] tmp_=new byte[1024];
            int len;
            int j=0;
            
            while((len=instream.read(tmp_))!=-1){
            	bytebuffer.append(tmp_, 0, len);
            	if(j<4&&detector!=null){
            		detector.handleData(tmp_, 0, len);
            	}
            	j++;
            }
            if(detector!=null){
	            detector.dataEnd();
	            String encoding = detector.getDetectedCharset();
	            detector.reset();
	            charset=Charset.forName(encoding);
            }
            if (charset == null) {
                charset = defaultCharset;
            }
            return new String(bytebuffer.toByteArray(),charset);
        } finally {
            instream.close();
        }
    }
}
