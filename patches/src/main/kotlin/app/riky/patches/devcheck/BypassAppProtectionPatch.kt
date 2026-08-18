package app.riky.patches.devcheck

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_DEVCHECK

// DevCheck ships with Google PairIP protection. Morphe re-signs the APK, so the
// protection layer rejects it and redirects to the Play Store before the app
// starts. These hooks no-op the startup checks so patched builds can launch.
@Suppress("unused")
val bypassAppProtectionPatch = bytecodePatch(
    name = "Bypass app protection",
    description = "Disables PairIP signature and startup integrity checks that block re-signed APKs.",
    default = true
) {
    compatibleWith(COMPATIBILITY_DEVCHECK)

    execute {
        PairIpVerifyIntegrityFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        // Skip only the startup integrity VM; other VMRunner hooks still run.
        PairIpVmRunnerInvokeFingerprint.method.addInstructions(
            0,
            """
                const-string v0, "DFQzzv4Rl5kZNZOE"
                invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
                move-result v0
                if-nez v0, :pairip_continue
                const/4 v0, 0x0
                return-object v0
                :pairip_continue
            """
        )
    }
}
