package com.example.redeemers_faz_com

/*
    Simplified version of
        https://github.com/raghavk92/Kontroller
*/


import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.util.Log
import androidx.annotation.RequiresPermission

@Suppress("MemberVisibilityCanBePrivate")
open class KeyboardSender(
    val hidDevice: BluetoothHidDevice,
    val host: BluetoothDevice
) {
    companion object {
        const val TAG = "KeyboardSender"
    }

    /**
     * send key as an HID Keyboard
     * @param keyCode the Hid Keycode of the key
     * @param modifiers the list of the modifier
     * @param releaseModifiers if false do not release modifier when releasing the key
     */
    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun sendKeyboard(keyCode: Int, modifiers: List<KeyModifier>, releaseModifiers: Boolean): Boolean {
        val byteKey = KeyboardReport.KeyEventMap[keyCode] ?: return false
        keyboardReport.setModifiers(modifiers)
        keyboardReport.key1 = byteKey.toByte()
        customSender(releaseModifiers)
        return true
    }





    val keyboardReport = KeyboardReport()

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    protected open fun sendKeys() {
        if (!hidDevice.sendReport(host, KeyboardReport.ID, keyboardReport.bytes)) {
            Log.e(TAG, "Report wasn't sent")
        }
    }
    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    protected open fun customSender(releaseModifiers: Boolean) {
        sendKeys()
        if (releaseModifiers.not()) {
            keyboardReport.key1 = 0.toByte()
            sendKeys()
        }
        keyboardReport.reset()
        sendKeys()
    }

}