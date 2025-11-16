package com.sandbox.ftptransfer.model

data class MonitoringSettings(
    val delaySeconds: Int = 2,  // 0-60 detik
    val powerAware: Boolean = true,
    val adaptiveScanning: Boolean = true
) {
    fun getDelayMillis(): Long {
        return (delaySeconds * 1000).toLong()
    }
    
    companion object {
        fun default(): MonitoringSettings {
            return MonitoringSettings(
                delaySeconds = 2,
                powerAware = true,
                adaptiveScanning = true
            )
        }
    }
}
