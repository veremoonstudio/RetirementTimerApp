package com.veremoonstudio.retirementtimer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import androidx.core.content.res.ResourcesCompat

object WidgetFontHelper {

    fun getCinzelTypeface(context: Context, weight: Int): Typeface {
        val base = try {
            ResourcesCompat.getFont(context, R.font.cinzel)
        } catch (_: Throwable) {
            null
        } ?: Typeface.SERIF

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Typeface.create(base, weight, false)
        } else {
            if (weight >= 700) Typeface.create(base, Typeface.BOLD)
            else Typeface.create(base, Typeface.NORMAL)
        }
    }

    fun getCormorantSemiBoldTypeface(context: Context): Typeface {
        val base = try {
            ResourcesCompat.getFont(context, R.font.cormorant_garamond_semibold)
        } catch (_: Throwable) {
            null
        } ?: Typeface.SERIF

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Typeface.create(base, 600, false)
        } else {
            Typeface.create(base, Typeface.BOLD)
        }
    }

    fun renderTitleBitmap(
        context: Context,
        prefixText: String,
        dateText: String,
        fontSizeSp: Float,
        colorInt: Int
    ): Bitmap {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        val textSizePx = fontSizeSp * scaledDensity

        val paintPrefix = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = getCinzelTypeface(context, 700)
            textSize = textSizePx
            color = colorInt
        }

        val paintDate = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = getCinzelTypeface(context, 600)
            textSize = textSizePx
            color = colorInt
        }

        val prefixWidth = paintPrefix.measureText(prefixText)
        val dateWidth = paintDate.measureText(dateText)
        val totalWidth = (prefixWidth + dateWidth).toInt() + 4

        val fontMetrics = paintPrefix.fontMetrics
        val textHeight = (fontMetrics.bottom - fontMetrics.top).toInt() + 2

        val width = if (totalWidth > 0) totalWidth else 1
        val height = if (textHeight > 0) textHeight else 1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val baselineY = -fontMetrics.top
        canvas.drawText(prefixText, 2f, baselineY, paintPrefix)
        canvas.drawText(dateText, 2f + prefixWidth, baselineY, paintDate)

        return bitmap
    }

    fun renderTextBitmap(
        context: Context,
        text: String,
        typeface: Typeface,
        fontSizeSp: Float,
        colorInt: Int
    ): Bitmap {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        val textSizePx = fontSizeSp * scaledDensity

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.typeface = typeface
            this.textSize = textSizePx
            this.color = colorInt
        }

        val textWidth = paint.measureText(text).toInt() + 4
        val fontMetrics = paint.fontMetrics
        val textHeight = (fontMetrics.bottom - fontMetrics.top).toInt() + 2

        val width = if (textWidth > 0) textWidth else 1
        val height = if (textHeight > 0) textHeight else 1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val baselineY = -fontMetrics.top
        canvas.drawText(text, 2f, baselineY, paint)

        return bitmap
    }
}
