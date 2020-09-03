package com.softarea.mpktarnow.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.softarea.mpktarnow.R;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class ImageUtils {
  public static Bitmap createBusPinImage(Context context, String text) {
    text = StringUtils.deleteWhiteSpaces(text);
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.vh_pin);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 8 * text.length();
    int y = bounds.height() + 24;
    canvas.drawText(text, x, y, paint);
    return bitmap;
  }
  public static Bitmap createLongBusPinImage(Context context, String text, String departue) {
    text = StringUtils.deleteWhiteSpaces(text);
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.vh_pin_loop);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 8 * text.length();
    int y = bounds.height() + 24;
    canvas.drawText(text, x, y, paint);

    String result;
    if( (int) Integer.parseInt(departue) / 60 < 1 ) {
      result = "< 1";
    } else {
      result = String.valueOf((int) Integer.parseInt(departue) / 60 );
    }
    departue = StringUtils.join("odjazd za " + result + " min");

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setTextSize(9 * scale);
    bounds = new Rect();
    paint.getTextBounds(departue, 0, departue.length(), bounds);

    x = bitmap.getWidth() / 2 - 6 * departue.length();
    y = bounds.height() + 60;
    canvas.drawText(departue, x, y, paint);


    return bitmap;
  }
}
