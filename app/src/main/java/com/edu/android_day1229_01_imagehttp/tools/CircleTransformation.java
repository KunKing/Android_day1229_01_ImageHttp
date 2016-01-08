package com.edu.android_day1229_01_imagehttp.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Ming on 2015/12/29.
 */
public class CircleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap result = Bitmap.createBitmap(
                source.getWidth(),
                source.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(
                        source,
                        Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP)
        );
        new Canvas(result).drawCircle(
                source.getWidth() / 2,
                source.getHeight() / 2,
                Math.min(source.getWidth() / 2, source.getHeight() / 2),
                paint
        );

        // 获取 图片的大小
        // int imagePx = result.getRowBytes() * result.getHeight();
        // 得到想要的图片之后  必须需要释放原图 否则内存吃紧
        source.recycle();
        return result;
    }

    @Override
    public String key() {
        return "circle";
    }
}
