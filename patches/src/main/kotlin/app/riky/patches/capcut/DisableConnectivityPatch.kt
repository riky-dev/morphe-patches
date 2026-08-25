package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val disableConnectivityPatch = bytecodePatch(
    name = "Disable all connectivity",
    description = "Spoofs CapCut's NetworkUtils helpers so the app believes it is offline " +
        "(same idea as airplane mode for gated features, update fetches that check " +
        "connectivity, TTNet Retrofit gates, templates, cloud, etc.). Opt-in: breaks " +
        "anything that needs the internet. Does not tear down sockets — native/cronet " +
        "paths that skip these helpers may still reach the network. Prefer " +
        "\"Disable force update\" if you only want to kill the version nag.",
    default = false,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        val returnFalse = """
            const/4 v0, 0x0
            return v0
        """

        // Primary CapCut infrastructure checks (~400 call sites).
        VegaNetworkIsConnectedFingerprint.method.addInstructions(0, returnFalse)
        VegaNetworkHasTransportFingerprint.method.addInstructions(0, returnFalse)
        VegaNetworkIsWifiFingerprint.method.addInstructions(0, returnFalse)
        VegaNetworkTypeFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Lcom/vega/infrastructure/util/NetworkUtils${'$'}NetworkType;->g:Lcom/vega/infrastructure/util/NetworkUtils${'$'}NetworkType;
                return-object v0
            """,
        )

        // Retouch / photo module bundled in CapCut.
        RetouchNetworkIsConnectedFingerprint.method.addInstructions(0, returnFalse)
        RetouchNetworkIsWifiFingerprint.method.addInstructions(0, returnFalse)
        RetouchNetworkTypeFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Lcom/xt/retouch/util/NetworkUtils${'$'}NetworkType;->h:Lcom/xt/retouch/util/NetworkUtils${'$'}NetworkType;
                return-object v0
            """,
        )

        // ByteDance common utils (TTNet / Retrofit / applog).
        BytedanceIsNetworkAvailableFingerprint.method.addInstructions(0, returnFalse)
        BytedanceIsNetworkAvailableFastFingerprint.method.addInstructions(0, returnFalse)
        BytedanceIsWifiFingerprint.method.addInstructions(0, returnFalse)
        BytedanceIsWifiFastFingerprint.method.addInstructions(0, returnFalse)
        BytedanceGetNetworkTypeFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Lcom/bytedance/common/utility/NetworkUtils${'$'}NetworkType;->NONE:Lcom/bytedance/common/utility/NetworkUtils${'$'}NetworkType;
                return-object v0
            """,
        )

        // Lemon components utils.
        LmNetworkAnyConnectedFingerprint.method.addInstructions(0, returnFalse)
        LmNetworkIsAvailableFingerprint.method.addInstructions(0, returnFalse)
        LmNetworkIsWifiFingerprint.method.addInstructions(0, returnFalse)
    }
}
