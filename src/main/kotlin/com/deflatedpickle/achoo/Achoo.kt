/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.achoo

import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

@Suppress("UNUSED")
object Achoo : ModInitializer {
    private const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    override fun onInitialize(mod: ModContainer) {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))
    }
}
