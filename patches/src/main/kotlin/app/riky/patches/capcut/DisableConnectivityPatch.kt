package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val disableConnectivityPatch = bytecodePatch(
    name = "Disable all connectivity",
    description = "Blocks CapCut's remote force-update / version-nag path (the part that " +
        "goes away when you toggle airplane mode) without spoofing global NetworkUtils. " +
        "Full offline spoofs abort opening a local video (draft check returns material " +
        "authorization network fail and pops back to home). Templates, effects, and cloud " +
        "still need a real network. Login-time app-upgrade errors are not covered.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Splash checks this before calling showPopupDialog.
        VersionUpdateShouldShowFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """,
        )

        // Startup fetch of version_update config. A failed fetch clears the local cache
        // (why airplane mode hides the nag); skip the fetch entirely.
        VersionUpdateRefreshFingerprint.method.addInstructions(
            0,
            """
                return-void
            """,
        )

        VersionUpdateShowPopupFingerprint.method.addInstructions(
            0,
            """
                return-void
            """,
        )
    }
}
