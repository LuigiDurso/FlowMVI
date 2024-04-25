package pro.respawn.flowmvi.test.plugin

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import pro.respawn.flowmvi.api.FlowMVIDSL
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.StorePlugin
import pro.respawn.flowmvi.plugins.TimeTravel
import kotlin.coroutines.coroutineContext

/**
 * A function that runs a test on a [StorePlugin].
 *
 * * This function suspends until the test is complete.
 * * The plugin may launch new coroutines, which will cause the test to suspend until the test scope is exited.
 * * The plugin may produce side effects which are tracked in the [PluginTestScope.timeTravel] property.
 * * The plugin may change the state, which is accessible via the [PluginTestScope.state] property.
 * * You can use [TestPipelineContext] which is provided with the [PluginTestScope], to set up the plugin's
 * environment for the test.
 */
@FlowMVIDSL
public suspend inline fun <S : MVIState, I : MVIIntent, A : MVIAction> StorePlugin<S, I, A>.test(
    initial: S,
    timeTravel: TimeTravel<S, I, A> = TimeTravel(),
    crossinline block: suspend PluginTestScope<S, I, A>.() -> Unit,
): Unit = coroutineScope {
    PluginTestScope(initial, coroutineContext, this@test, timeTravel).run {
        block()
    }
}
