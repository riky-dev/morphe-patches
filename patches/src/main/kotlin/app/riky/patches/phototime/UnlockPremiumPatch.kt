package app.riky.patches.phototime

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_PHOTOTIME

@Suppress("unused")
val unlockPremiumPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Forces PremiumStatus checks to succeed so client-gated PhotoTime " +
        "features unlock. Weather/forecast may still need network.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_PHOTOTIME)

    execute {
        // Feature gates: invoke-virtual … Ldu6;->a()Z
        PremiumStatusIsPremiumFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """,
        )

        // State / UI: cca.P(isPremiumUser, supporterCode) → always PREMIUM
        PremiumStatusFromFlagsFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Ldu6;->PREMIUM:Ldu6;
                return-object v0
            """,
        )
    }
}
