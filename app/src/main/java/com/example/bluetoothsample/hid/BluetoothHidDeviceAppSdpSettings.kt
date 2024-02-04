// com.example.bluetoothsample.hid.BluetoothHidDeviceAppSdpSettings

import android.bluetooth.BluetoothHidDevice

class BluetoothHidDeviceAppSdpSettings(
    val deviceName: String,
    val description: String,
    val provider: String,
    val subclass: Byte,
    val descriptor: ByteArray
) {
    // You can define properties and functions here based on your needs
}
