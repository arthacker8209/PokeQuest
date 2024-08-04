package com.deepak.pokequest.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class CoroutineDispatchersProvider(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher
) {
    @Inject
    constructor(): this(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO, Dispatchers.Unconfined)
}