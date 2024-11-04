package pro.respawn.flowmvi.plugins

import pro.respawn.flowmvi.api.FlowMVIDSL
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.StoreBuilder
import pro.respawn.flowmvi.dsl.plugin
import pro.respawn.flowmvi.api.StorePlugin

/**
 * Alias for [StorePlugin.onStop] callback or `plugin { onStop { block() } }`
 *
 * See the function documentation for more info.
 */
@FlowMVIDSL
public inline fun <S : MVIState, I : MVIIntent, A : MVIAction> deinitPlugin(
    crossinline block: (e: Exception?) -> Unit
): StorePlugin<S, I, A> = plugin { onStop { block(it) } }

/**
 * Install a new [deinitPlugin].
 */
@FlowMVIDSL
public inline fun <S : MVIState, I : MVIIntent, A : MVIAction> StoreBuilder<S, I, A>.deinit(
    crossinline block: (e: Exception?) -> Unit
): Unit = install(deinitPlugin(block))
