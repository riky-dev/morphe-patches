package app.riky.patches.devcheck

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_DEVCHECK

// DevCheck ships with Google PairIP protection. Morphe re-signs the APK, so the
// protection layer rejects it and redirects to the Play Store before the app
// starts. Hook SignatureCheck like pairipfix: no-op verifyIntegrity and force
// verifySignatureMatches to succeed so the startup VM can still register hooks.
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

        PairIpVerifySignatureMatchesFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )
    }
}
