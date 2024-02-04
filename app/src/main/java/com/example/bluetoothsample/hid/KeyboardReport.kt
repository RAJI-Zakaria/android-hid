// com.example.bluetoothsample.hid.KeyboardReport

@JvmInline
value class KeyboardReport(
    val bytes: ByteArray = ByteArray(3) { 0 }
) {
    //mapping clavier azerty ===> (linux, windows)
    // Implementation for setting modifiers, getting key1, resetting, and companion object with KeyEventMap
}
