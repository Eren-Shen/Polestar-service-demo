package com.gyf.cactus.callback

/**
 * 前后台切换回调
 */
interface CactusBackgroundCallback {
    /**
     * 前后台切换回调
     * @param background Boolean
     */
    fun onBackground(background: Boolean)
}