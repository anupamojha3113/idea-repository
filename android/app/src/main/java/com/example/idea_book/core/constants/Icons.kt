package com.example.idea_book.core.constants

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val LightBulb: ImageVector
    get() {
        if (lightBulb != null) {
            return lightBulb!!
        }
        lightBulb = materialIcon(name = "Filled.LightBulb") {
            materialPath {
                moveTo(9.0f, 21.0f)
                curveToRelative(0.0f, 0.5f, 0.4f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, -0.5f, 1.0f, -1.0f)
                verticalLineToRelative(-1.0f)
                lineTo(9.0f, 20.0f)
                verticalLineToRelative(1.0f)
                close()
                moveTo(12.0f, 2.0f)
                curveTo(8.1f, 2.0f, 5.0f, 5.1f, 5.0f, 9.0f)
                curveToRelative(0.0f, 2.4f, 1.2f, 4.5f, 3.0f, 5.7f)
                lineTo(8.0f, 17.0f)
                curveToRelative(0.0f, 0.5f, 0.4f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, -0.5f, 1.0f, -1.0f)
                verticalLineToRelative(-2.3f)
                curveToRelative(1.8f, -1.3f, 3.0f, -3.4f, 3.0f, -5.7f)
                curveToRelative(0.0f, -3.9f, -3.1f, -7.0f, -7.0f, -7.0f)
                close()
            }
        }
        return lightBulb!!
    }

val Sort: ImageVector
    get() {
        if (sort != null) {
            return sort!!
        }
        sort = materialIcon(name = "Filled.Sort") {
            materialPath {
                moveTo(3.0f, 18.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(-2.0f)
                lineTo(3.0f, 16.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(3.0f, 6.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(18.0f)
                lineTo(21.0f, 6.0f)
                lineTo(3.0f, 6.0f)
                close()
                moveTo(3.0f, 13.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(-2.0f)
                lineTo(3.0f, 11.0f)
                verticalLineToRelative(2.0f)
                close()
            }
        }
        return sort!!
    }

val Save: ImageVector
    get() {
        if (save != null) {
            return save!!
        }
        save = materialIcon(name = "Filled.Save") {
            materialPath {
                moveTo(17.0f, 3.0f)
                lineTo(5.0f, 3.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(21.0f, 7.0f)
                lineToRelative(-4.0f, -4.0f)
                close()
                moveTo(12.0f, 19.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, -1.34f, -3.0f, -3.0f)
                reflectiveCurveToRelative(1.34f, -3.0f, 3.0f, -3.0f)
                reflectiveCurveToRelative(3.0f, 1.34f, 3.0f, 3.0f)
                reflectiveCurveToRelative(-1.34f, 3.0f, -3.0f, 3.0f)
                close()
                moveTo(15.0f, 9.0f)
                lineTo(5.0f, 9.0f)
                lineTo(5.0f, 5.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(4.0f)
                close()
            }
        }
        return save!!
    }

private var save: ImageVector? = null
private var lightBulb: ImageVector? = null
private var sort: ImageVector? = null
