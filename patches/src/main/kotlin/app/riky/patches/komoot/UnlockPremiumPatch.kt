package app.riky.patches.komoot

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_KOMOOT

private const val GRANTED =
    "Lde/komoot/android/services/api/model/RoutingPermission\$StatusPermission;" +
        "->GRANTED:Lde/komoot/android/services/api/model/RoutingPermission\$StatusPermission;"

@Suppress("unused")
val unlockPremiumPatch = bytecodePatch(
    name = "Unlock Premium features",
    description = "Unlock Premium features. Some features are not possible to patch " +
        "(server sided), such as saving a multi-days hike.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KOMOOT)

    execute {
        // Force premium=true when server config is deserialized into AppConfigV3.
        AppConfigV3Fingerprint.method.addInstruction(
            0,
            "sget-object p1, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;",
        )

        // Force premium=true on public user profiles parsed from API responses.
        PublicUserProfileV7Fingerprint.method.addInstruction(
            0,
            "const/4 p5, 0x1",
        )

        // Force routing permission to GRANTED (ctor shape changed in 2026.41.3).
        try {
            // 2026.41.3+: JSONObject ctor — overwrite field a before return.
            val routingInit = RoutingPermissionJsonInitFingerprint.method
            routingInit.addInstructions(
                routingInit.implementation!!.instructions.lastIndex,
                """
                    sget-object v0, $GRANTED
                    iput-object v0, p0, Lde/komoot/android/services/api/model/RoutingPermission;->a:Lde/komoot/android/services/api/model/RoutingPermission${'$'}StatusPermission;
                """,
            )
        } catch (_: Exception) {
            // Pre-2026.41.3: StatusPermission ctor — overwrite p1 before iput at index 2.
            RoutingPermissionStatusInitFingerprint.method.addInstruction(
                2,
                "sget-object p1, $GRANTED",
            )
        }
    }
}
